package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Update {
    public static void update(List<String> queryList, DatabaseManager dbm) {
        View writer = new Console();
        if (queryList.size() != 6) { // update | tableName | column1 | value1 | column2 | value2
            writer.writeln(">\tОперация не выполнена. Причина: у команды update должно быть 5 аргументов: " +
                    "tableName, verifyColumn, verifyValue, goalColumn, newValue\n");
            return;
        }

        if (!dbm.isConnected()) {
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        queryList.remove(0);
        queryList.set(0, "\"" + queryList.get(0) + "\""); // имя таблицы заключаем в двойные кавычки
        for (int i = 1; i < queryList.size(); i++) {
            if (i % 2 == 1) {
                queryList.set(i, "\"" + queryList.get(i) + "\""); // имя столбца заключаем в двойные кавычки
            } else {
                queryList.set(i, "\'" + queryList.get(i) + "\'"); // значение заключаем в одинарные кавычки
            }
        }

        String verifyColumn = queryList.get(1);
        String verifyValue = queryList.get(2);

        try {
            dbm.update(queryList);  // TODO Подумать над тем, как получить таблицу из строк, которые были изменены
            writer.writeln(">\tСтрока модифицирована\n");
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось модифицировать строку.");
            writer.writeln(">\t" + e.getLocalizedMessage());
        }


    }
}
