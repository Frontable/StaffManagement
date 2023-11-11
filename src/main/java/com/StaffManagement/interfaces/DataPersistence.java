package com.StaffManagement.interfaces;

import com.StaffManagement.Employee;

import java.util.List;

public interface DataPersistence {
    void loadData();
    void saveData();
    List<Employee> getEmployees();
}
