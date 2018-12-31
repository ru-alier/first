package ru.incubator.alier;

import java.sql.*;

public class ReadDB {

    public ResultSet getDB(String schemaName) {
//        System.out.println("Вошел в метод readDB");

        //      Соединение с базой
        DBProcessor db = new DBProcessor();
        Connection conn = db.getConnection();
        // SQL запрос
        String query = "select * from "+schemaName;
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println("Ощибка при чтении из DB.");
        }

        ResultSet resultSet = null;
        try {
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ощибка получения ответа от базы данных. Неудалось создать ResultSet");
        }
        return resultSet;
    }
}
