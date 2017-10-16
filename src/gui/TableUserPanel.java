package gui;

import dao.DaoUsers;
import db.DB;
import entity.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableUserPanel extends TablePanel{
    private JPanel panel = this;
    public TableUserPanel(DB db) {
        super(db);
        super.initComponents();
        action();
    }


    @Override
    public void action() {
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(panel, "Select the line you want to remove");
                } else {
                    try {
                        db.update("update users set delete_status =1 "
                                + "where users_id ="
                                + table.getValueAt(table.getSelectedRow(), 0));
                    } catch (SQLException ex) {
                        System.out.println("Exeption TABLEUSERPANEL delete");
                    }
                    updateTable();
                }
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maxId = 0;
                    ResultSet rs = db.query("select max(users_id) from users"); if (rs.next()) {
                        maxId = rs.getInt(1); }
                    DaoUsers daoUsers = new DaoUsers(db);
                    daoUsers.insert(new Users(maxId));
                    updateTable();
                } catch (SQLException ex) { JOptionPane.showMessageDialog(panel, "error in add " + ex);
                } }
        });

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) { try {
                    int role = 0;
                    if (table.getValueAt(table.getSelectedRow(), 4).
                            toString().equalsIgnoreCase("user")) {
                        role = 1;
                    } else {
                        if (table.getValueAt(table.getSelectedRow(),
                                4).toString().
                                equalsIgnoreCase("admin")) { role = 0;
                        } else {
                            throw new SQLException("error role");
                        } }
                    int block_status = 0;
                    if (table.getValueAt( table.getSelectedRow(), 5).toString(). equalsIgnoreCase("active")) {
                        block_status = 0; } else {
                        if (table.getValueAt( table.getSelectedRow(), 5).toString(). equalsIgnoreCase("Blocked")) {
                            block_status = 1; } else {
                            throw new SQLException("" + "error block_status");
                        }
                    }
                    updateTable();
                    DaoUsers du = new DaoUsers(db);
                    du.update(new Users(Integer.valueOf(
                            table.getValueAt(table.getSelectedRow(), 0).toString()),
                            table.getValueAt(table.getSelectedRow(), 1).toString(),
                            table.getValueAt(table.getSelectedRow(), 2).toString(),
                            role,
                            block_status, Integer.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString())
                    ));
                    updateTable();
                } catch (IndexOutOfBoundsException ex ) {
                    Logger.getLogger(TablePanel.class.getName()).log(Level.SEVERE, null, ex);
                }catch (NullPointerException ex) {
                    Logger.getLogger(TablePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Incorrect data " + ex);
                        updateTable();
                    } catch (SQLException ex){
                    JOptionPane.showMessageDialog(panel, "Incorrect data " + ex);
                    updateTable();

                }
                }else{
                    JOptionPane.showMessageDialog(panel, "Select the line you want to change");
                }











            }
        });

    }



    @Override
    public void createTable() {
        try {
            table = new MyTable(db.query("select users_id,login,pass,balance,case role when 1 then 'user' when 0 then 'admin' end as role,IF(block_status=1,'Blocked','Active')as blocked_status from users where delete_status=0")){

                @Override
                public boolean isCellEditable(int row, int column) {
                if (column == 0) { return false;
                } else { return true;
                } }
        };
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error creating table\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
    } }


}
