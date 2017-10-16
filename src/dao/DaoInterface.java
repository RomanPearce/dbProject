package dao;

import java.sql.SQLException;

public interface DaoInterface <T>{
    void delete(T ob)throws SQLException;
    void update(T ob)throws SQLException;
    void insert(T ob)throws SQLException;
    void softDelete(T ob)throws SQLException;
}
