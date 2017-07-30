package ua.borman.Commands;
// +

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;


public class Drop {
    public static void drop(ArrayList<String> queryList, DatabaseManager dbm) {
        if (queryList.size() != 2){
            System.out.println(">\tОперация не выполнена. Причина: количество аргументов команды drop != 1\n");
            return;
        }

        if(!dbm.isConnected()){
            System.out.println(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1).trim() + "\"";
        try {
            dbm.drop(tableName);
            System.out.println(">\tТаблица удалена\n");
        } catch (SQLException e) {
            System.out.println(">\tНе удалось удалить таблицу");
            System.out.println(">\t" + e.getLocalizedMessage());
        }
    }
}
