package ru.gb.jdk.server;

import ru.gb.jdk.user.UserGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private static final int WIDTH = 300;
    private static final int HEIGTH = 200;
    private ArrayList<UserGUI> clientGUIList;
    public static final String LOG_PATH = "src/main/java/ru/gb/jdk/log.txt";

    private static JButton btnStart = new JButton("Start");
    private static JButton btnStop = new JButton("Stop");
    private static JTextArea info = new JTextArea();
    private static boolean isServerWorking;

    public ServerWindow() {
        clientGUIList = new ArrayList<>();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGTH);
        setResizable(false);
        setTitle("Server");
        setLocationRelativeTo(null);
        creatWindow();
        setVisible(true);
    }

    public boolean connectUser(UserGUI user) {
        if (isServerWorking) {
            clientGUIList.add(user);
            return true;
        }
        return false;
    }

    public void disconnectUser(UserGUI user) {
        clientGUIList.remove(user);
        if (user != null) {
            user.disconnectToServer();
        }
    }

    public void message(String text) {
        if (isServerWorking) {
            appendInfo(text);
            answerAll(text);
            saveInfo(text);
        }
    }

    private void answerAll(String text) {
        for (UserGUI user : clientGUIList) {
            user.answer(text);
        }
    }

    private void saveInfo(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInfo() {
        return readInfo();
    }

    private String readInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH)) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void appendInfo(String text) {
        info.append(text + "\n");
    }

    private void creatWindow() {
        add(info);
        add(creatButton(), BorderLayout.SOUTH);

    }

    private Component creatButton() {
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    appendInfo("Сервер уже был запущен");
                } else {
                    isServerWorking = true;
                    appendInfo("Сервер запущен!");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    appendInfo("Сервер уже был остановлен");
                } else {
                    isServerWorking = false;
                    while (!clientGUIList.isEmpty()){
                        disconnectUser(clientGUIList.get(clientGUIList.size()-1));
                    }
                    appendInfo("Сервер остановлен!");
                }
            }
        });
        return panBottom;
    }
}
