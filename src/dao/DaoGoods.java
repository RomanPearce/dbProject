package dao;

import db.DB;
import entity.Goods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoGoods  implements DaoInterface<Goods>{
    private DB db;

    public DaoGoods(DB db){
        this.db = db;
    }

    @Override
    public void delete(Goods ob)  {

    }

    @Override
    public void update(Goods ob) {
        try {
            PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                    "update " + ob.getClass().getSimpleName() + " set name=?, price=?,description = ? "
                            + "where goods_id=" + ob.getGoods_id());
            ps.setString(1,ob.getName());
            ps.setInt(2,ob.getPrice());
            ps.setString(3,ob.getDescription());
            ps.execute();

        } catch (SQLException e){
            System.out.println("EXEPTION in update method DaoGoods class");
        }
    }

    @Override
    public void insert(Goods ob) {
        try {
            PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                    "INSERT INTO " + ob.getClass().getSimpleName()
                            + "(name,image_path,price,description,delete_status)" + " values(?,?,?,?,?)");
            ps.setString(1, ob.getName());
            ps.setString(2, ob.getImagePath());
            ps.setInt(3, ob.getPrice());
            ps.setString(4, ob.getDescription());
            ps.setInt(5, ob.getDel_status());
            ps.execute();
        }catch (SQLException e){
            System.out.println(111);
        }
    }


    @Override
    public void softDelete(Goods ob) throws SQLException {
        db.update("delete from " + ob.getClass().getSimpleName() + " where goods_id=" + ob.getGoods_id());
    }

    public ArrayList<Goods> selectAll() throws  SQLException{
        ArrayList<Goods> tmp = new ArrayList<>();
        ResultSet rs  = this.db.query("SELECT * FROM goods");
        try {
            while (rs.next()) {
                tmp.add(new Goods(rs.getInt("goods_id"), rs.getString("name"), rs.getString("image_path"), rs.getInt("price"), rs.getString("description"),
                        rs.getInt("delete_status")));

            }
        }catch (SQLException e) {
            System.out.println();
        }
        return tmp;
    }

    public void setImagePath(Goods ob) throws SQLException { System.out.println(ob.getImagePath());
        db.update("update " + ob.getClass().getSimpleName()
                + " set image_path='" + ob.getImagePath().replace("\\", "\\/")
                + "' where goods_id=" + ob.getGoods_id()); }


}
