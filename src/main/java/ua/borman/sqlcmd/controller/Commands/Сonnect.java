package ua.borman.sqlcmd.controller.Commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;

public class Сonnect {

    public static void connect(ArrayList<String> queryList, DatabaseManager dbm) {
        Writer writer = new ConsoleWriter();
        for (int i = 0; i < queryList.size(); i++)
            queryList.set(i, queryList.get(i).trim());

        if (queryList.size() == 4) {
            try {
                dbm.connect(queryList.get(1), queryList.get(2), queryList.get(3));
            } catch (SQLException e) {
                writer.writeln(">\tНе удалось подключиться к базе данных. Проверте правильность написания имени БД, логина и пароля.\n" +
                        ">\tБуквы в верхнем регистре не равны буквам в нижнем");
                writer.writeln(">\tВведите следующую команду\n");
            }
            return;
        }

        writer.writeln(">\tНеправильный формат запроса. Введите запрос в формате:" +
                ">\tconnect|database|username|password");
    }
}
