package ru.incubator.alier;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class Main {

    public static void main(String args[]) throws ClassNotFoundException, InterruptedException, IOException, SQLException, ExecutionException {
        //Фиксация времени запуска прогграммы
//        long start = System.currentTimeMillis();

        Controller controller = new Controller();
        controller.init(args);//проверка даты и приведение к формату Date.
        controller.starting();

//        long timeWorkCode = System.currentTimeMillis() - start;
//        System.out.println("Скорость выполнения программы: " + timeWorkCode/1000 + " секунд.");


    }
}