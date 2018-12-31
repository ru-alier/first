package ru.incubator.alier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDBSRow {

    public synchronized void delThisId(int id) throws SQLException {
//        TimeUnit.MILLISECONDS.sleep(0);
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateNow);
//        System.out.println("DateNow in String: "+ dateNow1);
//        образец запроса
//        UPDATE incubator.tempdb SET status=302, data=20181224 WHERE id=1;
//        String query = "DELETE FROM " + schema_Table + " WHERE id="+id+";";
        String query ="DELETE FROM `tempdb` WHERE (`id` = '"+id+"');";
//        String query ="DELETE FROM `incubator`.`tempdb` WHERE (`id` = '"+id+"');";

        System.out.println("Текст запроса"+query);
//      Соединение с базой
        DBProcessor db = new DBProcessor();

        Connection conn = db.getConnection();
        System.out.println("getConn - OK "+conn);
        PreparedStatement prepStat = conn.prepareStatement(query);
//        System.out.println("PrepState - OK "+prepStat);

        conn.prepareStatement(query).execute();

            prepStat.execute() ;
            System.out.println("Executed!");

    }
}



//            Промежуточный код для масштабирования базы
//            String query = "INSERT into incubator.tempdb (url) values (\""+resultSet.getString("url")+"\")";
//        String query1 = "select * from incubator.tempdb";
//            PreparedStatement prepStat1 = conn.prepareStatement(query);
//            prepStat1.execute();
//            i1++;
//            prepStat1.execute();
//            i1++;
//            prepStat1.execute();
//            i1++;
//            switch (i1) {
//                case 10000:
//                    System.out.println("уже прочитано 10000 записей");
//                    break;
//                case 50000:
//                    System.out.println("уже прочитано 50000 записей");
//                    break;
//                case 100000:
//                    System.out.println("уже прочитано 100000 записей");
//                    break;
//                case 200000:
//                    System.out.println("уже прочитано 200000 записей");
//                    break;
//                case 300000:
//                    System.out.println("уже прочитано 300000 записей");
//                    break;
//            }