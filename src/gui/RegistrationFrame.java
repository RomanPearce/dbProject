package gui;

import dao.DaoUsers;
import db.DB;
import entity.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationFrame extends JFrame{
    private JPanel panel;
    private JLabel labelLogin, labelPass;
    private JTextField tfLogin, tfPass;
    private JButton enter, registration;
    private DB db;



    public RegistrationFrame(DB db) {
        this.db = db;
        setSize(250, 230);
        setTitle("RegistrationFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);


    }

    private void initComponents() {
        panel = new JPanel();
        labelLogin = new JLabel("Login");
        labelPass = new JLabel("Password"); tfLogin = new JTextField(20);
        tfPass = new JTextField(20);
        registration = new JButton("Registration"); panel.add(labelLogin);
        panel.add(tfLogin); panel.add(labelPass); panel.add(tfPass); panel.add(registration); add(panel);
    }


    private void action(){
        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tfLogin.getText().equals("") || tfPass.getText().equals("")){
                    JOptionPane.showMessageDialog(panel,"Fill all fileds please", "Error", JOptionPane.ERROR_MESSAGE);
                } else{
                    try {
                    ResultSet rs = db.query("SELECT * FROM users " + "WHERE login='" + tfLogin.getText() + "'");

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(panel, "This user already exist", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            DaoUsers daoUser = new DaoUsers(db);
                            daoUser.insert(new Users(tfLogin.getText(), tfPass.getText(), 1, 0, 0, 0));

                            JOptionPane.showMessageDialog(panel, "You have registred!", "YO", JOptionPane.INFORMATION_MESSAGE);
                            new LoginFrame(db);
                            dispose();
                        }
                    }catch (SQLException e1){
                        System.out.println("Exeption in REGISTRATION in RegistrationFrame class");
                    }



                }
            }
        });
    }

}
