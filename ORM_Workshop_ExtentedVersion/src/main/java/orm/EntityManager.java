package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static entities.constants.Constants.*;
import static entities.constants.Constants.Queries.*;
import static entities.constants.Constants.SQLTypes.*;
import static orm.MyConnector.getConnection;


public class EntityManager<E> implements DBContext<E> {
    private final Connection connection;

    public EntityManager() throws SQLException {
        this.connection = getConnection();
    }

/**
 * Insert the given entity into the database.
 */
    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        final Field idColumn = getIdColumn(entity.getClass());
        idColumn.setAccessible(true);

        final Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue <= 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, idColumn);
    }

    /**
     * Finds all entities of the specified table in the database.
     *
     * @param table the entity class representing the database table
     * @return an Iterable containing all entities found
     * @throws SQLException             if a database access error occurs
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws NoSuchMethodException     if the specified method does not exist
     * @throws InstantiationException    if an instance of the specified class cannot be created
     * @throws IllegalAccessException    if access to the entity's field is denied
     */
    @Override
    public Iterable<E> find(Class<E> table) throws SQLException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        final String tableName = getTableName(table);

        final PreparedStatement findFirstStatement =
                connection.prepareStatement(String.format(FIND_ALL_QUERY, tableName));

        return getPOJOs(findFirstStatement, table);
    }
     /**
      * Finds entities of the specified table in the database based on the given condition.
      * @param table     the entity class representing the database table
     * @param condition the condition to apply in the query
     * @return an Iterable containing entities that satisfy the condition
     * @throws SQLException             if a database access error occurs
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws NoSuchMethodException     if the specified method does not exist
     * @throws InstantiationException    if an instance of the specified class cannot be created
     * @throws IllegalAccessException    if access to the entity's field is denied
      */
    @Override
    public Iterable<E> find(Class<E> table, String condition) throws SQLException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        final String tableName = getTableName(table);
        final String finalCondition = condition != null
                ? WHERE_KEY_WORD + condition
                : "";

        final PreparedStatement findFirstStatement =
                connection.prepareStatement(String.format(FIND_ALL_WITH_CONDITION_QUERY, tableName, finalCondition));

        return getPOJOs(findFirstStatement, table);

    }


    /**
     * Finds the first entity of the specified table in the database.
     *
     * @param table the entity class representing the database table
     * @return the first entity found
     * @throws SQLException             if a database access error occurs
     * @throws NoSuchMethodException     if the specified method does not exist
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws InstantiationException    if an instance of the specified class cannot be created
     * @throws IllegalAccessException    if access to the entity's field is denied
     */
    @Override
    public E findFirst(Class<E> table) throws SQLException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        final String tableName = getTableName(table);

        final PreparedStatement findFirstStatement =
                connection.prepareStatement(String.format(FIND_FIRST_QUERY, tableName));

        return getPOJO(findFirstStatement, table);
    }

    /**
     * Finds the first entity of the specified table in the database based on the given condition.
     *
     * @param table     the entity class representing the database table
     * @param condition the condition to apply in the query
     * @return the first entity found that satisfies the condition
     * @throws SQLException             if a database access error occurs
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws InstantiationException    if an instance of the specified class cannot be created
     * @throws IllegalAccessException    if access to the entity's field is denied
     * @throws NoSuchMethodException     if the specified method does not exist
     */
    @Override
    public E findFirst(Class<E> table, String condition) throws SQLException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException {

        final String tableName = getTableName(table);
        final String finalCondition = condition != null
                ? WHERE_KEY_WORD + condition
                : "";

        final PreparedStatement findFirstStatement =
                connection.prepareStatement(String.format(FIND_FIRST_WITH_CONDITION_QUERY, tableName, finalCondition));

        return getPOJO(findFirstStatement, table);
    }

    /**
     * Creates a new table in the database based on the specified entity class.
     *
     * @param entity the entity class representing the table structure
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void doCreate(Class<E> entity) throws SQLException {
        final String tableName = getTableName(entity);

        final List<KeyValuePair> fieldsAndTypes = getAllFieldsAndDataTypes(entity);

        String fieldsWithTypesFormat = fieldsAndTypes.stream()
                .map(keyValuePair -> String.format(CREATE_VALUE_FORMAT, keyValuePair.key, keyValuePair.value))
                .collect(Collectors.joining(COMMA_SEPARATOR));

        connection.prepareStatement(String.format(CREATE_TABLE_QUERY_FORMAT, tableName, fieldsWithTypesFormat))
                .execute();
    }

    /**
     * Alters the existing table in the database to match the structure of the specified entity class.
     *
     * @param entity the entity class representing the desired table structure
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void doAlter(Class<E> entity) throws SQLException {
        final String tableName = getTableName(entity);

        final String partialAddColumnsStatement = addColumnsStatementForNewFields(entity, tableName);

        connection.prepareStatement(String.format(ALTER_TABLE_FORMAT,
                        tableName,
                        partialAddColumnsStatement))
                .executeUpdate();
    }

    /**
     * Deletes the specified entity from the database.
     *
     * @param entity the entity to delete
     * @throws SQLException          if a database access error occurs
     * @throws IllegalAccessException if access to the entity's field is denied
     */
    @Override
    public void doDelete(E entity) throws SQLException, IllegalAccessException {
        final String tableName = getTableName(entity.getClass());
        final Field idField = getIdColumn(entity.getClass());
        final String idColumnName = getSQLColumnName(idField);
        final Object fieldValue = getFieldValue(entity, idField);

        connection.prepareStatement(String.format(DELETE_RECORD_BY_CONDITION_FORMAT,
                        tableName,
                        idColumnName,
                        fieldValue))
                .executeUpdate();
    }

    /**
     * Retrieves the value of a field from the provided entity object.
     *
     * @param entity the entity object
     * @param field  the field whose value is to be retrieved
     * @return the value of the field in the entity object
     * @throws IllegalAccessException if access to the field is denied
     */
    private Object getFieldValue(E entity, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(entity);
    }

    /**
     * Generates SQL statements to add columns for new fields in the entity class.
     *
     * @param entity    the entity class
     * @param tableName the name of the database table
     * @return SQL statements to add columns for new fields
     * @throws SQLException if a database access error occurs
     */
    private String addColumnsStatementForNewFields(Class<E> entity, String tableName) throws SQLException {
        final Set<String> sqlColumnNames = getSQLColumnNames(tableName);
        final List<Field> allFieldsWithoutId = getAllFieldsWithoutId(entity);

        List<String> nonMatchingFields = new ArrayList<>();

        for (Field field : allFieldsWithoutId) {
            final String columnName = getSQLColumnName(field);

            if (sqlColumnNames.contains(columnName)) continue;

            final String columnType = getSQLType(field.getType());

            final String partialAddStatement = String.format(ADD_COLUMN_FORMAT, columnName, columnType);

            nonMatchingFields.add(partialAddStatement);
        }

        return String.join(COMMA_SEPARATOR, nonMatchingFields);
    }

    /**
     * Retrieves the names of all columns in the specified database table.
     *
     * @param tableName the name of the database table
     * @return a set containing the names of all columns in the table
     * @throws SQLException if a database access error occurs
     */
    private Set<String> getSQLColumnNames(String tableName) throws SQLException {
        Set<String> allFields = new HashSet<>();

        final PreparedStatement getAllFieldsStatement = connection.prepareStatement(GET_ALL_COLUMN_NAMES_BY_TABLE_NAME);
        getAllFieldsStatement.setString(1, tableName);

        final ResultSet allFieldsResultSet = getAllFieldsStatement.executeQuery();

        while (allFieldsResultSet.next()) {
            allFields.add(allFieldsResultSet.getString(1));
        }

        return allFields;
    }

    /**
     * Retrieves all fields and their corresponding data types in the entity class.
     *
     * @param entity the entity class
     * @return a list of key-value pairs representing fields and their data types
     */
    private List<EntityManager.KeyValuePair> getAllFieldsAndDataTypes(Class<E> entity) {
        return getAllFieldsWithoutId(entity)
                .stream()
                .map(f -> new EntityManager.KeyValuePair(getSQLColumnName(f), getSQLType(f.getType())))
                .toList();
    }

    /**
     * Retrieves the SQL column name associated with the given field.
     *
     * @param field the field in the entity class
     * @return the SQL column name
     */
    private String getSQLColumnName(Field field) {
        return field.getAnnotationsByType(Column.class)[0].name();
    }

    /**
     * Retrieves the SQL data type corresponding to the given Java type.
     *
     * @param type the Java type
     * @return the corresponding SQL data type
     */
    private String getSQLType(Class<?> type) {
        if (type == Integer.class || type == int.class || type == long.class || type == Long.class) {
            return INT;
        } else if (type == LocalDate.class) {
            return DATE;
        }

        return VARCHAR;
    }

    /**
     * Retrieves all fields in the entity class except for the primary key field.
     *
     * @param entity the entity class
     * @return a list of fields excluding the primary key
     */
    private List<Field> getAllFieldsWithoutId(Class<E> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(Column.class))
                .toList();
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param entity the entity to insert
     * @return true if the insertion was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    private boolean doInsert(E entity) throws SQLException {
        final String tableName = getTableName(entity.getClass());

        final List<EntityManager.KeyValuePair> keyValuePairs = getKeyValuePairs(entity);

        final String fields = keyValuePairs.stream()
                .map(EntityManager.KeyValuePair::key)
                .collect(Collectors.joining(COMMA_SEPARATOR));

        final String values = keyValuePairs.stream()
                .map(EntityManager.KeyValuePair::value)
                .collect(Collectors.joining(COMMA_SEPARATOR));

        final String insertQuery = String.format(INSET_QUERY_FORMAT, tableName, fields, values);

        return connection.prepareStatement(insertQuery).execute();
    }
    /**
     * Updates an existing entity in the database.
     *
     * @param entity   the entity to update
     * @param idColumn the primary key field of the entity
     * @return true if the update was successful, false otherwise
     * @throws SQLException          if a database access error occurs
     * @throws IllegalAccessException if access to the entity's field is denied
     */
    private boolean doUpdate(E entity, Field idColumn) throws SQLException, IllegalAccessException {
        final String tableName = getTableName(entity.getClass());

        final List<EntityManager.KeyValuePair> keyValuePairs = getKeyValuePairs(entity);

        final String updateValues = keyValuePairs.stream()
                .map(keyValuePair -> String.format(UPDATE_VALUE_FORMAT, keyValuePair.key, keyValuePair.value))
                .collect(Collectors.joining(COMMA_SEPARATOR));

        final int idValue = Integer.parseInt(idColumn.get(entity).toString());

        final String insertQuery = String.format(UPDATE_QUERY_BY_ID_FORMAT, tableName, updateValues, idValue);

        return connection.prepareStatement(insertQuery).execute();
    }


    /**
     * Retrieves a single entity object from the database result set.
     *
     * @param findFirstStatement the prepared statement for executing the query
     * @param table              the entity class
     * @return the entity object retrieved from the database
     * @throws SQLException             if a database access error occurs
     * @throws NoSuchMethodException     if the specified method does not exist
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws InstantiationException    if an instance of the specified class cannot be created
     * @throws IllegalAccessException    if access to the entity's field is denied
     */
    private E getPOJO(PreparedStatement findFirstStatement, Class<E> table) throws SQLException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        final ResultSet resultSet = findFirstStatement.executeQuery();
        resultSet.next();

        final E entity = table.getDeclaredConstructor().newInstance();

        fillEntity(table, resultSet, entity);

        return entity;
    }

    /**
     * Retrieves multiple entity objects from the database result set.
     *
     * @param findFirstStatement the prepared statement for executing the query
     * @param table              the entity class
     * @return a list of entity objects retrieved from the database
     * @throws SQLException             if a database access error occurs
     * @throws NoSuchMethodException     if the specified method does not exist
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws InstantiationException    if an instance of the specified class cannot be created
     * @throws IllegalAccessException    if access to the entity's field is denied
     */
    private Iterable<E> getPOJOs(PreparedStatement findFirstStatement, Class<E> table) throws SQLException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        final ResultSet resultSet = findFirstStatement.executeQuery();

        List<E> entities = new ArrayList<>();

        while (resultSet.next()) {
            final E entity = table.getDeclaredConstructor().newInstance();

            fillEntity(table, resultSet, entity);

            entities.add(entity);
        }

        return entities;
    }

    /**
     * Fills the fields of an entity object with data from the database result set.
     *
     * @param table      the entity class
     * @param resultSet  the result set containing the data
     * @param entity     the entity object to fill
     */
    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) {
        Arrays.stream(table.getDeclaredFields())
                .forEach(field -> fillFiled(field, resultSet, entity));
    }

    /**
     * Fills a specific field of an entity object with data from the database result set.
     *
     * @param field      the field to fill
     * @param resultSet  the result set containing the data
     * @param entity     the entity object to fill
     */
    private void fillFiled(Field field, ResultSet resultSet, E entity) {
        final Class<?> type = field.getType();
        field.setAccessible(true);

        try {
            if (type == int.class || type == long.class) {
                field.set(entity, resultSet.getInt(field.getName()));
                return;
            } else if (type == LocalDate.class) {
                field.set(entity, LocalDate.parse(resultSet.getString(field.getName())));
                return;
            }

            field.set(entity, resultSet.getString(field.getName()));
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves the primary key field of the specified class.
     *
     * @param clazz the class to inspect
     * @return the primary key field
     * @throws UnsupportedOperationException if the primary key field is missing
     */
    private Field getIdColumn(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(ID_COLUM_MISSING_MESSAGE));
    }

    /**
     * Retrieves the name of the database table associated with the specified class.
     *
     * @param aClass the class representing the entity
     * @return the name of the database table
     * @throws UnsupportedOperationException if the class is not annotated as an entity
     */
    private String getTableName(Class<?> aClass) {
        final Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0) throw new UnsupportedOperationException(CLASS_MUST_BE_ENTITY_MESSAGE);

        return annotationsByType[0].name();
    }

    /**
     * Retrieves key-value pairs representing the fields and their values in the entity object.
     *
     * @param entity the entity object
     * @return a list of key-value pairs representing the fields and their values
     */
    private List<EntityManager.KeyValuePair> getKeyValuePairs(E entity) {
        final Class<?> aClass = entity.getClass();

        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(Column.class))
                .map(f -> new EntityManager.KeyValuePair(f.getAnnotationsByType(Column.class)[0].name(),
                        mapFieldsToGivenType(f, entity)))
                .collect(Collectors.toList());
    }

    /**
     * Maps the value of a field to a string representation based on its type.
     *
     * @param field  the field to map
     * @param entity the entity object containing the field
     * @return a string representation of the field's value
     */
    private String mapFieldsToGivenType(Field field, E entity) {
        field.setAccessible(true);

        Object o = null;

        try {
            o = field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return o instanceof String || o instanceof LocalDate
                ? "'" + o + "'"
                : Objects.requireNonNull(o).toString();
    }

    public record KeyValuePair(String key, String value) {
    }
}