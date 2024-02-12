import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintAllMinionNames {
    private static final String SELECT_MINION_NAMES_ASC = "select name from minions order by name asc ";

    /**
     * Retrieves all minion names from the database, alternately prints them in a zigzag pattern,
     * starting from both ends of the result set towards the center.
     *
     * @throws SQLException If a database access error occurs or this method is called on a closed connection.
     */
    public static void main(String[] args) throws SQLException {
        final Connection connection = Util.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SELECT_MINION_NAMES_ASC,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        final ResultSet minionsResultSet = statement.executeQuery();

        int minionsCount = 0;

        // Counting total minions in the database
        while (minionsResultSet.next()) minionsCount++;

        // Reset the cursor to before the first row to iterate through the result set again
        minionsResultSet.beforeFirst();

        int firstIndex = 1;
        int lastIndex = minionsCount;

        for (int i = 1; i < minionsCount + 1; i++) {
            if (i % 2 == 0) {
                minionsResultSet.absolute(firstIndex);
                firstIndex++;
            } else {
                minionsResultSet.absolute(lastIndex);
                lastIndex--;
            }

            System.out.println(minionsResultSet.getString("name"));

            minionsResultSet.next();
        }
    }
}
