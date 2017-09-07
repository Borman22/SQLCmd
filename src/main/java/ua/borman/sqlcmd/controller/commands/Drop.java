package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;


public class Drop {
    public static void drop(List<String> queryList, DatabaseManager dbm) {
        View writer = new Console();
        if (queryList.size() != 2){
            writer.writeln(">\tОперация не выполнена. Причина: количество аргументов команды drop != 1\n");
            return;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1) + "\"";
        try {
            dbm.drop(tableName);
            writer.writeln(">\tТаблица удалена\n");
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось удалить таблицу");
            writer.writeln(">\t" + e.getLocalizedMessage());
        }
    }
}
