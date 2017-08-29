package com.company.classes.util;

import java.sql.*;

public final class ConnectionManager {
    public static final String driverName = "com.mysql.jdbc.Driver";
    public static final String url = "jdbc:mysql://192.168.10.9:3306/db1?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";

    //public static final String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
    public static final String user = "root";
    public static final String password = "admin";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // 把JDBC驱动类装载入Java虚拟机中
            Class.forName(driverName);
            // 加载驱动，并与数据库建立连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
