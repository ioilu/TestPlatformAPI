package com.huilu.testplatform.util.mysql;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huilu.testplatform.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class JdbcUtils {
    @Autowired
    Config config;

    private Connection getConnection() throws SQLException {
        String url = config.getMYSQL_URL();
        String username = config.getMYSQL_USERNAME();
        String password = config.getMYSQL_PASSWORD();
        return DriverManager.getConnection(url, username, password);

    }

    private void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            rs = null;
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean insert(String sql) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            st = conn.createStatement();
            boolean num = st.execute(sql);
            if (num = true) {
                System.out.println("添加数据成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            release(conn, st, rs);
        }
        return true;
    }

    private boolean delete(String sql) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if (num > 0) {
                System.out.println("删除数据成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            release(conn, st, rs);
        }
        return true;
    }

    private boolean update(String sql) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if (num > 0) {
                System.out.println("修改数据成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            release(conn, st, rs);
        }
        return true;
    }

    private JSONArray query(String sql) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        JSONArray array = new JSONArray();
        try {
            conn = getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = md.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObject.put(columnName, value);
                }
                array.add(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return array;
        } finally {
            release(conn, st, rs);
        }
        return array;
    }
    public void executeSql(String sql){
        sql = sql.trim().toLowerCase();
        String[] s = sql.split(" ");
        if (s.length > 2){
            switch (s[0]) {
                case "select":
                    query(sql);
                    break;
                case "update":
                    update(sql);
                    break;
                case "delete":
                    delete(sql);
                    break;
                case "insert":
                    insert(sql);
                    break;
                default:
                    System.out.println("sql错误");
            }
        }

    }

    public static void main(String[] args) {
    }
}
