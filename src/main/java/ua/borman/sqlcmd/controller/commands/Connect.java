// +
package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Connect implements Command{

    private final DatabaseManager dbm;
    private final View view;

    public Connect(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }

    @Override
    public void process(List<String> queryList) {

        queryList.remove(0); // удаляем команду

        if (queryList.size() == 3 || queryList.size() == 4) {
            try {
                dbm.connect(queryList);
            } catch (SQLException e) {
                view.writeln(">\tНе удалось подключиться к базе данных. Проверте правильность написания имени БД, логина, пароля, хоста и порта.\n" +
                        ">\tБуквы в верхнем регистре не равны буквам в нижнем.");
                view.writeln(">\tВведите следующую команду\n");
            }
            return;
        }

        view.writeln(">\tНеправильный формат запроса. Введите запрос в формате:" +
                ">\tconnect|database|username|password или connect|database|username|password|host:port");
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }
}
