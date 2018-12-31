package ru.incubator.alier;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class UrlStatusCheck extends Thread{
    Semaphore turn;
    boolean updateDateInBaseOn;
    volatile String curUrl;
    int id;
    int timeOutConnection = 2000;// задает время ожидания, введено для ускорения обработки "повисающих" сайтов
    String schema_Table;
    DBProcessor dbProcessor;

    public void run() {
        try {
            turn.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = null;
        try {
            url = new URL(curUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
//            System.out.print("http OK. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        http.setConnectTimeout(timeOutConnection);
        http.setReadTimeout(timeOutConnection);


        int statusCode = 0;
        String respMess="";
        try {
//            http.setRequestMethod("HEAD");
            statusCode = http.getResponseCode();
//             respMess= http.getResponseMessage();

//            System.out.print("Response OK. ");
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Коннект тайм аут! URL: "+curUrl);
        }
        String dateNow;
        if (updateDateInBaseOn) dateNow = new SimpleDateFormat("yyyyMMdd").format(new Date());
        else dateNow="nothing";
        boolean isItUpdated = dbProcessor.updatingDBStatus(id, schema_Table, statusCode, dateNow, dbProcessor );
//        System.out.println("Вы вышли из метода с кодом результата :" + statusCode +"| номер потока ====>" +Thread.currentThread().getName()+" Обновление URL в DB = " + isItUpdated +"  Осталось открытых потоков ==>>"+Thread.activeCount());
        turn.release();
    }
}
