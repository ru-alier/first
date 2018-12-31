package ru.incubator.alier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.*;

public class Controller extends Thread {
    //    Класс для проверки условий и запуска потоков
    private boolean updateDateInBaseOn = true;
    //    final public boolean QUERY_SORT_IN_APPLICATION = true;
    int threadCount = 2000;
    private Date enteredDate;

    void init(String[] argDate) {

        //Чтение аргументов приложения. Проверка корректности введенной даты и сравнение с текущей датой.
        DateProcessor dateProcessor = new DateProcessor();
        String strDate = dateProcessor.chkDate(argDate);
        enteredDate = dateProcessor.convertSrtToDate(strDate);
        boolean date = dateProcessor.goodDate(enteredDate, new Date());
        if (!date) {
            System.out.println("Вы ввели не корректную дату. Программа будет закрыта. " +
                    "Введите дату еще раз в формате dd-MM-yyyy. В качестве разделителя используйте точку или тире." +
                    "\nНедопустимо использовать дату больше или равную текущей: " + LocalDate.now());
            System.exit(0);
        } else System.out.println("Результат проверки корректности введенной даты - ОК.");
    }

    void starting() throws SQLException {
//        int id;
        String schemaDBName = "tempdb";
        Semaphore turn = new Semaphore(threadCount);
//        String currentUrl;
//        Date dbDate;

        //Соединение с базой
        DBProcessor dbProcessor = new DBProcessor();
        dbProcessor.getConnection();
        //Чтение БД
        ResultSet resultSet = dbProcessor.readDB(schemaDBName);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String currentUrl = resultSet.getString("url");
            Date dbDate = resultSet.getDate("date");
            DateProcessor dateProcessor = new DateProcessor();
//            System.out.println("ID: " + id + "  DATE! :" + dbDate);
            if (dbDate != null) {
                if (dateProcessor.goodDate(enteredDate, dbDate)) {
                    UrlStatusCheck urlStatusCheck = new UrlStatusCheck();
//                    UrlStatusCheckNew urlStatusCheck = new UrlStatusCheckNew();
                    urlStatusCheck.turn = turn;
                    urlStatusCheck.updateDateInBaseOn = updateDateInBaseOn;
                    urlStatusCheck.curUrl = currentUrl;
                    urlStatusCheck.id = id;
                    urlStatusCheck.schema_Table = schemaDBName;
                    urlStatusCheck.dbProcessor = dbProcessor;
                    urlStatusCheck.start();
                    dbProcessor.getConnection().close();
                }
            } else if (updateDateInBaseOn) {
//                System.out.print(" goodDate: ");
                UrlStatusCheck urlStatusCheck = new UrlStatusCheck();
//                    UrlStatusCheckNew urlStatusCheck = new UrlStatusCheckNew();
                urlStatusCheck.turn = turn;
                urlStatusCheck.updateDateInBaseOn = updateDateInBaseOn;
                urlStatusCheck.curUrl = currentUrl;
                urlStatusCheck.id = id;
                urlStatusCheck.schema_Table = schemaDBName;
                urlStatusCheck.dbProcessor = dbProcessor;
                urlStatusCheck.start();
                dbProcessor.getConnection().close();
//                System.out.println("- it's worked!");
            } else
                System.out.println("Записть не требует обновления и пропущена");
        }
    }


/*    void startRD() throws SQLException, IOException, ExecutionException, InterruptedException {
        //Чтение БД
        ReadDB readDB = new ReadDB();
//        ResultSet resultSet = readDB.readDB("incubator.tempdb" );
        ResultSet resultSet = readDB.getDB("tempdb");
        DateProcessor dateProcessor = new DateProcessor();

        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + ", url: " + resultSet.getString("url") + ", status code:" + resultSet.getInt("status") + ", дата обновления: " + resultSet.getDate("date"));
        }

    }*/
}
