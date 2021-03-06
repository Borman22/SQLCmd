package ua.borman.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class DropDB_Test {


    private static Connection connection;
    private static View view;
    private static DatabaseManager dbm;
    private static CommandExecutor executor;

    @BeforeClass
    public static void init() {
        view = new Console();
        dbm = new DatabaseManager(view);
        executor = new CommandExecutor(view, dbm);
    }

    @Before
    public void init2() {
        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD));
        executor.execute(getQueryString("createdb", DB_NAME_FALSE, USERNAME, PASSWORD));
        executor.execute("close");
    }

    @Test
    public void dropDBTest() {

        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD));

        Connection connection = connectToSQL(DB_NAME_FALSE);
        try {
            if (connection == null || connection.isClosed()) {
                fail("База данных не создана - удалять нечего");
                return;
            }
        } catch (SQLException e) {
            // Do nothing
        }
        try {
            connection.close();
        } catch (SQLException e) {
            view.writeln("Не удалось отключитьс от БД");
        }
        executor.execute(getQueryString("dropDB", DB_NAME_FALSE));
        executor.execute("close");

        connection = connectToSQL(DB_NAME_FALSE);


        try {
            if (connection == null || connection.isClosed()) {
                view.writeln("Все ОК, БД удалилась");
            } else {
                fail("База не удалилась - удалось к ней подключитсья");
                disconnect(connection);
            }
        } catch (SQLException e) {
            view.writeln("Попытались сделать connection.isClosed(), но connection == " + connection);
            view.writeln(e.getLocalizedMessage());
        }

    }
}
