package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.List;

public class Clear {
    public static void clear(List<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();

        if(queryList.size() != 2){
            writer.writeln(">\tОперация не выполнена. Причина: количество аргументов команды clear != 1\n");
            return;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }
        String tableName = "\"" + queryList.get(1) + "\"";
        try {
            dbm.clear(tableName);
            writer.writeln(">\tТаблица очищена.\n");
        } catch (SQLException e) {
            writer.writeln("Не удалось очистить таблицу " + tableName + ". Возможно имя представлено в виде \"schema.tableName\"");
            writer.writeln(">t" + e.getLocalizedMessage());
        }
    }
}
