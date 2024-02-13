import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Connector.createConnection("root","123","soft_uni");
        Connection connection = Connector.getConnection();

        EntityManager<User> userManager = new EntityManager<>(connection);

        User user = new User("First",29, LocalDate.now());

        User first = userManager.findFirst(User.class);

        User first1 = userManager.findFirst(User.class, "id = 1");


       userManager.find(User.class,"age > 18 AND registration > '2022-06-06' ")
               .forEach(System.out::println);




    }

}
