package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;


public class Drop {
    public static void drop(ArrayList<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();
        if (queryList.size() != 2){
            writer.writeln(">\tОперация не выполнена. Причина: количество аргументов команды drop != 1\n");
            return;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1).trim() + "\"";
        try {
            dbm.drop(tableName);
            writer.writeln(">\tТаблица удалена\n");
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось удалить таблицу");
            writer.writeln(">\t" + e.getLocalizedMessage());
        }
    }
}
