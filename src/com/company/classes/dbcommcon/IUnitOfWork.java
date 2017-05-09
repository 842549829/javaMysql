package com.company.classes.dbcommcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IUnitOfWork extends AutoCloseable {
    Connection getConnection();

    PreparedStatement getPreparedStatement(String sql) throws SQLException;

    ResultSet getResultSet(String sql) throws SQLException;

    void complete() throws SQLException;

    void rollback() throws SQLException;
}
