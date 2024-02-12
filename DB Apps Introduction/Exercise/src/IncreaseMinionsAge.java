import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class IncreaseMinionsAge {
    private static final String GET_MINIONS_BY_ID ="select * from minions where id in ";
    private static final String GET_ALL_MINIONS = "select * from minions ";
    private static final String PRINT_FORMAT = "%d %s %d%n";
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final String ids = scanner.nextLine()
                .replaceAll(" ",",");

        final Connection connection = Util.getConnection();

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_BY_ID + "(" + ids + ")",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
        ResultSet resultSet = minionsStatement.executeQuery();

        while (resultSet.next()){
            final String minionNameLowerCase = resultSet.getString(Constants.VILLAIN_MINION_NAME).toLowerCase();
            final int increasedAgeMinion = resultSet.getInt(Constants.VILLAIN_MINION_AGE) + 1;

            resultSet.updateString(Constants.VILLAIN_MINION_NAME, minionNameLowerCase);
            resultSet.updateInt(Constants.VILLAIN_MINION_AGE, increasedAgeMinion);

            resultSet.updateRow();
        }
        PreparedStatement allMinionsStatement = connection.prepareStatement(GET_ALL_MINIONS);
        ResultSet allMinions = allMinionsStatement.executeQuery();

        while (allMinions.next()){
            final int minionId = allMinions.getInt("id");
            final String minionName = allMinions.getString(Constants.VILLAIN_MINION_NAME);
            final int minionAge = allMinions.getInt(Constants.VILLAIN_MINION_AGE);

            System.out.printf(PRINT_FORMAT, minionId, minionName, minionAge);
        }

    }

}
