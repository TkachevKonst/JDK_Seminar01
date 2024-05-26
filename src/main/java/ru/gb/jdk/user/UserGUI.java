package ru.gb.jdk.user;

import ru.gb.jdk.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGTH = 400;
    private ServerWindow serverWindow;
    private boolean connected = false;
    private String name;

    private JTextArea info;
    private JTextField IPAddress, Port, Login, Message;
    private JPasswordField password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;

    public UserGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        setSize(WIDTH, HEIGTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(serverWindow.getX() - 400, serverWindow.getY());
        setResizable(false);
        setTitle("User chat");
        creatWindow();
        setVisible(true);
    }

    private void appendInfo(String text) {
        info.append(text + "\n");
    }

    private void creatWindow() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createInfo());
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3));
        IPAddress = new JTextField("127.0.0.1");
        Port = new JTextField("8080");
        Login = new JTextField("Petr");
        password = new JPasswordField("13579");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });
        headerPanel.add(IPAddress);
        headerPanel.add(Port);
        headerPanel.add(new JPanel());
        headerPanel.add(Login);
        headerPanel.add(password);
        headerPanel.add(btnLogin);
        return headerPanel;
    }

    private Component createFooterPanel() {
        JPanel footerPanel = new JPanel(new GridLayout(1, 2));
        Message = new JTextField();
        Message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    message();
                }
            }
        });
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        footerPanel.add(Message);
        footerPanel.add(btnSend, BorderLayout.EAST);

        return footerPanel;
    }

    private Component createInfo() {
        info = new JTextArea();
        info.setEditable(false);
        return new JScrollPane(info);
    }

    private void connectToServer() {
        if(serverWindow.connectUser(this)){
        connected = true;
        appendInfo("Подключение произошло успешно \n");
        name = Login.getText();
        headerPanel.setVisible(false);
        String log = serverWindow.getInfo();
        if (log != null) {
            appendInfo(log);
        } else appendInfo("Истроия сообщений пуста");

        } else appendInfo("Подключение к серверу не удалось");
    }

    public void disconnectToServer() {
        if (connected) {
            connected = false;
            headerPanel.setVisible(true);
            serverWindow.disconnectUser(this);
            appendInfo("Сервер отключен");
        }
    }

    public void answer(String text) {
        appendInfo(text);
    }

    private void message() {
        if (connected) {
            String string = Message.getText();
            if (!string.equals("")) {
                serverWindow.message(name + ": " + string);
                Message.setText("");
            }
            }else {
            Message.setText("");
            appendInfo("Сервер не подключен");
        }
    }
}
