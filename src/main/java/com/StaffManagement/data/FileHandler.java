package com.StaffManagement.data;
import com.StaffManagement.Employee;
import com.StaffManagement.interfaces.DataPersistence;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileHandler implements DataPersistence {
    private String filePath;
    private ObjectMapper objectMapper;
    private List<Employee> employees;
    public FileHandler(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.employees = new ArrayList<>();
    }

    @Override
    public void loadData() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                JsonNode file = objectMapper.readTree(inputStream);
                JsonNode employeeNodes = file.get("employees");

                for (JsonNode employeeNode : employeeNodes) {
                    int id = employeeNode.get("id").asInt();
                    String name = employeeNode.get("name").asText();
                    String department = employeeNode.get("department").asText();
                    String role = employeeNode.get("role").asText();
                    double salary = employeeNode.get("salary").asDouble();

                    employees.add(new Employee(id, name, department, role, salary));
                }

                // Add logging
                System.out.println("Number of employees loaded: " + employees.size());
            } else {
                System.err.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading data", e);
        }
    }

    @Override
    public void saveData() {
        try {
            objectMapper.writeValue(new File(filePath), employees);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }
}
