package ua.borman.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class Clear_Test {
    @BeforeClass
    public static void init(){
        createdbWithoutTables();
    }

    @Before
    public void initTableWith3Rows(){
        createTablesAnd3Rows();
    }

    @Test
    public void ClearTest(){
        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD), dbm);

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

        executor.execute(getQueryString("clear", TEST_TABLE), dbm);

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
        executor.execute("close", dbm);


    }
}