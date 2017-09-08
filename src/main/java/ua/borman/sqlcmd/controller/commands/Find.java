package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.controller.Table;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Find implements Command{

    private final DatabaseManager dbm;
    private final View view;

    public Find(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }


    @Override
    public void process(List<String> queryList) {

        if (queryList.size() != 2){
            view.writeln(">\tОперация не выполнена. Причина: у команды find должен быть 1 аргумент: tableName\n");
            return;
        }

        if(!dbm.isConnected()){
            view.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1) + "\""; // имя таблицы заключаем в двойные кавычки
        Table table = null;
        try {
            table = dbm.find(tableName); //"SELECT * FROM users"

            for (int i = 0; i < table.getColumnCount(); i++) {
                view.write(table.getColumnName(i) + "\t");
            }
            view.writeln("");
            for (int i = 0; i < table.getRowCount(); i++) {
                view.writeln(table.getRow(i).toString());
            }
        } catch (SQLException e) {
            view.writeln(">\t\tНе удалось получить данные из таблицы " + tableName);
            view.writeln(">\t\t" + e.getLocalizedMessage());
            return;
        }
//        if(table == null) return;

    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("find");
    }

}
