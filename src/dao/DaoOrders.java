package dao;

import db.DB;
import entity.Orders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoOrders  implements DaoInterface<Orders>{
    private DB db;
    public DaoOrders(DB db) { this.db = db;
    }
    @Override
    public void insert(Orders ob) {
        try {
            PreparedStatement ps = (PreparedStatement)db.getCn().prepareStatement(
                    "insert into " + ob.getClass().getSimpleName()
                            + "(users_id,payment,delete_status,total_cost) "
                            + "values(?,?,?,?)");
            ps.setInt(1, ob.getUsers_id());
            ps.setString(2, ob.getPayment());
            ps.setInt(3, ob.getDelete_status());
            ps.setInt(4, ob.getTotal_cost()); ps.execute();
        }catch (SQLException e){
            System.out.println("Exeption in insert DaoOrders");
        }
    }
    @Override
    public void update(Orders ob) {
        try {
            PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                    "update " + ob.getClass().getSimpleName()
                            + " set payment=? where orders_id=" + ob.getOrders_id()
            );
            ps.setString(1, ob.getPayment());
            ps.execute();
        }catch (SQLException e){
            System.out.println("Exeption in update DaoOrders");
        }
    }
    @Override
    public void softDelete(Orders ob) throws SQLException{

            db.update("delete from " + ob.getClass().getSimpleName() + " where orders_id=" + ob.getOrders_id());
    }
    @Override
    public void delete(Orders ob) throws SQLException {
        db.update("update " + ob.getClass().getSimpleName()
                + " set delete_status=1 where orders_id=" + ob.getOrders_id());
    }
    public int getLastInsertId() throws SQLException {
        ResultSet rs = db.query("SELECT LAST_INSERT_ID() FROM orders");
                rs.next();
        return rs.getInt(1); }

}
