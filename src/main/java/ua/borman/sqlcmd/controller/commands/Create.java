package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create implements Command{

    private final DatabaseManager dbm;
    private final View view;

    public Create(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }

    @Override
    public void process(List<String> queryList) {
        if (queryList.size() < 2){
            view.writeln(">\tОперация не выполнена. Причина: количество аргументов команды create == 0\n");
            return;
        }

        if(!dbm.isConnected()){
            view.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        queryList.remove(0); // удаляем команду create

        for (int i = 0; i < queryList.size(); i++)
            queryList.set(i, "\"" + queryList.get(i) + "\"");  // Все заключаем в кавычки, потому, что по-другому Postgres не работает с большими буквами

        try {
            dbm.create(queryList);
            view.writeln(">\tТаблица успешно создана\n");
        } catch (SQLException e) {
            view.writeln(">\tНе удалось создать таблицу.");
            view.writeln(">\t" + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("create");
    }

}
