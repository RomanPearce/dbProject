package db;

import java.sql.*;

public class DB {
    private Connection cn;
    private Statement st;

    public Connection getCn() {
        return cn;
    }
    public DB(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        cn = (Connection) DriverManager.getConnection(url, user, password);
        st = (Statement) cn.createStatement();
    }


    public void update(String sql) throws SQLException {
        st.executeUpdate(sql);
    }
    public ResultSet query(String sql) throws SQLException {
        ResultSet rs = null;
        rs = st.executeQuery(sql);
        return rs;
    }
    public void showResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) { System.out.print(rsmd.getColumnName(i) + "\t");
        }
        while (rs.next()) {
            System.out.println();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rs.getString(i) + "\t"); }
        } }
    public void close() throws SQLException { st.close();
        cn.close(); }



}