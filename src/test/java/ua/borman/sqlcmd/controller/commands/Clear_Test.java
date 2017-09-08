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

public class Clear_Test {

    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @BeforeClass
    public static void init(){
        dbm = new DatabaseManager();
        view = new Console();
        executor = new CommandExecutor(view, dbm);
        createdbWithoutTables(dbm, view);
    }

    @Before
    public void initTableWith3Rows(){
        createTablesAnd3Rows(dbm, view);
    }

    @Test
    public void ClearTest(){

        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));

        Connection connection = connectToSQL(DB_NAME);
        Statement statement = null;
        ResultSet rs;
        int countOfRows = -100;

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM \"" + TEST_TABLE + "\"");
            rs.next();
            countOfRows = rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Не могу получить стейтмент");
            e.printStackTrace();
        }

        assertEquals(countOfRows, 3);

        executor.execute(getQueryString("clear", TEST_TABLE));

        try {
            rs = statement.executeQuery("SELECT COUNT(*) FROM \"" + TEST_TABLE + "\"");
            rs.next();
            countOfRows = rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Не могу получить стейтмент");
            e.printStackTrace();
        }

        assertEquals(countOfRows, 0);


        disconnect(connection);
        executor.execute("close");


    }
}
