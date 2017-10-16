package gui;

import dao.DaoOrders;
import dao.DaoUsers;
import db.DB;
import entity.Orders;
import entity.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;



public class TableOrdersPanel extends TablePanel {
    private JButton detailed;
    private JPanel panel = this;
    public TableOrdersPanel(DB db) { super(db);
        initComponents();
        action();
    }
    @Override
    public void initComponents() {
        super.initComponents();
        detailed = new JButton("Detailed");
        change.setText("Change status");
        detailed.setBounds(20, 350, 200, 20);
        remove(add);
        remove(delete);
        add(detailed);
        updateUI();
    }

    @Override
    public void action() {
        detailed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame goodsInOrdersFrame = new JFrame("Goods in orders");
                goodsInOrdersFrame.setSize(300, 280);
                goodsInOrdersFrame.setLocationRelativeTo(null);
                goodsInOrdersFrame.add(new
                        TableGoodsInOrdersPanel(db,Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString())));
                goodsInOrdersFrame.setVisible(true);
                goodsInOrdersFrame.setResizable(false);
            }
        });

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) { DaoOrders dor = new DaoOrders(db); try {
                    dor.update(new Orders(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()),
                            table.getValueAt(table.getSelectedRow(), 3).toString())); DaoUsers daoUser = new DaoUsers(db);
                    if (table.getValueAt(table.getSelectedRow(),
                            3).toString().equals("reject")) { daoUser.updateBalance(new
                            Users(table.getValueAt(table.getSelectedRow(), 1).toString(),
                            Integer.valueOf(table.getValueAt(table.getSelectedRow(), 2).toString()))); }
                } catch (SQLException ex) { JOptionPane.showMessageDialog(panel, "Incorrect data. Input prosessing, payed or reject"); }
                        updateTable(); } else {
                    JOptionPane.showMessageDialog(panel, "Select the line you want to change");
                }
            }
        });
    }

    @Override
    public void createTable() {
        try{
            table = new MyTable(db.query("select orders.orders_id,users.login, orders.total_cost,payment from orders JOIN users ON orders.users_id=users.users_id order by orders.orders_id")){
                @Override
                public boolean isCellEditable(int row, int column) { if (column == 3) {
                    return true; } else {
                    return false; }
                }
            };

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error creating table\n" + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);

            }


    }
}
