package gui;

import dao.DaoGoods;
import dao.DaoOrders;
import dao.DaoUsers;
import db.DB;
import entity.Goods;
import entity.Orders;
import entity.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserFrame extends JFrame{
    private DB db;
    private JPanel panel;
    private JScrollBar scroll;
    private MyTable tableUser;
    private JButton deleteUser, addUser;
    private KeyAdapter key;
    private JPanel itemList;
    private JScrollPane scrollPane;
    private JButton logout;
    private JPanel userInfo;
    private JButton order;
    private LoginFrame login;
    private ArrayList<ShopItem> goodsList;
    private Users currentUser;
    private JLabel balancelabel;

    public UserFrame(DB db, Users user, LoginFrame login){
        this.goodsList = new ArrayList<ShopItem>();
        this.db = db;
        this.login = login;
        this.currentUser = user;
        setSize(600, 600);
        setTitle("UserFrame");
        setLayout(new BorderLayout()); setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.userInfo = new JPanel();
        this.userInfo.add(new JLabel("Hello, "));
        this.balancelabel = new JLabel("" + user.getBalance() + "$ ");
        this.userInfo.add(new JLabel(user.getLogin() + "! "));
        this.userInfo.add(new JLabel("Balance: "));
        this.userInfo.add(balancelabel);
        this.logout = new JButton("LogOut");
        this.logout.setSize(100, 100);
        this.userInfo.add(this.logout);
        add(this.userInfo, BorderLayout.NORTH);
        DaoGoods daoGoods = new DaoGoods(db); ArrayList<Goods> goods = null;
        int position = 100;
        try {
            goods = daoGoods.selectAll();
        } catch (SQLException ex) { System.out.println(ex);
        }
        this.itemList = new JPanel(new GridLayout(goods.size(), 1)); this.itemList.setPreferredSize(new Dimension(500, goods.size() * 200)); for (Goods i : goods) {
            ShopItem tmp = new ShopItem(i, position); this.itemList.add(tmp); this.goodsList.add(tmp);
        }
        this.scrollPane = new JScrollPane(this.itemList); this.scrollPane.getVerticalScrollBar().setUnitIncrement(10); add(this.scrollPane, BorderLayout.CENTER);
        this.order = new JButton("Order");
        this.order.setSize(100, 100);
        add(this.order, BorderLayout.SOUTH);


        setVisible(true); //прорисовка
        actions();

    }
    public void actions(){
        this.logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                login.setVisible(true);
            }
        });

        this.order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<Goods, Integer> choosenGoods = new HashMap<Goods, Integer>();
                int totalcost = 0;
                for (ShopItem shopItem : goodsList) {
                    if (shopItem.isChoosen()) { choosenGoods.put(shopItem.getCurrentGood(),
                            shopItem.getAmount());
                        totalcost += shopItem.getCurrentGood().getPrice() *
                                shopItem.getAmount(); }
                }
                if (currentUser.getBalance() >= totalcost) {
                    int lastInsertID = 0; try {
                        new DaoOrders(db).insert(new Orders(currentUser.getUsers_id(), "processing", 0, totalcost));
                        lastInsertID = new DaoOrders(db).getLastInsertId();
                    } catch (SQLException ex) {
                    }
                    StringBuilder sb = new StringBuilder("INSERT INTO goods_in_orders VALUES ");
                    for (Map.Entry<Goods, Integer> entry : choosenGoods.entrySet()) {
                        sb.append("('" + lastInsertID + "','" + entry.getKey().getGoods_id() + "','" + entry.getValue() + "'),");
                    }

                    String result = sb.toString(); try {
                        db.update(result.substring(0, result.length() - 1)); balancelabel.setText("" + (currentUser.getBalance() - totalcost) + "$  ");
                                currentUser.setBalance(currentUser.getBalance() - totalcost); new DaoUsers(db).updateBalance(currentUser); JOptionPane.showMessageDialog(null, "Total Cost: " +
                                totalcost, "Success order", 1);
                    } catch (SQLException ex) {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Total Cost " + totalcost + " more than your balance", "Fail order", 1);
                }

            }
        });




    }
}
