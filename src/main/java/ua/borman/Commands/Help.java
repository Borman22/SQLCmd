package ua.borman.Commands;

import ua.borman.DatabaseManager;

import java.util.ArrayList;

/**
 * Created by Borman on 29.07.2017.
 */
public class Help {
    private static String fullList = "\n>\tСписок всех поддерживаемых комманд:\n" +
            ">\t\tconnect\n>\t\ttables\n>\t\tclear\n>\t\tdrop\n>\t\tcreate\n>\t\tfind\n>\t\tinsert\n>\t\tupdate\n>\t\tdelete\n>\t\thelp\n>\t\texit\n>\t\tclose\n" +
            ">\tДля более детального описания необходимой команды, напишите help|\"название команды\"\n" +
            ">\tНапример help|create\n";

    public static void help(ArrayList<String> queryList) {
        if (queryList.size() > 1) {
            queryList.set(1, queryList.get(1).trim().toLowerCase());
            switch (queryList.get(1)) {
                case "connect":
                    System.out.println("\n>\tКоманда для подключения к соответствующей БД\n" +
                            ">\tФормат команды: connect | database | username | password\n" +
                            ">\tгде: database - имя БД\n" +
                            ">\tusername -  имя пользователя БД\n" +
                            ">\tpassword - пароль пользователя БД\n");
                    break;

                case "tables":
                    System.out.println("\n>\tКоманда выводит список всех таблиц\n" +
                            ">\tФормат: tables (без параметров)\n");
                    break;

                case "clear":
                    System.out.println("\n>\tКоманда очищает содержимое указанной (всей) таблицы\n" +
                            ">\tФормат: clear | tableName\n" +
                            ">\tгде tableName - имя очищаемой таблицы\n");
                    break;

                case "drop":
                    System.out.println("\n>\tКоманда удаляет заданную таблицу\n" +
                            ">\tФормат: drop | tableName\n" +
                            ">\tгде tableName - имя удаляемой таблицы\n");
                    break;

                case "create":
                    System.out.println("\n>\tКоманда создает новую таблицу с заданными полями\n" +
                            ">\tФормат: create | tableName | column1 | column2 | ... | columnN\n" +
                            ">\tгде: tableName - имя таблицы\n" +
                            ">\tcolumn1 - имя первого столбца записи\n" +
                            ">\tcolumn2 - имя второго столбца записи\n" +
                            ">\tcolumnN - имя n-го столбца записи\n");
                    break;

                case "find":
                    System.out.println("\n>\tКоманда для получения содержимого указанной таблицы\n" +
                            ">\tФормат: find | tableName\n" +
                            ">\tгде tableName - имя таблицы\n");
                    break;

                case "insert":
                    System.out.println("\n>\tКоманда для вставки одной строки в заданную таблицу\n" +
                            ">\tФормат: insert | tableName | column1 | value1 | column2 | value2 | ... | columnN | valueN\n" +
                            ">\tгде: tableName - имя таблицы\n" +
                            ">\tcolumn1 - имя первого столбца записи\n" +
                            ">\tvalue1 - значение первого столбца записи\n" +
                            ">\tcolumn2 - имя второго столбца записи\n" +
                            ">\tvalue2 - значение второго столбца записи\n" +
                            ">\tcolumnN - имя n-го столбца записи\n" +
                            ">\tvalueN - значение n-го столбца записи\n");
                    break;

                case "update":
                    System.out.println("\n>\tКоманда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1\n" +
                            ">\tФормат: update | tableName | column1 | value1 | column2 | value2\n" +
                            ">\tгде: tableName - имя таблицы\n" +
                            ">\tcolumn1 - имя столбца записи которое проверяется\n" +
                            ">\tvalue1 - значение которому должен соответствовать столбец column1 для обновляемой записи\n" +
                            ">\tcolumn2 - имя обновляемого столбца записи\n" +
                            ">\tvalue2 - значение обновляемого столбца записи\n" +
                            ">\tcolumnN - имя n-го обновляемого столбца записи\n" +
                            ">\tvalueN - значение n-го обновляемого столбца записи\n");
                    break;

                case "delete":
                    System.out.println("\n>\tКоманда удаляет одну или несколько записей для которых соблюдается условие column = value\n" +
                            ">\tФормат: delete | tableName | column | value\n" +
                            ">\tгде: tableName - имя таблицы\n" +
                            ">\tColumn - имя столбца записи которое проверяется\n" +
                            ">\tvalue - значение которому должен соответствовать столбец column1 для удаляемой записи\n");
                    break;

                case "help":
                    System.out.println("\n>\tКоманда выводит в консоль список всех доступных команд\n" +
                            ">\tФормат: help (без параметров)\n" +
                            ">\tРасширенный вариант команды help выводит детальную информацию про выбранную команду\n" +
                            ">\tФормат: help | command\n" +
                            ">\tгде: command - название интересующей команды\n");
                    break;

                case "exit":
                    System.out.println("\n>\tКоманда для отключения от БД и выход из приложения\n" +
                            ">\tФормат: exit (без параметров)\n");
                    break;
                case "close":
                    System.out.println("\n>\tКоманда для отключения от БД. При этом программа не завершается и можно подключиться к другой БД.\n" +
                            ">\tФормат: close (без параметров)\n");
                    break;
                default:
                    System.out.println("\n>\tОшибка! Несуществующая команда!\n");
                    System.out.println(fullList);
            }
            return;
        }

        System.out.println(fullList);


    }
}
