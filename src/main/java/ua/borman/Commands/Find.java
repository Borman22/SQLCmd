package ua.borman.Commands;

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Find {
    public static void find(ArrayList<String> queryList, DatabaseManager dbm) {
        if (queryList.size() != 2){
            System.out.println(">\tОперация не выполнена. Причина: у команды find должен быть 1 аргумент: tableName\n");
            return;
        }

        if(!dbm.isConnected()){
            System.out.println(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        String tableName = "\"" + queryList.get(1).trim() + "\""; // имя таблицы заключаем в двойные кавычки
        try {
            dbm.find(tableName); //"SELECT * FROM users"
        } catch (SQLException e) {
            System.out.println(">\t\tНе удалось получить данные из таблицы " + tableName);
            System.out.println(">\t\t" + e.getLocalizedMessage());
        }


    }
}
