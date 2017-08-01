package ua.borman.Commands;
// +

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Delete {
    public static void delete(ArrayList<String> queryList, DatabaseManager dbm) {
        if (queryList.size() < 4){
            System.out.println(">\tОперация не выполнена. Причина: у команды delete должно быть минимум 3 аргумента: " +
                    "tableName, column_1, value_1\n");
            return;
        }

        if(queryList.size() % 2 != 0){
            System.out.println(">\tОперация не выполнена. Причина: запрос не соответствует фомату: " +
                    "tableName, column_1, value_1, column_2, value_2, ..., column_N, value_N \n");
        }

        if(!dbm.isConnected()){
            System.out.println(">\tЧтобы работать с таблицами необходимо подключиться к БД\n");
            return;
        }

        queryList.remove(0);
        queryList.set(0, "\"" + queryList.get(0).trim() + "\""); // имя таблицы заключаем в двойные кавычки
        for (int i = 1; i < queryList.size(); i++) {
            if(i % 2 == 1){
                queryList.set(i, "\"" + queryList.get(i).trim() + "\""); // имя столбца заключаем в двойные кавычки
            } else {
                queryList.set(i, "\'" + queryList.get(i).trim() + "\'"); // значение заключаем в одинарные кавычки
            }
        }
        try {
            dbm.delete(queryList);
            System.out.println(">\tИз таблицы удалены данные\n");
        } catch (SQLException e) {
            System.out.println(">\tНе удалось удалить данные с таблицы.\n");
            System.out.println(">t" + e.getLocalizedMessage());
        }
    }
}
