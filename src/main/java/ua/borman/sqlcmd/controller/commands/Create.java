package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Create {
    public static void create(List<String> queryList, DatabaseManager dbm) {
        View writer = new Console();
        if (queryList.size() < 2){
            writer.writeln(">\tОперация не выполнена. Причина: количество аргументов команды create == 0\n");
            return;
        }

        if(!dbm.isConnected()){
            writer.writeln(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        queryList.remove(0); // удаляем команду create

        for (int i = 0; i < queryList.size(); i++)
            queryList.set(i, "\"" + queryList.get(i) + "\"");  // Все заключаем в кавычки, потому, что по-другому Postgres не работает с большими буквами

        try {
            dbm.create(queryList);
            writer.writeln(">\tТаблица успешно создана\n");
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось создать таблицу.");
            writer.writeln(">\t" + e.getLocalizedMessage());
        }
    }
}
