import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddMinion {
    private static final String GET_VILLAIN_BY_NAME = "select id from villains where name = ? ";
    private static final String INSERT_TOWN_OF_MINION ="INSERT INTO towns(name) VALUES (?)";
    private static final String GET_TOWN_BY_NAME = "select id from towns where name = ? ";
    private static final String INSERT_VILLAIN_NOT_PRESENT ="INSERT  INTO villains(name, evilness_factor) VALUES (?,?)";
    private static final String DEFAULT_EVILNESS_FACTOR = "evil";
    private static final String INSERT_MINION = "INSERT  INTO minions(name, age, town_id) VALUES (?,?,?)";
    private static final String INSERT_MINION_SERVANT_OF_VILLAIN = "INSERT INTO minions_villains(minion_id, villain_id) VALUES (?,?)";
    private static final String GET_LAST_MINION_BY_NAME =
            "select id from minions where name = ? order by id desc limit 1";
    private static final String PRINT_FORMAT_SUCCESSFULLY_ADDED_VILLAIN = "Villain %s was added to the database.\n";
    private static final String PRINT_FORMAT_SUCCESSFULLY_ADDED_TOWN = "Town %s was added to the database.\n";
    private static final String PRINT_FORMAT_SUCCESSFULLY_ADDED_MINION_SERVANT = "Successfully added %s to be minion of %s.\n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Util.getConnection();

        final Scanner scanner = new Scanner(System.in);

        final String[] minionsInfo = scanner.nextLine().split(" ");
        final String minionName = minionsInfo[1];
        final int minionAge = Integer.parseInt(minionsInfo[2]);
        final String minionTownName = minionsInfo[3];

        final String villainName = scanner.nextLine().split(" ")[1];


        int townId = getEntryId(connection,
                List.of(minionTownName),
                GET_TOWN_BY_NAME,
                INSERT_TOWN_OF_MINION,
                PRINT_FORMAT_SUCCESSFULLY_ADDED_TOWN);

        int villainId = getEntryId(connection,
                List.of(villainName, DEFAULT_EVILNESS_FACTOR),
                GET_VILLAIN_BY_NAME,
                INSERT_VILLAIN_NOT_PRESENT,
                PRINT_FORMAT_SUCCESSFULLY_ADDED_VILLAIN);

        final PreparedStatement minionStatement = connection.prepareStatement(INSERT_MINION);
        minionStatement.setString(1,minionName);
        minionStatement.setInt(2,minionAge);
        minionStatement.setInt(3,townId);

        minionStatement.executeUpdate();

        final PreparedStatement addedMinion = connection.prepareStatement(GET_LAST_MINION_BY_NAME);
        addedMinion.setString(1,minionName);

        final ResultSet resultOfMinion = addedMinion.executeQuery();
        resultOfMinion.next();

        final int addedMinionId = resultOfMinion.getInt("id");

        final PreparedStatement servantStatement = connection.prepareStatement(INSERT_MINION_SERVANT_OF_VILLAIN);
        servantStatement.setInt(1,addedMinionId);
        servantStatement.setInt(2,villainId);

        servantStatement.executeUpdate();

        System.out.printf(PRINT_FORMAT_SUCCESSFULLY_ADDED_MINION_SERVANT,minionName,villainName);

        connection.close();
    }
    /**
     * Retrieves the ID of an entry from the database, either by querying or inserting it.
     *
     * @param connection   The database connection.
     * @param arguments    A list of arguments to be used in the query or insert statement.
     * @param selectQuery  The SQL query used to retrieve the entry.
     * @param insertQuery  The SQL query used to insert the entry if it doesn't exist.
     * @param printFormat  The format string used to print a success message if the entry is inserted.
     * @return The ID of the entry retrieved or inserted.
     * @throws SQLException If an SQL exception occurs.
     */
    private static int getEntryId(Connection connection, List<String> arguments, String selectQuery, String insertQuery, String printFormat) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement(selectQuery);
        final String name = arguments.get(0);
        statement.setString(1, name);

        final ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

            for (int i = 1; i <= arguments.size(); i++) {
                insertStatement.setString(i, arguments.get(i - 1));
            }

            insertStatement.executeUpdate();

            final ResultSet afterUpdate = statement.executeQuery();
            afterUpdate.next();

            System.out.printf(printFormat,name);

            return afterUpdate.getInt("id");
        }

        return resultSet.getInt("id");
    }
}
