package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;


public class Tables {
    public static void getTables(ArrayList<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();
        if (queryList.size() != 1){
            writer.writeln(">\tОперация не выполнена. Причина: количество аргументов команды tables != 0\n");
            return;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы обращаться к БД необходимо подключиться к БД\n");
            return;
        }

        try {
            ArrayList<String> tablesList = dbm.tables();
            writer.writeln(">\tВ базе данных содержатся таблицы: " + tablesList);
            writer.writeln(">\tКоличество таблиц = " + tablesList.size());
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось получить список всех таблиц");
            writer.writeln(e.getLocalizedMessage());
        }

    }
}
