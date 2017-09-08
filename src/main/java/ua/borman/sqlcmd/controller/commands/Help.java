package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.util.List;

public class Help implements Command{

    private View view;

    public Help(View view){
        this.view = view;
    }


    private static String fullList = "\n>\tСписок всех поддерживаемых комманд:\n" +
            ">\t\tconnect - подключение к БД. Формат: connect|database|username|password или connect|database|username|password|host:port\n" +
            ">\t\ttables - вывести список всех таблиц. Формат: tables\n" +
            ">\t\tclear - очистить таблицу. Формат: clear | tableName\n" +
            ">\t\tdrop - удалить таблицу из БД. Формат: drop | tableName\n" +
            ">\t\tdropDB - удалить БД. Формат: dropDB | DBName | username | password  или  dropDB | DBName если подключение к SQL серверу уже установлено\n" +
            ">\t\tcreate - создать новую таблицу в БД. Формат: create | tableName | column1 | column2 | ... | columnN\n" +
            ">\t\tcreateDB - создать новую БД. Формат: createDB | DBName | username | password  или  createDB | DBName если подключение к SQL серверу уже установлено\n" +
            ">\t\tfind - показать содержимое таблицы. Формат: find | tableName\n" +
            ">\t\tinsert - вставить строку в таблицу. Формат: insert | tableName | column1 | value1 | column2 | value2 | ... | columnN | valueN\n" +
            ">\t\tupdate - заменить значение в колонке column2 на value2, если в column1 содержится value1. Формат: update | tableName | column1 | value1 | column2 | value2\n" +
            ">\t\tdelete - удалить все строки, у которых в колонке columnN содержится значение value. Формат: delete | tableName | columnN | value\n" +
            ">\t\thelp - помощь. Формат 1: help. \tФормат 2: help | commandName\n" +
            ">\t\texit - закрыть соединение с БД и завершить приложение. Формат: exit\n" +
            ">\t\tclose - закрыть соединение с БД. Формат: close\n" +
            ">\tДля более детального описания необходимой команды, напишите help|\"название команды\"\n" +
            ">\tНапример help|create\n";

    @Override
    public void process(List<String> queryList) {

        if (queryList.size() > 1) {
            queryList.set(1, queryList.get(1).trim().toLowerCase());
            switch (queryList.get(1)) {
                case "connect":
                    view.writeln("\n>\tКоманда для подключения к соответствующей БД\n" +
                            ">\tФормат команды: connect|database|username|password (используется для localhost:5432) или  connect|database|username|password|host:port\n" +
                            ">\tгде: database - имя БД\n" +
                            ">\tusername - имя пользователя БД\n" +
                            ">\tpassword - пароль пользователя БД\n" +
                            ">\thost - адресс SQL сервера\n" +
                            ">\tport - номер порта\n");
                    break;

                case "tables":
                    view.writeln("\n>\tКоманда выводит список всех таблиц\n" +
                            ">\tФормат: tables (без параметров)\n");
                    break;

                case "clear":
                    view.writeln("\n>\tКоманда очищает содержимое указанной (всей) таблицы\n" +
                            ">\tФормат: clear | tableName\n" +
                            ">\tгде tableName - имя очищаемой таблицы\n");
                    break;

                case "drop":
                    view.writeln("\n>\tКоманда удаляет заданную таблицу\n" +
                            ">\tФормат: drop | tableName\n" +
                            ">\tгде tableName - имя удаляемой таблицы\n");
                    break;

                case "dropDB":
                    view.writeln("\n>\tКоманда удаляет базу данных\n" +
                            ">\tФормат: dropDB | DBName | username | password  или  dropDB | DBName если подключение к SQL серверу уже установлено\n" +
                            ">\tгде DBName - имя базы данных\n" +
                            ">\tusername - имя пользователя БД\n" +
                            ">\tpassword - пароль пользователя БД\n");
                    break;

                case "create":
                    view.writeln("\n>\tКоманда создает новую таблицу с заданными полями\n" +
                            ">\tФормат: create | tableName | column1 | column2 | ... | columnN\n" +
                            ">\tгде: tableName - имя таблицы\n" +
                            ">\tcolumn1 - имя первого столбца записи\n" +
                            ">\tcolumn2 - имя второго столбца записи\n" +
                            ">\tcolumnN - имя n-го столбца записи\n");
                    break;

                case "createDB":
                    view.writeln("\n>\tКоманда создает базу данных\n" +
                            ">\tФормат: createDB | DBName | username | password  или  createDB | DBName если подключение к SQL серверу уже установлено\n" +
                            ">\tгде DBName - имя базы данных\n" +
                            ">\tusername - имя пользователя БД\n" +
                            ">\tpassword - пароль пользователя БД\n");
                    break;

                case "find":
                    view.writeln("\n>\tКоманда для получения содержимого указанной таблицы\n" +
                            ">\tФормат: find | tableName\n" +
                            ">\tгде tableName - имя таблицы\n");
                    break;

                case "insert":
                    view.writeln("\n>\tКоманда для вставки одной строки в заданную таблицу\n" +
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
                    view.writeln("\n>\tКоманда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1\n" +
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
                    view.writeln("\n>\tКоманда удаляет одну или несколько записей для которых соблюдается условие column = value\n" +
                            ">\tФормат: delete | tableName | columnN | value\n" +
                            ">\tгде: tableName - имя таблицы\n" +
                            ">\tColumn - имя столбца записи которое проверяется\n" +
                            ">\tvalue - значение которому должен соответствовать столбец column1 для удаляемой записи\n");
                    break;

                case "help":
                    view.writeln("\n>\tКоманда выводит в консоль список всех доступных команд\n" +
                            ">\tФормат: help (без параметров)\n" +
                            ">\tРасширенный вариант команды help выводит детальную информацию про выбранную команду\n" +
                            ">\tФормат: help | command\n" +
                            ">\tгде: command - название интересующей команды\n");
                    break;

                case "exit":
                    view.writeln("\n>\tКоманда для отключения от БД и выход из приложения\n" +
                            ">\tФормат: exit (без параметров)\n");
                    break;
                case "close":
                    view.writeln("\n>\tКоманда для отключения от БД. При этом программа не завершается и можно подключиться к другой БД.\n" +
                            ">\tФормат: close (без параметров)\n");
                    break;
                default:
                    view.writeln("\n>\tОшибка! Несуществующая команда!\n");
                    view.writeln(fullList);
            }
            return;
        }

        view.writeln(fullList);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

}
