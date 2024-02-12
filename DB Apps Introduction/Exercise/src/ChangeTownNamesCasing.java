import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    private static final String UPDATE_COUNTRY_NAMES_UPPERCASE = "UPDATE towns " +
            "SET name = UPPER(name) " +
            "WHERE country = ?";
    private static final String PRINT_FORMAT = "%d towns were affected.\n";
    private static final String GET_UPDATED_COUNTRIES = "select name from towns" +
            " where country = ?";

    private static final String PRINT_FORMAT_NOT_ROWS_AFFECTED = "No town names were affected.\n";
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String countryName = scanner.nextLine();

        final Connection connection = Util.getConnection();
        final PreparedStatement statement = connection.prepareStatement(UPDATE_COUNTRY_NAMES_UPPERCASE);
        statement.setString(1,countryName);
        int countUpdatedCountries = statement.executeUpdate();

        if(countUpdatedCountries<=0){
            System.out.printf(PRINT_FORMAT_NOT_ROWS_AFFECTED);
            connection.close();
            return;
        }

        PreparedStatement namesOfUpdatedTownsStatement = connection.prepareStatement(GET_UPDATED_COUNTRIES);
        namesOfUpdatedTownsStatement.setString(1,countryName);
        ResultSet resultSet = namesOfUpdatedTownsStatement.executeQuery();

        List<String> namesOfCountries = getNamesOfUpdatedCountries(resultSet);

        printResult(countUpdatedCountries, namesOfCountries);

        connection.close();
    }

    private static void printResult(int countUpdatedCountries, List<String> namesOfCountries) {
            System.out.printf(PRINT_FORMAT, countUpdatedCountries);
            System.out.println(namesOfCountries);
    }

    private static List<String> getNamesOfUpdatedCountries(ResultSet resultSet) throws SQLException {
        List<String> namesOfCountries = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            namesOfCountries.add(name);
        }
        return namesOfCountries;
    }

}
