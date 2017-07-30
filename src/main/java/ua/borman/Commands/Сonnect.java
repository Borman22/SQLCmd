/**
 * класс подключается к базе данных, если запрос правильный
 */

package ua.borman.Commands;
// +

import ua.borman.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Сonnect {

    public static void connect(ArrayList<String> queryList, DatabaseManager dbm) {
        for (int i = 0; i < queryList.size(); i++)
            queryList.set(i, queryList.get(i).trim());

        if (queryList.size() == 4) {
            try {
                dbm.connect(queryList.get(1), queryList.get(2), queryList.get(3));
            } catch (SQLException e) {
                System.out.println(">\tНе удалось подключиться к базе данных. Проверте правильность написания имени БД, логина и пароля.\n" +
                        ">\tБуквы в верхнем регистре не равны буквам в нижнем");
                System.out.println(">\tВведите следующую команду\n");
            }
            return;
        }

        System.out.println(">\tНеправильный формат запроса. Введите запрос в формате:" +
                ">\tconnect|database|username|password");
    }
}
