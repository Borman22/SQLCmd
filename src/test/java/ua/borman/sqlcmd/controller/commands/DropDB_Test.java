package ua.borman.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class DropDB_Test {

    @Before
    public void init() {
        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("createdb", DB_NAME_FALSE, USERNAME, PASSWORD), dbm);
        executor.execute("close", dbm);
    }

    @Test
    public void dropDBTest() {
        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD), dbm);

        Connection connection = connectToSQL(DB_NAME_FALSE);
        try {
            if(connection == null || connection.isClosed()){
                fail("База данных не создана - удалять нечего");
                return;
            }
        } catch (SQLException e) {
            // Do nothing
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Не удалось отключитьс от БД");
        }
        executor.execute(getQueryString("dropDB", DB_NAME_FALSE), dbm);

        connection = connectToSQL(DB_NAME_FALSE);
        try {
            System.out.println(connection);
            if(connection != null && !connection.isClosed()){
                fail("База не удалилась - удалось к ней подключитсья");
                disconnect(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        executor.execute("close", dbm);
    }
}
