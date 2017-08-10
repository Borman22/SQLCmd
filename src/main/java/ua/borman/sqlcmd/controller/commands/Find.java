package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.controller.Table;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;

public class Find {
    public static Table find(ArrayList<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();
        if (queryList.size() != 2){
            writer.writeln(">\tОперация не выполнена. Причина: у команды find должен быть 1 аргумент: tableName\n");
            return null;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return null;
        }

        String tableName = "\"" + queryList.get(1) + "\""; // имя таблицы заключаем в двойные кавычки
        try {
            Table table = dbm.find(tableName); //"SELECT * FROM users"

            for (int i = 0; i < table.getColumnCount(); i++) {
                writer.write(table.getColumnName(i) + "\t\t");
            }
            writer.writeln("");
            for (int i = 0; i < table.getRowCount(); i++) {
                writer.writeln(table.getRow(i).toString());
            }
            return table;
        } catch (SQLException e) {
            writer.writeln(">\t\tНе удалось получить данные из таблицы " + tableName);
            writer.writeln(">\t\t" + e.getLocalizedMessage());
        }
        return null;
    }
}
