package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Tables implements Command {

    private final DatabaseManager dbm;
    private final View view;
    private List<String> tempList = new ArrayList<>();

    public Tables(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }

    public Tables(DatabaseManager dbm, View view, List<String> tempList) {
        this(dbm, view);
        this.tempList = tempList;
    }


    @Override
    public void process(List<String> queryList) {

        if (queryList.size() != 1){
            view.writeln(">\tОперация не выполнена. Причина: количество аргументов команды tables != 0\n");
            return;
        }

        if(!dbm.isConnected()){
            view.writeln(">\tЧтобы обращаться к БД необходимо подключиться к БД\n");
            return;
        }

        try {
            List<String> tablesList = dbm.tables();
            view.writeln(">\tВ базе данных содержатся таблицы: " + tablesList);
            view.writeln(">\tКоличество таблиц = " + tablesList.size());
            tempList.addAll(tablesList);
        } catch (SQLException e) {
            view.writeln(">\tНе удалось получить список всех таблиц");
            view.writeln(e.getLocalizedMessage());
        }

    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

}
