package ru.gb.jdk;

import ru.gb.jdk.server.DataFile;
import ru.gb.jdk.server.ServerController;

import ru.gb.jdk.server.ServerGUI;
import ru.gb.jdk.user.UserController;
import ru.gb.jdk.user.UserGUI;


public class Program {
    public static void main(String[] args) {

        DataFile file = new DataFile();
        ServerGUI serverGUI = new ServerGUI();
        ServerController serverController = new ServerController(serverGUI,file);
        serverGUI.setServerController(serverController);


        UserGUI userGUI1 = new UserGUI();
        UserGUI userGUI2 = new UserGUI();

        UserController user1 = new UserController(serverController, userGUI1);
        userGUI1.setUserController(user1);
        UserController user2 = new UserController(serverController, userGUI2);
        userGUI2.setUserController(user2);


    }
}
