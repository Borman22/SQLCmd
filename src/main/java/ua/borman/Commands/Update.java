package ua.borman.Commands;
// TODO Надо сделать вывод со старыми значениями обновленных данных

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Update {
    public static void update(ArrayList<String> queryList, DatabaseManager dbm) {
        if (queryList.size() != 6){ // update | tableName | column1 | value1 | column2 | value2
            System.out.println(">\tОперация не выполнена. Причина: у команды update должно быть 5 аргументов: " +
                    "tableName, verifyColumn, verifyValue, goalColumn, newValue\n");
            return;
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
            dbm.update(queryList);  // TODO Подумать над тем, как получить таблицу из строк, которые были изменены
            System.out.println(">\tСтрока модифицирована\n");
        } catch (SQLException e) {
            System.out.println(">\tНе удалось модифицировать строку.");
            System.out.println(">\t" + e.getLocalizedMessage());
        }


    }
}
