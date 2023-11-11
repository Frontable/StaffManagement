package com.StaffManagement;

import com.StaffManagement.CLI.StateManager;
import com.StaffManagement.data.FileHandler;
import com.StaffManagement.interfaces.DataPersistence;

public class Main {
    public static void main(String[] args) {

        DataPersistence dataPersistence = new FileHandler("employees.json");
        StaffManagementSystem staffManagementSystem = new StaffManagementSystem(dataPersistence);
        StateManager stateManager = new StateManager(staffManagementSystem);
        stateManager.start();
    }
}