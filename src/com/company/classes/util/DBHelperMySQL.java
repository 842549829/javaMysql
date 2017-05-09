package com.company.classes.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelperMySQL {
    @Contract(pure = true)
    public static int executeNonQuery(String sql, Object[] params) throws Exception {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            prepareCommand(preparedStatement, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception(ex);
        } finally {
            ConnectionManager.closeStatement(preparedStatement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public static Object getModel(String sql, Object[] params, Class<?> classObj) throws Exception {
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            prepareCommand(preparedStatement, params);
            rs = preparedStatement.executeQuery();
            Object obj = classObj.newInstance();
            Field[] fields = classObj.getDeclaredFields();
            if (rs.next()) {
                for (Field item : fields) {
                    if (!item.getName().equals("serialVersionUID")) {
                        System.out.println(rs.getString(item.getName()));
                        item.setAccessible(true);
                        Class<?> type = item.getType();
                        if (type.isPrimitive()) {
                            item.set(obj,convert(item.getType().toString(),rs.getString(item.getName())));
                        } else {
                            String name = item.getType().getName();
                            if (name.equals("java.lang.String")) {
                                item.set(obj, rs.getString(item.getName()));
                            } else if (name.equals("java.util.Date")) {
                                Date date = Timestamp.valueOf(rs.getString(item.getName()));
                                item.set(obj, date);
                            } else {
                                Method m = type.getMethod("valueOf",String.class);
                                item.set(obj,m.invoke(null,rs.getString(item.getName())));
                            }
                        }
                        item.setAccessible(false);
                    }
                }
                return obj;
            }
            return null;
        } catch (SQLException ex) {
            throw new Exception(ex);
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(preparedStatement);
            ConnectionManager.closeConnection(conn);
        }
    }

    public static List<?> executeReader(String sql, Object[] params, Class<?> classObj) throws Exception {
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            prepareCommand(pstmt, params);
            rs = pstmt.executeQuery();
            @SuppressWarnings({"rawtypes", "unchecked"})
            List<Object> list = new ArrayList();
            Field[] fields = classObj.getDeclaredFields();
            while (rs.next()) {
                Object obj = classObj.newInstance();
                for (Field item : fields) {
                    if (!item.getName().equals("serialVersionUID")) {
                        item.setAccessible(true);
                        Class<?> type = item.getType();
                        try {
                            if (type.isPrimitive()) {
                                item.set(obj, convert(item.getType().toString(), rs.getString(item.getName())));
                            } else {
                                if (item.getType().getName().equals("java.lang.String")) {
                                    item.set(obj, rs.getString(item.getName()));
                                } else if (item.getType().getName().equals("java.util.Date")) {
                                    Date date = Timestamp.valueOf(rs.getString(item.getName()));
                                    item.set(obj, date);
                                } else {
                                    Method m = type.getMethod("valueOf", String.class);
                                    item.set(obj, m.invoke(null, rs.getString(item.getName())));
                                }
                            }
                        } catch (Exception e) {
                            continue;
                        }
                        item.setAccessible(false);
                    }
                }
                System.out.println(obj.toString());
                list.add(obj);
            }
            return list;
        } catch (SQLException ex) {
            throw new Exception(ex);
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pstmt);
            ConnectionManager.closeConnection(conn);
        }
    }

    @Nullable
    public static Object executeScalar(String sql, String name, Object[] params) throws Exception {
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            prepareCommand(preparedStatement, params);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject(name);
            }
            return null;
        } catch (SQLException ex) {
            throw new Exception(ex);
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(preparedStatement);
            ConnectionManager.closeConnection(conn);
        }
    }

    @Nullable
    public static Object executeScalar(String sql, int index, Object[] params) throws Exception {
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            prepareCommand(preparedStatement, params);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getObject(index);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(preparedStatement);
            ConnectionManager.closeConnection(conn);
        }
    }

    public static void prepareCommand(PreparedStatement preparedStatement, Object[] params) throws SQLException {
        if (preparedStatement != null && params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                int parameterIndex = i + 1;
                preparedStatement.setString(parameterIndex, params[i].toString());
            }
        }
    }

    @Nullable
    private static Object convert(String type, String value) {
        if (type.equals("int")) {
            return new Integer(value);
        }
        if (type.equals("double")) {
            return new Double(value);
        }
        if (type.equals("float")) {
            return new Float(value);
        }
        return null;
    }
}
