package com.company.classes.dbcommcon;

import com.company.classes.model.Parameters;
import com.company.classes.model.ParametersType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository implements AutoCloseable {

    private String name = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private IUnitOfWork unitOfWork = null;
    private ResultSet resultSet = null;
    private List<Parameters> parameters = null;

    protected BaseRepository(IUnitOfWork unit, String name) {
        this.name = name;
        this.unitOfWork = unit;
        if (this.unitOfWork != null) {
            this.connection = this.unitOfWork.getConnection();
        } else {
            if (this.connection == null) {
                this.connection = DbFactories.getConnection();
            }
        }
    }

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException {
        this.preparedStatement = connection.prepareStatement(sql);
        this.parameters = new ArrayList();
        return this.preparedStatement;
    }

    protected ResultSet executeQuery() throws SQLException {
        if (this.parameters != null) {
            this.setPreparedStatement();
        }
        this.resultSet = this.preparedStatement.executeQuery();
        return this.resultSet;
    }

    public int executeNonQuery() throws SQLException {
        if (this.parameters != null) {
            this.setPreparedStatement();
        }
        return this.preparedStatement.executeUpdate();
    }

    protected void setPreparedStatement() throws SQLException {
        for (int i = 1; i <= parameters.size(); i++) {
            Parameters para = parameters.get(i - 1);
            if (para.getType() == ParametersType.INT) {
                this.addParameter(i, Integer.parseInt(String.valueOf(para.getValue())));
            } else if (para.getType() == ParametersType.STRING) {
                this.addParameter(i, String.valueOf(para.getValue()));
            }else if (para.getType() == ParametersType.BOOLEAN) {
                this.addParameter(i, Boolean.valueOf(para.getValue().toString()));
            }else if(para.getType() == ParametersType.DOUBLE){
                this.addParameter(i, Double.valueOf(para.getValue().toString()));
            }else if(para.getType() == ParametersType.DATE){
                this.addParameter(i, Date.valueOf(para.getValue().toString()));
            }
        }
    }

    protected void addParameter(Parameters parameters) {
        if (parameters != null) {
            this.parameters.add(parameters);
        }
    }

    protected void addParameter(ParametersType type, Object value) {
        this.addParameter(new Parameters(type, value));
    }

    protected void addParameter(int parameterIndex, String value) throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.setString(parameterIndex, value);
        }
    }

    protected void addParameter(int parameterIndex, double value) throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.setDouble(parameterIndex, value);
        }
    }

    protected void addParameter(int parameterIndex, Boolean value) throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.setBoolean(parameterIndex, value);
        }
    }

    protected void addParameter(int parameterIndex, int value) throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.setInt(parameterIndex, value);
        }
    }

    protected void addParameter(int parameterIndex, Date value) throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.setDate(parameterIndex, value);
        }
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