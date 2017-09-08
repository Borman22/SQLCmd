package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;


public class Drop implements Command{

    private final DatabaseManager dbm;
    private final View view;

    public Drop(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }

    @Override
    public void process(List<String> queryList) {

        if (queryList.size() != 2){
            view.writeln(">\tОперация не выполнена. Причина: количество аргументов команды drop != 1\n");
            return;
        }

        if(!dbm.isConnected()){
            view.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1) + "\"";
        try {
            dbm.drop(tableName);
            view.writeln(">\tТаблица удалена\n");
        } catch (SQLException e) {
            view.writeln(">\tНе удалось удалить таблицу");
            view.writeln(">\t" + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("drop");
    }

}
