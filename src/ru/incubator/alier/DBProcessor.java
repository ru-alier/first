package ru.incubator.alier;

import java.sql.*;

public class DBProcessor {

    public Connection getConnection() {
        String URL = "jdbc:mysql://46.161.156.88:3306/incubator?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//        String URL = "jdbc:mysql://46.161.156.88:3307/incubator?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true";
        String USERNAME = "username";
        String PASSWORD = "password";

/*        String URL = "jdbc:mysql://localhost:3306/incubator?useSSL=false&useUnicode=true";
        String USERNAME = "root";
        String PASSWORD = "root";*/
//        String driver = "mysql-connector-java-8.0.13.jar";
        String driver = "com.mysql.jdbc.Driver";
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            if (conn != null)
////                System.out.println("Приложение подключилось к БД !");
//            else
////                System.out.println("Приложение НЕ может подключится к БД.");
//
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Приложение НЕ может подключиться к БД.");
            System.exit(-1);
        }
        return conn;
    }

    public ResultSet readDB(String schemaName) {
//        System.out.println("Вошел в метод readDB");

        //      Соединение с базой
        Connection conn = getConnection();
        // SQL запрос
        String query = "select * from " + schemaName;
//        System.out.println(query);
        Statement stmt = null;
        ResultSet resultSet = null;
            if (conn == null) {
                System.out.println("Приложение НЕ может подключиться к БД.");
                System.exit(-1);
            }
            else {

                try {
                    stmt = conn.createStatement();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    System.out.println("Ощибка при чтении из DB.");
                }


                try {
                    resultSet = stmt.executeQuery(query);
                    return resultSet;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Ощибка получения ответа от базы данных. Неудалось создать ResultSet");
                }


            }
        return resultSet;
    }

    public boolean updatingDBStatus(int id, String schema_Table, int statusCode, String dateNow, DBProcessor dbProcessor) {

        String query;
        if (dateNow.equals("nothing")) {
            query = "UPDATE " + schema_Table + " SET status=" + statusCode + " WHERE id=" + id + ";";
        } else {
            query = "UPDATE " + schema_Table + " SET status=" + statusCode + ", date=" + dateNow + " WHERE id=" + id + ";";

        }
//        System.out.println("Текст запроса" + query);

        //      Соединение с базой
//        DBProcessor db = new DBProcessor();
        Connection conn = null;
        conn= dbProcessor.getConnection();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


}
