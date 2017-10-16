package gui;


import dao.DaoGoods;
import db.DB;
import entity.Goods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableGoodsPanel  extends TablePanel{
    private JPanel panel = this;
    private JFileChooser chooser;

    public TableGoodsPanel(DB db) { super(db);
        initComponents();
        action();
    }
    @Override
    public void initComponents() {
        super.initComponents();
       // chooser = new JFileChooser();
        //FileNameExtensionFilter filter = new FileNameExtensionFilter(
        //        "Images", "jpg", "png", "jpeg"); chooser.setFileFilter(filter);
    }

    @Override
    public void action() {
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(panel, "Select the line you want to remove");
                    } else {
                        db.update("update goods set delete_status =1 " + "where goods_id ="
                            + table.getValueAt(table.getSelectedRow(), 0));
                        updateTable();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "error in delete " + ex); }
            }
        });


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maxId = 0;
                    ResultSet rs = db.query("select max(goods_id) from goods");
                    if (rs.next()) {
                        maxId = rs.getInt(1);
                    }
                    DaoGoods daoGoods = new DaoGoods(db); daoGoods.insert(new Goods(maxId));
                    updateTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "error in add " + ex);
                }
            }
        });

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    try {
                        DaoGoods dg = new DaoGoods(db);
                        dg.update(new
                                Goods(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()),
                                table.getValueAt(table.getSelectedRow(), 1).toString(), Integer.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString()),
                                table.getValueAt(table.getSelectedRow(), 2).toString()));
                        chooser.showOpenDialog(panel);
                        if (chooser.getSelectedFile() != null) {
                            dg.setImagePath(new
                                    Goods(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()), chooser.getSelectedFile().getName()));
                        }
                        updateTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Incorrect data " + ex);
                        updateTable();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Incorrect data " + ex);
                        updateTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Select the line you want to change");
                }

            }
        });

    }

    @Override
    public void createTable() {
        try {
            table = new MyTable(db.query("select goods_id,name, description, price from goods where delete_status=0")) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) { return false;
                    } else { return true;
                    } }
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating table\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
