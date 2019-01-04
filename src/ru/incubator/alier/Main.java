package ru.incubator.alier;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class Main {

    public static void main(String args[]) throws ClassNotFoundException, InterruptedException, IOException, SQLException, ExecutionException {

        Controller controller = new Controller();
        controller.init(args);//проверка даты и приведение к формату Date.
        controller.starting();

    }
}