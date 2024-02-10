package com.company;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Properties properties = new Properties();

        properties.setProperty("user","root");
        properties.setProperty("password","123");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo",properties);


        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter last_name: ");
        String lastName = scanner.nextLine();

        PreparedStatement query = connection
                .prepareStatement("SELECT user_name,first_name,last_name , " +
                        "(SELECT COUNT(*) FROM users_games WHERE user_id = u.id) AS game_count " +
                        "FROM users AS u " +
                        "WHERE user_name =? AND last_name = ?");
        query.setString(1,username);
        query.setString(2,lastName);

        ResultSet result = query.executeQuery();


        if(result.next()){
            // Valid Username
            String dbUsername = result.getString("user_name");
            String dbFirstName = result.getString("first_name");
            String dbLastName = result.getString("last_name");
            int dbGamesCount = result.getInt("game_count");

            System.out.printf("User: %s%n%s %s has played %d games.",dbUsername,dbFirstName,
                    dbLastName,dbGamesCount);
        }else{
            System.out.println("No such user exists!");
        }

    }
}
