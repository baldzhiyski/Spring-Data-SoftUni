package org.example;

import org.example.entities.DoctorConsoleUI;

public class Main {
    public static void main(String[] args) {
        DoctorConsoleUI doctorUI = DoctorConsoleUI.getDoctorConsole();
        doctorUI.start();
    }
}