package ru.gb.jdk.server;

import ru.gb.jdk.user.UserController;
import ru.gb.jdk.user.UserGUI;

import java.util.ArrayList;

public class ServerController {


    private Viev viev;
    private DataBase dataBase;
    private boolean work = false;
    private ArrayList<UserController> userControllers;



    public ServerController (Viev viev, DataBase dataBase){
        userControllers = new ArrayList<>();
        this.viev = viev;
        this.dataBase = dataBase;
    }
    public boolean isServerWorking() {
        if (work)return true;
        else return false;
    }

    public void setServerWorking(boolean serverWorking) {
        work = serverWorking;
    }

    public boolean connectUser(UserController userController) {
        if (work) {
            userControllers.add(userController);
            return true;
        }
        return false;
    }
    public void disconnectUser() {
        UserController user = userControllers.get(userControllers.size()-1);
        userControllers.remove(userControllers.get(userControllers.size()-1));
        if (user != null) {
            user.disconnectedToServer();
        }
    }

    public void disconnectUser(UserController user) {
        userControllers.remove(user);
        if (user != null) {
            user.disconnectedToServer();
        }
    }


    public boolean isEmptyUsersList(){
        if (!userControllers.isEmpty()) return true;
        else return false;
    }
    private void showAllUsers(String text) {
        for (UserController userController : userControllers) {
            userController.answer(text);
        }
    }
    public void message(String text) {
        if (work) {
            showInfo(text);
            showAllUsers(text);
            saveLog(text);
        }
    }

    public String getInfo() {
        return dataBase.readInfo();
    }

    private void saveLog(String text){
        dataBase.saveInfo(text);
    }

    private void showInfo(String text){
        viev.showMessage(text);
    }

}
