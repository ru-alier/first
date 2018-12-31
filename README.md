# Инструкция по установке. 
***
Данная программа является ботом, запрашивающим статус коды страниц и обновляющая информацию об их доступности в базе.

При запуске программы нужно укзать дату после которой будет поверятся актуальность страниц.

Формат даты dd.MM.yyyy (например, 25.12.2018). Если дата будет введена не корректно, программа может работать не корректно.

Пример запуска программы (во избежание проблем с кодировкой):

**java -jar -Dfile.encoding=UTF-8 lastVerBot.jar 15.12.2018**

После считывания даты программа проверит дату на корректность и приведет к формату Date().

Разделителем даты рекомендуется использовать следующие символы: "." или "-" или"\" или "/".

Использование пробела не допустимо, т.к. программа воспримет каждое значение введённое через пробел как аргумент.

Для работы с БД в каталоге с программой(lasrVerBot.jar) должен, лежать файл драйвера mysql-connector-java-8.0.13.jar . Данный файл прописан в Class Path MANIFEST.MF и определяется при запуске автоматически.

Если среда запуска поддерживает bat файлы,в качетве альтернативы приложен файл startme.cmd. Запуск производится следующим образом:

**startme** ***date***
например:
**startme 12.12.2018**

**Спасибо, что дочитали до конца!**
PS Для корректной работы программа требует чтобы сервер MySQL был запущен.

# For English users
The program is a bot, requesting the status of page codes and updating information about their availability in the database.

When you start the program, you must enter a date. After this date, the relevance of the pages will be verified.

The date format is dd.MM.yyyy (for example, 12/25/2018). If the date is entered incorrectly, the program may not work correctly.

An example of running the program:

** java -jar -Dfile.encoding = UTF-8 lastVerBot.jar 12/15/2018 **

The date separator is recommended to use the following characters: "." or "-" or "\" or "/".

Space is not allowed.

To work with the database, the mysql-connector-java-8.0.13.jar driver file must be in the program directory (lasrVerBot.jar). This file is registered in Class Path MANIFEST.MF and is automatically determined at startup.

If the startup environment supports bat files, the startme.cmd file is attached as an alternative. The launch is as follows:

** startme ** *** date ***
eg:
** startme 12/12/2018 **

** Thank you for reading to the end! **
PS For correct operation, the program requires that my MySQL server be running.
