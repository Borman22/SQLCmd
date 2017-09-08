package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.List;

public class Close implements Command{

    private final DatabaseManager dbm;
    private final View view;

    public Close(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }


    @Override
    public void process(List<String> queryList) {

         try {
            int status = dbm.close();
            if (status == 0) {
                view.writeln(">\tУспешно отключились от базы данныых ");
            } else {
                view.writeln(">\tКоманда не выполнена, посколькy программа не была подключена ни к одной БД");
            }

        } catch (SQLException e) {
             view.writeln(">\tНе удалось отключиться от базы данных ");
             view.writeln(">\t" + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("close");
    }


}
