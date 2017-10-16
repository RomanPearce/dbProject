package dao;

import db.DB;
import entity.Users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoUsers implements DaoInterface<Users> {
    public DB db;
    public DaoUsers(DB db){
        this.db = db;
    }

    @Override
    public void delete(Users ob) throws SQLException {
        db.update("update " + ob.getClass().getSimpleName()
                + " set delete_status=1 where users_id=" + ob.getUsers_id());
    }

    @Override
    public void update(Users ob) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update " + ob.getClass().getSimpleName()
                        + " set login=?, pass=?, role=? , block_status=?,"
                        + " balance =? where users_id=" + ob.getUsers_id());
        ps.setString(1, ob.getLogin());
        ps.setString(2, ob.getPass());
        ps.setInt(3, ob.getRole());
        ps.setInt(4, ob.getBlock_status());
        ps.setInt(5, ob.getBalance());
        ps.execute();
    }

    @Override
    public void insert(Users ob) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "insert into " + ob.getClass().getSimpleName()
                        + " (login,pass,role,block_status,delete_status,balance)" + " values(?,?,?,?,?,?)");
        ps.setString(1, ob.getLogin()); ps.setString(2, ob.getPass()); ps.setInt(3, ob.getRole()); ps.setInt(4, ob.getBlock_status()); ps.setInt(5, ob.getDel_status()); ps.setInt(6, ob.getBalance()); ps.execute();
    }

    public void updateBalance(Users ob) throws SQLException{
        int balance = 0;
        ResultSet rs = db.query("select balance from users "
                + "where login='" + ob.getLogin() + "'"); if (rs.next()) {
            balance = rs.getInt(1); }
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update " + ob.getClass().getSimpleName() + " set balance =? where login='"
                        + ob.getLogin() + "'");
        ps.setInt(1, balance + ob.getBalance()); ps.execute();


    }

    @Override
    public void softDelete(Users ob) throws SQLException{
        db.update("delete from " + ob.getClass().getSimpleName() + " where users_id=" + ob.getUsers_id());
    }


    public Users getUser(ResultSet rs)
            throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return new Users(rs.getInt("users_id"), rs.getString("login"), rs.getString("pass"), rs.getInt("role"), rs.getInt("block_status"), rs.getInt("balance"));
    }
}
