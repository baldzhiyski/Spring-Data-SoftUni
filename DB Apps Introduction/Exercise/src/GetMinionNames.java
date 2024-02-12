import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMinionNames {
    private static final  String GET_ALL_MINIONS_INFO_BY_VILLAIN_ID = "SELECT m.name , m.age " +
            " FROM minions_villains AS mv" +
            " JOIN minions AS m ON mv.minion_id = m.id" +
            " WHERE mv.villain_id = ?";
    private static final  String GET_INFO_ABOUT_VILLAIN = "SELECT v.name " +
            "FROM villains AS v " +
            "WHERE v.id = ?";
    private static final String PRINT_FORMAT_VILLAIN = "Villain: %s%n";
    private static final String PRINT_FORMAT_MINION_INFO = "%d. %s %d%n";
    private static final String PRINT_FORMAT_NO_VILLAIN_FOUNDED = "No villain with ID %d exists in the database.\n";
    private static final int VILLAIN_ID = 2;

    public static void main(String[] args) throws SQLException {
        Connection connection = Util.getConnection();

        final PreparedStatement villainStatement = connection.prepareStatement(GET_INFO_ABOUT_VILLAIN);
        villainStatement.setInt(1,VILLAIN_ID);
        ResultSet villainResult = villainStatement.executeQuery();

        if(!villainResult.next()){
            System.out.println(PRINT_FORMAT_NO_VILLAIN_FOUNDED);
            connection.close();
            return;
        }

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_ALL_MINIONS_INFO_BY_VILLAIN_ID);
        minionsStatement.setInt(1,VILLAIN_ID);
        ResultSet minionsResult = minionsStatement.executeQuery();

        printResults(villainResult, minionsResult);
        connection.close();
    }

    private static void printResults(ResultSet villainResult, ResultSet minionsResult) throws SQLException {
        System.out.printf(PRINT_FORMAT_VILLAIN, villainResult.getString(Constants.VILLAIN_MINION_NAME));

        for (int index = 1; minionsResult.next(); index++){
            System.out.printf(PRINT_FORMAT_MINION_INFO,index,
                    minionsResult.getString(Constants.VILLAIN_MINION_NAME),
                    minionsResult.getInt(Constants.VILLAIN_MINION_AGE));
        }
    }
}
