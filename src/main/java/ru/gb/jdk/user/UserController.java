package ru.gb.jdk.user;

import ru.gb.jdk.server.ServerController;

public class UserController {

    private boolean connected;
    private ServerController serverController;
    private String name;
    private Viev viev;

    public UserController(ServerController serverController, Viev viev) {
        this.serverController = serverController;
        this.viev = viev;

    }



    private void showInWindow(String text) {
        viev.showMessage(text);
    }

    public void disconnectedToServer() {
        if (connected) {
            connected = false;
            viev.disconnectToServer();
            showInWindow("Сервер отключен");
        }
    }

    public void disconnectToServer() {
        serverController.disconnectUser(this);
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (serverController.connectUser(this)) {
            connected = true;
            showInWindow("Подключение произошло успешно \n");
            String log = serverController.getInfo();
            if (log != null) {
                showInWindow(log);
            } else showInWindow("Истроия сообщений пуста");
            return true;
        } else {
            showInWindow("Подключение к серверу не удалось");
            return false;
        }
    }

    public void answer(String text) {
        showInWindow(text);
    }

    public void message(String text) {
        if (connected) {
            if (!text.isEmpty()) {
                serverController.message(name + ": " + text);
            }
        } else {
            showInWindow("Сервер не подключен");
        }
    }
}
