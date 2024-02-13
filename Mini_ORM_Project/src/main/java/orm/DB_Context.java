package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DB_Context<T> {
    boolean persist(T entity) throws SQLException, IllegalAccessException;
    Iterable<T> find(Class<T> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    Iterable<T> find(Class<T> entityType,String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    T findFirst(Class<T> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    T findFirst(Class<T> entityTyp,String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
