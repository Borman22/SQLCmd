package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;

public class Close {
    public static void close(DatabaseManager dbm){

         Writer writer = new ConsoleWriter();
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
