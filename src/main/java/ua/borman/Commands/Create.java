package ua.borman.Commands;
// +
import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Create {
    public static void create(ArrayList<String> queryList, DatabaseManager dbm) {
        if (queryList.size() < 2){
            System.out.println(">\tОперация не выполнена. Причина: количество аргументов команды create == 0\n");
            return;
        }

        if(!dbm.isConnected()){
            System.out.println(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        queryList.remove(0); // удаляем команду create

        for (int i = 0; i < queryList.size(); i++)
            queryList.set(i, "\"" + queryList.get(i).trim() + "\"");  // Все заключаем в кавычки, потому, что по-другому Postgres не работает с большими буквами

        try {
            dbm.create(queryList);
            System.out.println(">\tТаблица успешно создана\n");
        } catch (SQLException e) {
            System.out.println(">\tНе удалось создать таблицу.");
            System.out.println(">t" + e.getLocalizedMessage());
        }
    }
}
