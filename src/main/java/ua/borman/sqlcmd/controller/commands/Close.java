package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;

public class Close {
    public static void close(DatabaseManager dbm){

        View writer = new Console();
         try {
            int status = dbm.close();
            if (status == 0) {
                writer.writeln(">\tУспешно отключились от базы данныых ");
            } else {
                writer.writeln(">\tКоманда не выполнена, посколькy программа не была подключена ни к одной БД");
            }

        } catch (SQLException e) {
             writer.writeln(">\tНе удалось отключиться от базы данных ");
             writer.writeln(">\t" + e.getLocalizedMessage());
        }
    }
}
