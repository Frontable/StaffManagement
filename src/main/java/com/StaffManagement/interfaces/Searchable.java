package com.StaffManagement.interfaces;

import com.StaffManagement.Employee;

import java.util.List;

public interface Searchable {
    List<Employee> search(String keyword);
}
