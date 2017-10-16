package gui;

import dao.DaoUsers;
import db.DB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame{
    private JPanel panel;
    private JLabel labelLogin, labelPass;
    private JTextField tfLogin;
    private JPasswordField tfPass;
    private JButton enter, registration;
    private DB db;
    private LoginFrame login;


    public LoginFrame(DB db){
        this.db = db;
        setSize(250, 230);
        setTitle("LoginFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponets();
        action();
        setResizable(false);
        setVisible(true);
        login = this;
    }

    public void initComponets(){
        panel = new JPanel();
        labelLogin = new JLabel("Login");
        labelPass = new JLabel("Password");
        tfLogin = new JTextField("admin", 20);
        tfPass = new JPasswordField("1234", 20);
        enter = new JButton("Enter");
        registration = new JButton("Registration");
        panel.add(labelLogin);
        panel.add(tfLogin);
        panel.add(labelPass);
        panel.add(tfPass);
        panel.add(enter);
        panel.add(registration);
        add(panel);
    }

    public void action(){
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = db.query("select * from users where login='"
                            + tfLogin.getText() + "' and delete_status=0");


                    if (rs.next()) {

                        if (String.valueOf(tfPass.getPassword()).equals(rs.getString("pass"))) {
                            DaoUsers du = new DaoUsers(db);
                            if (rs.getInt("role") == 1) {
                                try {

                                    new UserFrame(db, du.getUser(rs), login);
                                } catch (NoSuchAlgorithmException e1) {
                                    e1.printStackTrace();
                                } catch (UnsupportedEncodingException e1) {
                                    e1.printStackTrace();
                                }
                                dispose();
                            } else if (rs.getInt("role") == 0) {
                                new AdminFrame(db);
                                dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(panel,
                                    "Incorrect password", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "Incorrect login",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(panel,"Error in db " + ex,
                    "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationFrame(db);
                dispose();
            }
        });



        }
}



