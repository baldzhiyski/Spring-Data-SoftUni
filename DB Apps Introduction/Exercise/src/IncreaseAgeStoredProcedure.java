import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    private static final String PROCEDURE_INCREASE_AGE ="" +
            "{CALL usp_get_older (?)}";
    private static final String GET_MINION_NAME_AND_AGE_BY_ID = "SELECT m.name, m.age FROM minions_db.`minions` AS m WHERE id = ?";
    private static final String PRINT_MINION_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {

        final int minionId = new Scanner(System.in).nextInt();

        Connection connection = Util.getConnection();

        final PreparedStatement increasingStatement = connection.prepareStatement(PROCEDURE_INCREASE_AGE);
        increasingStatement.setInt(1,minionId);
        increasingStatement.executeUpdate();

        final PreparedStatement getNameAndAgeStatement = connection.prepareStatement(GET_MINION_NAME_AND_AGE_BY_ID);
        getNameAndAgeStatement.setInt(1,minionId);
        ResultSet resultSet = getNameAndAgeStatement.executeQuery();

        resultSet.next();

        String nameOfMinion = resultSet.getString(Constants.VILLAIN_MINION_NAME);
        int ageOfMinion = resultSet.getInt(Constants.VILLAIN_MINION_AGE);

        System.out.printf(PRINT_MINION_FORMAT,nameOfMinion,ageOfMinion);
    }
}
