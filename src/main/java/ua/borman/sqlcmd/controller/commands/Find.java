package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.controller.DataSet;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Find {
    public static void find(ArrayList<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();
        if (queryList.size() != 2){
            writer.writeln(">\tОперация не выполнена. Причина: у команды find должен быть 1 аргумент: tableName\n");
            return;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1).trim() + "\""; // имя таблицы заключаем в двойные кавычки
        try {
            List<DataSet> tableContent = dbm.find(tableName); //"SELECT * FROM users"

            if(tableContent.size() != 0) {
                List<String> colNames = tableContent.get(0).getColNames();
                for (String colName : colNames) {
                    writer.write(colName + "\t\t");
                }

                writer.write("\n");     // TODO сделать нормальный форматированный вывод
                for (DataSet ds : tableContent) {
                    writer.write(ds.toString());
                    writer.write("\n");
                }
            }
        } catch (SQLException e) {
            writer.writeln(">\t\tНе удалось получить данные из таблицы " + tableName);
            writer.writeln(">\t\t" + e.getLocalizedMessage());
        }


    }
}
