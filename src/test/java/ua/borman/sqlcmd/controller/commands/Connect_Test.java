package ua.borman.sqlcmd.controller.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ua.borman.sqlcmd.Templates_For_Tests.*;
import static ua.borman.sqlcmd.Templates_For_Tests.PASSWORD_FALSE;
import static ua.borman.sqlcmd.Templates_For_Tests.USERNAME_FALSE;

public class Connect_Test {

    private static Connection connection;

    @BeforeClass
    public static void init() {
        connection = connectToSQL();
    }

    @Test
    public void connectWithTrueParam() {
        int countOfActiveConnection = getCountOfActiveConnection();

        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD), dbm);
        assertEquals(countOfActiveConnection + 1, getCountOfActiveConnection());
        executor.execute("close", dbm);
    }

    @Test
    public void connectWithFalsePassword(){     // Из-за многопоточности этот тест часто не проходит
        int countOfActiveConnection = getCountOfActiveConnection();

        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD_FALSE), dbm);
        assertEquals(countOfActiveConnection, getCountOfActiveConnection());
        executor.execute("close", dbm);
    }

    @Test
    public void connectWithFalseUsername(){
        int countOfActiveConnection = getCountOfActiveConnection();

        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", "", USERNAME_FALSE, PASSWORD), dbm);
        assertEquals(countOfActiveConnection, getCountOfActiveConnection());
        executor.execute("close", dbm);
    }

    @Test
    public void connectWithFalseUsernameAndPassword(){
        int countOfActiveConnection = getCountOfActiveConnection();

        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", "", USERNAME_FALSE, PASSWORD_FALSE), dbm);
        assertEquals(countOfActiveConnection, getCountOfActiveConnection());
        executor.execute("close", dbm);
    }



    private int getCountOfActiveConnection() {
        if(connection == null)
            return -1;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM \"pg_stat_activity\"");
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("Не удалось получить statement в @Before");
            System.err.println(e.getLocalizedMessage());
        }
        return -1;
    }

    @AfterClass
    public static void fin(){
        disconnect(connection);
    }
}
