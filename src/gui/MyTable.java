package gui;

import db.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTable extends JTable{
    private  DefaultTableModel model;
    public DefaultTableModel getModel(){
        return model;
    }

    public MyTable(ResultSet rs){
        model = new DefaultTableModel();

        try {
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                model.addColumn(rsmd.getColumnName(i));
            }
            
            while (rs.next()){
                Vector v = new Vector();
                for (int i = 1; i <= rsmd.getColumnCount() ; i++) {
                    v.add(rs.getString(i));
                }
                model.addRow(v);
            }
            setModel(model);
            setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
            
        }catch (SQLException ex){
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE,null,ex);
        }

    }



}
