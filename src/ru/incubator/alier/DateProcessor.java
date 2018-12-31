package ru.incubator.alier;

import java.util.Calendar;
import java.util.Date;

public class DateProcessor {

    public String chkDate(String[] chkDate){
        // Проверка корректности аргумента из командной строки
        try {
            if ((chkDate[0]==null)|(chkDate.length > 1)) {
                System.out.println("Вы ввели недопустимое число аргументов (" + chkDate.length + " вместо одного)." +
                        " Пробел считается разделителем аргументов строки.");
                System.exit(0);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Программа закрыта. Ввведите дату в формате dd.MM.yyyy в качестве аргумента.");
            System.exit(0);
        }

        if ((chkDate[0].length())!= 10){
        System.out.println("Недопустимая длина аргумента. Введите дату еще раз в формате dd-MM-yyyy. " +
                "В качестве разделителя используйте точку(.) или тире(-).");
        System.exit(0);}
        return String.valueOf(chkDate[0]);
    }

    public Date convertSrtToDate(String strDate) {

//      Преобразование аргумента строки к формату DATE
        Calendar calendar = Calendar.getInstance();//возвращает календарь с временым поясом по умолчанию
        String input = strDate;
//      Вырезаем из строки день месяц и год, пропуская разделители.
        String day = input.substring(0, 2);
        String month = input.substring(3, 5);
        String year = input.substring(6, 10);

        try {
            calendar.set(Calendar.YEAR, Integer.valueOf(year));
            calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            System.out.println("Программа закрыта. Для ввода даты используйте цифры. Формат даты dd.MM.yyyy, т.е. день, месяц, год, через точку без пробелов).");
            System.exit(0);
        }

        Date formatStrToDate = calendar.getTime();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(formatStrToDate));

        return formatStrToDate;
    }

    boolean goodDate(Date enDate, Date dbDate) {
        if (dbDate == null) return true;
        if (dbDate.after(enDate))
            return true;
        else
            return false;
    }
}
