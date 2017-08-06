package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;

public class Insert {
    public static void insert(ArrayList<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();
        if (queryList.size() < 4){
            writer.writeln(">\tОперация не выполнена. Причина: у команды insert должно быть минимум 3 аргумента: " +
                    "tableName, column_1, value_1\n");
            return;
        }

        if(queryList.size() % 2 != 0){
            writer.writeln(">\tОперация не выполнена. Причина: запрос не соответствует фомату: " +
                    "tableName, column_1, value_1, column_2, value_2, ..., column_N, value_N \n");
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }


        queryList.remove(0);
        queryList.set(0, "\"" + queryList.get(0).trim() + "\""); // имя таблицы заключаем в двойные кавычки
        for (int i = 1; i < queryList.size(); i++) {
            if(i % 2 == 1){
                queryList.set(i, "\"" + queryList.get(i).trim() + "\""); // имя столбца заключаем в двойные кавычки
            } else {
                queryList.set(i, "\'" + queryList.get(i).trim() + "\'"); // значение заключаем в одинарные кавычки
            }
        }
        try {
            dbm.insert(queryList);
            writer.writeln(">\tВ таблицу вставлена строка\n");
        } catch (SQLException e) {
            writer.writeln(">\tВ таблицу не удалось вставить строку.");
            writer.writeln(">\t" + e.getLocalizedMessage());
        }
    }
}
