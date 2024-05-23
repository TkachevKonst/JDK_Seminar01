package ru.gb.jdk;

import ru.gb.jdk.server.ServerWindow;
import ru.gb.jdk.user.UserGUI;

public class Program {
    public static void main(String[] args) {
    ServerWindow serverWindow = new ServerWindow();
    UserGUI user1 = new UserGUI(serverWindow);
    UserGUI user2 = new UserGUI(serverWindow);

    }
}
