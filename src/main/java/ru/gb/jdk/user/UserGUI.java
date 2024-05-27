package ru.gb.jdk.user;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserGUI extends JFrame implements Viev {
    private static final int WIDTH = 400;
    private static final int HEIGTH = 400;



    private JTextArea info;
    private JTextField IPAddress, Port, Login, Message;
    private JPasswordField password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public UserGUI() {
        setting();
        creatWindow();
        setVisible(true);
    }

    void setting() {
        setSize(WIDTH, HEIGTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("User chat");
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
                login();
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

    private void login() {
        if (userController.connectToServer(Login.getText())) {
            headerPanel.setVisible(false);
        }
    }

    public void showMessage(String text) {
        info.append(text + "\n");
    }


    public void disconnectToServer() {
        headerPanel.setVisible(true);
    }


    private void message() {
        userController.message(Message.getText());
        Message.setText("");
    }
}
