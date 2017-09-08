
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
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class Insert_Test {

    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @BeforeClass
    public static void init(){
        view = new Console();
        dbm = new DatabaseManager(view);
        executor = new CommandExecutor(view, dbm);
        createdbWithoutTables(dbm, view);
    }

    @Before
    public void initTableWith3Rows(){
        createTablesAnd3Rows(dbm, view);
    }

    @Test
    public void insertTest(){

        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));

        Connection connection = connectToSQL(DB_NAME);
        Statement statement = null;
        int countOfRows1 = 0;
        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM \"" + TEST_TABLE + "\"");
            rs.next();
            countOfRows1 = rs.getInt(1);
        } catch (SQLException e) {
            view.writeln("Не могу получить стейтмент");
            view.writeln(e.getLocalizedMessage());
        }

        executor.execute(getQueryString("insert", TEST_TABLE, "col1", "rN_c1_value", "col2", "rN_c2_value"));

        int countOfRows2 = -100;
        try {
            rs = statement.executeQuery("SELECT COUNT(*) FROM \"" + TEST_TABLE + "\"");
            rs.next();
            countOfRows2 = rs.getInt(1);
        } catch (SQLException e) {
            view.writeln("Опять не могу получить стейтмент");
            view.writeln(e.getLocalizedMessage());
        }

        assertEquals(countOfRows1, countOfRows2 - 1);

        disconnect(connection);
        executor.execute("close");
    }
}

