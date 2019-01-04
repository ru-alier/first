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
    private int threadCount = 2000;
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
        String schemaDBName = "tempdb";
        Semaphore turn = new Semaphore(threadCount);

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
//                  Приведение к принцыпу инкапсуляции, передаем параметры в конструкторе, а не на прямую.
                    UrlStatusCheck urlStatusCheck = new UrlStatusCheck(turn, updateDateInBaseOn, currentUrl, id, schemaDBName, dbProcessor);
                    urlStatusCheck.start();
                    dbProcessor.getConnection().close();
                }
            } else if (updateDateInBaseOn) {
//                System.out.print(" goodDate: ");
//              Приведение к принцыпу инкапсуляции, передаем параметры в конструкторе, а не на прямую.
                UrlStatusCheck urlStatusCheck = new UrlStatusCheck(turn, true, currentUrl, id, schemaDBName, dbProcessor);
                urlStatusCheck.start();
                dbProcessor.getConnection().close();
//                System.out.println("- it's worked!");
            } else
                System.out.println("Записть не требует обновления и пропущена");
        }
    }
}
