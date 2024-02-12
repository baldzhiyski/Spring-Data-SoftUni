import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    private static final String GET_VILLAINS_NAMES = "SELECT v.name, COUNT( DISTINCT mv.minion_id) AS 'minion_count'" +
            " FROM villains AS v\n" +
            " JOIN minions_db.minions_villains mv on v.id = mv.villain_id\n" +
            " GROUP BY mv.villain_id\n" +
            " HAVING minion_count > ? " +
            "ORDER BY minion_count DESC";
    private static final String COLUMN_LABEL_COUNT_OF_MINIONS = "minion_count";
    public static void main(String[] args) throws SQLException {


        Connection connection = Util.getConnection();
        PreparedStatement villainNamesStatement = connection.prepareStatement(GET_VILLAINS_NAMES);
        villainNamesStatement.setInt(1,15);

        ResultSet resultSet = villainNamesStatement.executeQuery();

        while (resultSet.next()){
            printResults(resultSet);
        }
        connection.close();
    }

    private static void printResults(ResultSet resultSet) throws SQLException {
        System.out.println(resultSet.getString(Constants.VILLAIN_MINION_NAME)+ " " +
                resultSet.getInt(COLUMN_LABEL_COUNT_OF_MINIONS));
    }
}
