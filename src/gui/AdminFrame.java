package gui;

import db.DB;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {
    public DB db;
    private TablePanel panelUsers, panelGoods, panelOrders;
    private JTabbedPane tabbedTables;
    private JButton logout;
    private JPanel panel;

    public AdminFrame(DB db){
        this.db = db;
        setSize(750, 480);
        setTitle("AdminFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        action();
        setVisible(true);
    }

    public void initComponents(){
        panelUsers = new TableUserPanel(db);
        panel = new JPanel();
        panelGoods = new TableGoodsPanel(db);
        panelOrders = new TableOrdersPanel(db);
        tabbedTables = new JTabbedPane();

        logout = new JButton("LogOut");
        panel.add(logout);
        tabbedTables.add("Users", panelUsers);
        tabbedTables.add("Goods", panelGoods);
        tabbedTables.add("Orders", panelOrders);
        tabbedTables.add("Info",panel);


        add(tabbedTables);


    }

    public void action(){
        tabbedTables.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panelUsers.updateTable();
                panelGoods.updateTable();
                panelOrders.updateTable();
            }
        });
    logout.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoginFrame(db);
            dispose();
        }
    });
    }


}
