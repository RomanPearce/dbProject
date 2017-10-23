package gui;

import db.DB;
import db.WorkDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class StartFrame extends JFrame{
    private JPanel panel;
    private JLabel labelUrl, labelLogin, labelPass; private JTextField tfUrl, tfLogin;
    private JPasswordField tfPass;
    private JButton create, delete, connect;


    public StartFrame(){
        setSize(250, 250);
        setTitle("StartFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);
    }

    public void initComponents(){
        panel = new JPanel();
        labelUrl = new JLabel("URL");
        labelLogin = new JLabel("Login");
        labelPass = new JLabel("Password");
        tfUrl = new JTextField("127.0.0.1", 20);
        tfLogin = new JTextField("root", 20);
        tfPass = new JPasswordField("1234",20);
        create = new JButton("Create");
        delete = new JButton("Delete");
        connect = new JButton("Connect");
        panel.add(labelUrl);
        panel.add(tfUrl);
        panel.add(labelLogin);
        panel.add(tfLogin);
        panel.add(labelPass);
        panel.add(tfPass);
        panel.add(create); panel.add(delete); panel.add(connect);
        add(panel);
    }

    public  void action(){
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try{
                        WorkDB.create("jdbc:mysql://127.0.0.1/", tfLogin.getText(), String.valueOf(tfPass.getPassword()));
                }catch (NoSuchAlgorithmException ex) {
                    }
                } catch (UnsupportedEncodingException ex){
                    JOptionPane.showMessageDialog(panel,
                            "Database installing successfully",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (ClassNotFoundException ex){
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(panel,"Error installing database \n" + ex,"Message", JOptionPane.ERROR_MESSAGE);

                }catch (SQLException ex){
                    System.out.println(ex); JOptionPane.showMessageDialog(panel,
                            "Error installing database \n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DB db = new DB("jdbc:mysql://" + tfUrl.getText()
                            + "/" + WorkDB.NAME_DB, tfLogin.getText(),
                            tfPass.getText());
                    new LoginFrame(db);
                    dispose();
                }catch (ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(panel,
                            "The database is not installed\n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);


                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(panel,
                            "The database is not installed\n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);

                }


            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    WorkDB.delete("jdbc:mysql://127.0.0.1/", tfLogin.getText(), tfPass.getText());
                    JOptionPane.showMessageDialog(panel,
                            "Database drop successfully", "Message",
                            JOptionPane.INFORMATION_MESSAGE); } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(panel,
                            "Error drop database\n" + ex, "Error",
                            JOptionPane.ERROR_MESSAGE); } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Error drop database\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
                } }
        });

    }
}
