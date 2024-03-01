package bg.softuni.usersystem;


import bg.softuni.usersystem.domain.entities.User;
import bg.softuni.usersystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;

    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1 for seeing emails . Enter 2 for updating users.");
        int number = scanner.nextInt();

        switch (number){
            case 1:
                System.out.println("Give a provider : ");
                String provider = scanner.nextLine();

                printAllEmails(provider);
                break;
            case 2:
                System.out.println("Give a date : ");
                LocalDate date = LocalDate.parse(scanner.nextLine());
                this.userService.setFieldIsDeletedTrueAllUsersNotLoggedAfterDate(date);
                break;
            default:throw new RuntimeException("Invalid input");
        }

    }

    private void printAllEmails(String provider) {
        this.userService.getUsersByEmailProvider(provider)
                .stream().map(User::getEmail)
                .forEach(System.out::println);
    }
}