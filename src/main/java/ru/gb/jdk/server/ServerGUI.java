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

public class ServerGUI extends JFrame implements Viev {
    private static final int WIDTH = 300;
    private static final int HEIGTH = 200;

    private ServerController serverController;

    private JButton btnStart = new JButton("Start");
    private JButton btnStop = new JButton("Stop");
    private JTextArea info = new JTextArea();

    public ServerGUI() {
        setting();
        creatWindow();
        setVisible(true);
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    private void setting() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGTH);
        setResizable(false);
        setTitle("Server");
        setLocationRelativeTo(null);
    }

    public void showMessage(String text) {
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
                if (serverController.isServerWorking()) {
                    showMessage("Сервер уже был запущен");
                } else {
                    serverController.setServerWorking(true);
                    showMessage("Сервер запущен!");
                }
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!serverController.isServerWorking()) {
                    showMessage("Сервер уже был остановлен");
                } else {
                    serverController.setServerWorking(false);
                    while (serverController.isEmptyUsersList()) {
                        serverController.disconnectUser();
                    }
                    showMessage("Сервер остановлен!");
                }
            }
        });
        return panBottom;
    }

}
