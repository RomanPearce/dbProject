package db;

import dao.DaoGoods;
import dao.DaoOrders;
import dao.DaoUsers;
import entity.Goods;
import entity.Orders;
import entity.Users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class WorkDB {
    public final static String NAME_DB = "car_rent";
    public static void create(String url, String user, String password) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException {
        DB db = new DB(url, user, password);
        db.update("create database " + NAME_DB + " DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci");
        db.update("use " + NAME_DB); db.update("CREATE table users ("
                + "users_id int auto_increment primary key," + " login varchar(30) not null, "
                + "pass varchar(128) not null, "
                + "role int(1),"
                + "balance int,"
                + "block_status int(1),"
                + "delete_status int(1))");
        DaoUsers du = new DaoUsers(db);
        du.insert(new Users("user1", "1111", 1, 10000, 0, 0));
        du.insert(new Users("user2", "2222", 1, 0, 0, 0));
        du.insert(new Users("admin", "1234", 0, 0, 0, 0));
        db.update("CREATE table goods ("
                + "goods_id int auto_increment primary key," + " name varchar(30), "
                + "image_path text, "
                + "price int, "
                + "description text, "
                + "delete_status int(1))");
        DaoGoods dg = new DaoGoods(db);
        dg.insert(new Goods(1, "Computer", "good1.jpg", 1000, "Very big computer", 0));
        dg.insert(new Goods(2, "Mouse", "good2.jpg", 10, "mad skills mouse", 0));
        dg.insert(new Goods(3, "Keyboard", "good3.jpg", 15, "defender keyboard", 0));
        db.update("CREATE table orders ("
                + "orders_id int auto_increment primary key,"
                + "users_id int, "
                + "payment ENUM('processing','reject','payed'), "
                + "delete_status int(1),"
                + "total_cost int,"
                + "foreign key(users_id) references users(users_id))");
        DaoOrders dor = new DaoOrders(db); dor.insert(new Orders(1, 1, "processing", 0, 50)); dor.insert(new Orders(2, 2, "processing", 0, 3000)); dor.insert(new Orders(3, 1, "reject", 0, 150)); db.update("CREATE table goods_in_orders ("
                + "orders_id int,"
                + "goods_id int, "
                + "count int,"
                + "foreign key(orders_id) references orders(orders_id)," + "foreign key(goods_id) references goods(goods_id))");
        db.update("insert into goods_in_orders values(1,2,5)"); db.update("insert into goods_in_orders values(2,1,3)"); db.update("insert into goods_in_orders values(3,3,10)"); db.close();
    }
    public static void delete(String url, String user, String password) throws SQLException, ClassNotFoundException {
        DB db = new DB(url, user, password); db.update("drop database " + NAME_DB); db.close();
    }

}
