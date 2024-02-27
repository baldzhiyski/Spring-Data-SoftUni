package org.example.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class DoctorConsoleUI {
    private Scanner scanner;
    private EntityManager entityManager;

    private static boolean IS_INITIALISED = false;

    private DoctorConsoleUI() {
        scanner = new Scanner(System.in);
        this.entityManager = Persistence.createEntityManagerFactory("hospital")
                .createEntityManager();
    }

    public void displayMenu() {
        System.out.println("Welcome, Doctor!");
        System.out.println("1. Register Patient");
        System.out.println("2. View Patient's Visitations");
        System.out.println("3. Add Diagnosis");
        System.out.println("4. Prescribe Medicament");
        System.out.println("0. Exit");
    }

    public static DoctorConsoleUI getDoctorConsole(){
        if(!IS_INITIALISED){
            return new DoctorConsoleUI();
        }
        throw new IllegalArgumentException("The Doctor console is already initialised");
    }
    public void start() {
        int choice;
        do {
            displayMenu();
            choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    viewPatientVisitations();
                    break;
                case 3:
                    addDiagnosis();
                    break;
                case 4:
                    prescribeMedicament();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    private void registerPatient() {
        System.out.println("Let's the register begin!");
        System.out.print("Enter the first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter the last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter the address: ");
        String address = scanner.nextLine();

        System.out.print("Enter the email: ");
        String email = scanner.nextLine();

        System.out.print("Does the patient have medical insurance? (true/false): ");
        boolean hasMedicalInsurance = Boolean.parseBoolean(scanner.nextLine());

        entityManager.getTransaction().begin();

        // Create a new Patient entity
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAddress(address);
        patient.setEmail(email);
        patient.setMedicalInsurance(hasMedicalInsurance);
       
        entityManager.persist(patient);

        entityManager.getTransaction().commit();
        System.out.println("Patient registered successfully!");

    }

    private void viewPatientVisitations() {
        System.out.println("Viewing Patient's Visitations...");
        // Logic to view patient's visitations (interact with database)
    }

    private void addDiagnosis() {
        System.out.println("Adding Diagnosis...");
        // Logic to add a diagnosis (interact with database)
    }

    private void prescribeMedicament() {
        System.out.println("Prescribing Medicament...");
        // Logic to prescribe a medicament (interact with database)
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // Consume invalid input
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        return input;
    }
}