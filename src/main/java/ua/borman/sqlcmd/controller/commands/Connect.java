package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Connect {

    public static void connect(List<String> queryList, DatabaseManager dbm) {
        View writer = new Console();
        queryList.remove(0); // удаляем команду

        if (queryList.size() == 3 || queryList.size() == 4) {
            try {
                dbm.connect(queryList);
            } catch (SQLException e) {
                writer.writeln(">\tНе удалось подключиться к базе данных. Проверте правильность написания имени БД, логина, пароля, хоста и порта.\n" +
                        ">\tБуквы в верхнем регистре не равны буквам в нижнем");
                writer.writeln(">\tВведите следующую команду\n");
            }
            return;
        }

        writer.writeln(">\tНеправильный формат запроса. Введите запрос в формате:" +
                ">\tconnect|database|username|password или connect|database|username|password|host:port");
    }
}
