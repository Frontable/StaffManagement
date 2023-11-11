package com.StaffManagement.CLI;

import com.StaffManagement.Employee;
import com.StaffManagement.StaffManagementSystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class StateManager {
    private StaffManagementSystem staffManagementSystem;
    private Scanner scanner;

    public StateManager(StaffManagementSystem staffManagementSystem) {
        this.staffManagementSystem = staffManagementSystem;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    editEmployee();
                    break;
                case 3:
                    fireEmployee();
                    break;
                case 4:
                    listEmployees();
                    break;
                case 5:
                    searchEmployees();
                    break;
                case 6:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n===== Employee Management System =====");
        System.out.println("1. Add Employee");
        System.out.println("2. Edit Employee");
        System.out.println("3. Fire Employee");
        System.out.println("4. List Employees");
        System.out.println("5. Search Employees");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return choice;
    }

    private void addEmployee() {
        System.out.println("=== Add Employee ===");
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter employee start date (yyyy-MM-dd): ");
        LocalDate startDate = parseDate(scanner.nextLine());

        System.out.print("Enter employee end date (yyyy-MM-dd, or leave empty for ongoing employment): ");
        LocalDate endDate = parseDate(scanner.nextLine());

        System.out.print("Enter employee department: ");
        String department = scanner.nextLine();

        System.out.print("Enter employee role: ");
        String role = scanner.nextLine();

        System.out.print("Enter employee salary: ");
        double salary = Double.parseDouble(scanner.nextLine());

        Employee newEmployee = new Employee(id, name, department, role, salary);
        staffManagementSystem.addEmployee(newEmployee);
        System.out.println("Employee added successfully!");
    }

    private void editEmployee() {
        System.out.println("=== Edit Employee ===");
        System.out.print("Enter employee ID to edit: ");
        int idToEdit = Integer.parseInt(scanner.nextLine());

        // Retrieve the employee based on the ID
        Employee existingEmployee = staffManagementSystem.searchEmployeesById(idToEdit);

        if (existingEmployee != null) {
            // Prompt for new details
            System.out.print("Enter new name (leave empty to keep existing): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingEmployee.setName(newName);
            }

            System.out.print("Enter new department (leave empty to keep existing): ");
            String newDepartment = scanner.nextLine();
            if (!newDepartment.isEmpty()) {
                existingEmployee.setDepartment(newDepartment);
            }

            System.out.print("Enter new role (leave empty to keep existing): ");
            String newRole = scanner.nextLine();
            if (!newRole.isEmpty()) {
                existingEmployee.setRole(newRole);
            }

            System.out.print("Enter new salary (leave empty to keep existing): ");
            String newSalaryInput = scanner.nextLine();
            if (!newSalaryInput.isEmpty()) {
                double newSalary = Double.parseDouble(newSalaryInput);
                existingEmployee.setSalary(newSalary);
            }

            // Update the employee
            staffManagementSystem.editEmployee(idToEdit, existingEmployee);
            System.out.println("Employee edited successfully!");
        } else {
            System.out.println("Employee with ID " + idToEdit + " not found.");
        }
    }

    private void fireEmployee() {
        System.out.println("=== Fire Employee ===");
        System.out.print("Enter employee ID to fire: ");
        int idToFire = Integer.parseInt(scanner.nextLine());

        // Check if the employee exists before firing
        Employee existingEmployee = staffManagementSystem.searchEmployeesById(idToFire);

        if (existingEmployee != null) {
            staffManagementSystem.fireEmployee(idToFire);
            System.out.println("Employee with ID " + idToFire + " has been fired.");
        } else {
            System.out.println("Employee with ID " + idToFire + " not found.");
        }
    }

    private void listEmployees() {
        System.out.println("=== List of Employees ===");
        staffManagementSystem.listEmployees();
    }


    private void searchEmployees() {
        System.out.println("=== Search Employees ===");
        System.out.print("Enter a keyword to search: ");
        String keyword = scanner.nextLine();
        staffManagementSystem.search(keyword).forEach(System.out::println);
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use the format yyyy-MM-dd.");
            return null;
        }
    }
}

