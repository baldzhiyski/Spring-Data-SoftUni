package orm;

import orm.anotations.Column;
import orm.anotations.Entity;
import orm.anotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<T> implements DB_Context<T> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(T entity) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        String fieldList = this.getDBFieldsWithoutIdentity(entity);
        String valueList = this.getInsertValues(entity);

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, fieldList, valueList);

        return this.connection.prepareStatement(sql).execute();
    }

    private String getInsertValues(T entity) throws IllegalAccessException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        List<String> result = new ArrayList<>();



        for (Field declaredField : declaredFields) {
            if(declaredField.getAnnotation(Column.class)==null){
                continue;
            }

            declaredField.setAccessible(true);
            Object value = declaredField.get(entity);
            result.add("\"" + value.toString() + "\"");
        }
        return String.join(",",result);

    }

    private String getDBFieldsWithoutIdentity(T entity) {
        return Arrays.stream(entity.getClass()
                .getDeclaredFields())
                .filter(f->f.getAnnotation(Column.class) !=null)
                .map(f->f.getAnnotation(Column.class).name())
                .collect(Collectors.joining(","));


    }

    private String getTableName(Class<?> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if(annotation== null){
          throw new ORMException("Provided class has no such annotation");
        }
        return annotation.name();
    }

    @Override
    public Iterable<T> find(Class<T> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(entityType,null);
    }

    @Override
    public Iterable<T> find(Class<T> entityType,String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(entityType);

        String sql = String.format("SELECT * FROM %s",tableName,
                where == null ? "" : "WHERE " + where);
        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();

        List<T> result = new ArrayList<>();

        T lastResult = this.createEntity(entityType, resultSet);

        while (lastResult !=null){
            result.add(lastResult);
            lastResult = this.createEntity(entityType,resultSet);
        }
        return result;
    }

    @Override
    public T findFirst(Class<T> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(entityType,null);
    }

    @Override
    public T findFirst(Class<T> entityTyp,String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(entityTyp);

        String sql = String.format("SELECT * FROM %s LIMIT 1",tableName,
                where == null ? "" : "WHERE " + where);
        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();

        return this.createEntity(entityTyp ,resultSet);
    }

    private T createEntity(Class<T> entityTyp, ResultSet resultSet) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!resultSet.next()){
            return null;
        }
        T entity = entityTyp.getDeclaredConstructor().newInstance();

        // Create empty object
        // Fill each field
        Field[] declaredFields = entityTyp.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(!declaredField.isAnnotationPresent(Column.class)
            && !declaredField.isAnnotationPresent(Id.class)){
                continue;
            }
            Column annotation = declaredField.getAnnotation(Column.class);

            String fieldName =
                   annotation == null ? declaredField.getName() : annotation.name();

            String value = resultSet.getString(fieldName);
            this.fillData(entity,declaredField,value);
        }

        return  entity;
    }

    private T fillData(T entity, Field declaredField,String value) throws IllegalAccessException {
        declaredField.setAccessible(true);
        if(declaredField.getType() == long.class || declaredField.getType() == Long.class){
            declaredField.setLong(entity,Long.parseLong(value));
        }else if(declaredField.getType() == int.class || declaredField.getType() == Integer.class){
            declaredField.setInt(entity,Integer.parseInt(value));
        }
        else  if ( declaredField.getType()== LocalDate.class){
            declaredField.set(entity,LocalDate.parse(value));
        }else if ( declaredField.getType() == String.class){
            declaredField.set(entity,value);
        }else{
            throw new ORMException("Unsupported type " + declaredField.getType());
        }
        return  entity;
    }
}
