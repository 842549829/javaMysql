package com.company.classes.dbcommcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnitOfWork implements IUnitOfWork {

    private String name = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    protected UnitOfWork(IUnitOfWork unit, String name) throws SQLException {
        this.name = name;
        this.connection = DbFactories.getConnection();
        this.connection.setAutoCommit(false);
    }

    @Override
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        this.preparedStatement = connection.prepareStatement(sql);
        return this.preparedStatement;
    }

    @Override
    public ResultSet getResultSet(String sql) throws SQLException {
        this.getPreparedStatement(sql);
        this.resultSet = this.preparedStatement.executeQuery();
        return this.resultSet;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void complete() throws SQLException {
        this.connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    @Override
    public void close() {
        if (this.preparedStatement != null) {
            DbFactories.closeStatement(this.preparedStatement);
        }
        if (this.resultSet != null) {
            DbFactories.closeResultSet(this.resultSet);
        }
        if (connection != null) {
            DbFactories.closeConnection(this.connection);
        }
    }
}