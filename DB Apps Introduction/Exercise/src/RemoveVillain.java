import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    private static final String GET_NAME_VILLAIN_TO_BE_DELETED = "select name from villains where id = ?";
    private static final String CHECK_IF_VILLAIN_EXISTS = "select name from villains where id = ?;" ;
    private static final String DELETE_VILLAIN_FROM_VILLAINS = "delete  from villains " +
            "where id = ?";
    private static final String DELETE_VILLAIN_FROM_MAPPING_TABLE = "delete  from minions_villains " +
            "where villain_id = ?";
    private static final String GET_COUNT_OF_RELEASED_MINIONS = "select COUNT(minion_id) AS count_minions from minions_villains " +
            "where villain_id = ? " +
            "group by villain_id";

    private static final String PRINT_FORMAT_NO_SUCH_VILLAIN ="No such villain was found.";
    private static final String PRINT_FORMAT = "%s was deleted.%n%d minions released.";
    public static void main(String[] args) throws SQLException {
        int villainId = Integer.parseInt(new Scanner(System.in).nextLine());

        final Connection connection = Util.getConnection();

        PreparedStatement villainExistsStatement = connection.prepareStatement(CHECK_IF_VILLAIN_EXISTS);
        villainExistsStatement.setInt(1,villainId);
        ResultSet resultSet = villainExistsStatement.executeQuery();

        if(!resultSet.next()){
            System.out.println(PRINT_FORMAT_NO_SUCH_VILLAIN);
            connection.close();
            return;
        }

        String nameOfVillain = getNameOfVillain(connection, villainId);

        int numberOfReleasedMinions = getNumberOfReleasedMinions(connection, villainId);


        connection.setAutoCommit(false);

        removeVillainFromTables(connection, villainId);

        System.out.printf(PRINT_FORMAT,nameOfVillain,numberOfReleasedMinions);

    }

    /**
     * Retrieves the number of released minions associated with a villain.
     *
     * @param connection The database connection.
     * @param villainId  The ID of the villain.
     * @return The number of released minions.
     * @throws SQLException If a database access error occurs or this method is called on a closed connection.
     */
    private static int getNumberOfReleasedMinions(Connection connection, int villainId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_COUNT_OF_RELEASED_MINIONS);
        statement.setInt(1, villainId);
        final ResultSet resultOfReleasedMinions = statement.executeQuery();
        resultOfReleasedMinions.next();

        return resultOfReleasedMinions.getInt("count_minions");
    }

    /**
     * Retrieves the name of a villain to be deleted based on its ID.
     *
     * @param connection The database connection.
     * @param villainId  The ID of the villain.
     * @return The name of the villain.
     * @throws SQLException If a database access error occurs or this method is called on a closed connection.
     */

    private static String getNameOfVillain(Connection connection, int villainId) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement(GET_NAME_VILLAIN_TO_BE_DELETED);
        statement.setInt(1, villainId);
        final ResultSet villainResultForName = statement.executeQuery();
        villainResultForName.next();

        return villainResultForName.getString(Constants.VILLAIN_MINION_NAME);
    }

    /**
     * Removes a villain and associated entries from the database.
     *
     * @param connection The database connection.
     * @param villainId  The ID of the villain to be removed.
     * @throws SQLException If a database access error occurs, this method is called on a closed connection,
     *                      or the SQL execution fails.
     */
    private static void removeVillainFromTables(Connection connection, int villainId) throws SQLException {
        try (PreparedStatement releaseMinionsStatement =
                     connection.prepareStatement(DELETE_VILLAIN_FROM_MAPPING_TABLE);
             PreparedStatement deleteVillainStatement =
                      connection.prepareStatement(DELETE_VILLAIN_FROM_VILLAINS)) {

            releaseMinionsStatement.setInt(1, villainId);
            releaseMinionsStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();

            connection.commit();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

            connection.rollback();
        }
    }
}
