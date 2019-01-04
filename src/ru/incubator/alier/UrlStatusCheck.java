package ru.incubator.alier;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class UrlStatusCheck extends Thread {
    private Semaphore turn;
    private boolean updateDateInBaseOn;
    private volatile String curUrl;
    private int id;
    private int timeOutConnection = 2000;// задает время ожидания, введено для ускорения обработки "повисающих" сайтов
    private String schema_Table;
    private DBProcessor dbProcessor;

    public UrlStatusCheck(Semaphore turn, boolean updateDateInBaseOn, String curUrl, int id, String schema_Table, DBProcessor dbProcessor) {
        this.turn = turn;
        this.updateDateInBaseOn = updateDateInBaseOn;
        this.curUrl = curUrl;
        this.id = id;
        this.schema_Table = schema_Table;
        this.dbProcessor = dbProcessor;
    }

    public Semaphore getTurn() {
        return turn;
    }

    public void setTurn(Semaphore turn) {
        this.turn = turn;
    }

    public boolean isUpdateDateInBaseOn() {
        return updateDateInBaseOn;
    }

    public void setUpdateDateInBaseOn(boolean updateDateInBaseOn) {
        this.updateDateInBaseOn = updateDateInBaseOn;
    }

    public String getCurUrl() {
        return curUrl;
    }

    public void setCurUrl(String curUrl) {
        this.curUrl = curUrl;
    }

    public int getIdIndex() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchema_Table() {
        return schema_Table;
    }

    public void setSchema_Table(String schema_Table) {
        this.schema_Table = schema_Table;
    }

    public DBProcessor getDbProcessor() {
        return dbProcessor;
    }

    public void setDbProcessor(DBProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    public UrlStatusCheck() {
    }

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
        String respMess = "";
        try {
//            http.setRequestMethod("HEAD");
            statusCode = http.getResponseCode();
//            System.out.print("Response OK. ");
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Коннект тайм аут! URL: " + curUrl);
        }
        String dateNow;
        if (updateDateInBaseOn) dateNow = new SimpleDateFormat("yyyyMMdd").format(new Date());
        else dateNow = "nothing";
        boolean isItUpdated = dbProcessor.updatingDBStatus(id, schema_Table, statusCode, dateNow, dbProcessor);
//        System.out.println("Вы вышли из метода с кодом результата :" + statusCode +"| номер потока ====>" +Thread.currentThread().getName()+" Обновление URL в DB = " + isItUpdated +"  Осталось открытых потоков ==>>"+Thread.activeCount());
        turn.release();
    }
}
