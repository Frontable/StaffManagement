package com.StaffManagement;

import com.StaffManagement.interfaces.DataPersistence;
import com.StaffManagement.data.FileHandler;
import com.StaffManagement.interfaces.Searchable;

import java.util.ArrayList;
import java.util.List;

public class StaffManagementSystem implements Searchable {
    private List<Employee> employees;
    private DataPersistence dataPersistence;

    public StaffManagementSystem(DataPersistence dataPersistence) {
        this.employees = new ArrayList<>(); // Initialize the list
        this.dataPersistence = dataPersistence;
        loadData(); // Load existing data upon system initialization
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveData(); // Save data after adding a new employee
    }

    public void editEmployee(int id, Employee newDetails) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.set(i, newDetails);
                saveData(); // Save data after editing an employee
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void fireEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
        saveData(); // Save data after firing an employee
    }

    public void listEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public List<Employee> search(String keyword) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getName().contains(keyword) ||
                    String.valueOf(employee.getId()).contains(keyword) ||
                    employee.getDepartment().contains(keyword)) {
                result.add(employee);
            }
        }
        return result;
    }

    public Employee searchEmployeesById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null; // Return null if employee with the given ID is not found
    }

    private void loadData() {
        dataPersistence.loadData();
        this.employees = dataPersistence.getEmployees();

        // Add logging
        System.out.println("Number of employees loaded: " + (employees != null ? employees.size() : "null"));

        if (employees == null) {
            System.err.println("Warning: employees list is null after loading data.");
        }
    }

    private void saveData() {
        dataPersistence.saveData();
    }
}
