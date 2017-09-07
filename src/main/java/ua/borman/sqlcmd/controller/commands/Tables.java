package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;


public class Tables {
    public static List<String> getTables(List<String> queryList, DatabaseManager dbm) {
        View writer = new Console();
        if (queryList.size() != 1){
            writer.writeln(">\tОперация не выполнена. Причина: количество аргументов команды tables != 0\n");
            return null;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы обращаться к БД необходимо подключиться к БД\n");
            return null;
        }

        try {
            List<String> tablesList = dbm.tables();
            writer.writeln(">\tВ базе данных содержатся таблицы: " + tablesList);
            writer.writeln(">\tКоличество таблиц = " + tablesList.size());
            return tablesList;
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось получить список всех таблиц");
            writer.writeln(e.getLocalizedMessage());
            return null;
        }

    }
}
