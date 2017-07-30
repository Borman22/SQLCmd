package ua.borman.Commands;

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;


public class Tables {
    public static void getTables(ArrayList<String> queryList, DatabaseManager dbm) {
        if (queryList.size() != 1){
            System.out.println(">\tОперация не выполнена. Причина: количество аргументов команды tables != 0\n");
            return;
        }

        if(!dbm.isConnected()){
            System.out.println(">\tЧтобы обращаться к БД необходимо подключиться к БД\n");
            return;
        }

        try {
            ArrayList<String> tablesList = dbm.tables();
            System.out.println(">\tВ базе данных содержатся таблицы: " + tablesList);
            System.out.println(">\tКоличество таблиц = " + tablesList.size());
        } catch (SQLException e) {
            System.out.println(">\tНе удалось получить список всех таблиц");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
