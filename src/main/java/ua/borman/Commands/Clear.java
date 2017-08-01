package ua.borman.Commands;
// +

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Clear {
    public static void clear(ArrayList<String> queryList, DatabaseManager dbm) {
        if(queryList.size() != 2){
            System.out.println(">\tОперация не выполнена. Причина: количество аргументов команды clear != 1\n");
            return;
        }

        if(!dbm.isConnected()){
            System.out.println(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }
        String tableName = "\"" + queryList.get(1).trim() + "\"";
        try {
            dbm.clear(tableName);
            System.out.println(">\tТаблица очищена.\n");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу " + tableName + ". Возможно имя представлено в виде \"schema.tableName\"");
            System.out.println(">t" + e.getLocalizedMessage());
        }
    }
}
