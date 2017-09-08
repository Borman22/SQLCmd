package ua.borman.sqlcmd.controller.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class CreateDB_Test {

    private static Connection connection;
    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @BeforeClass
    public static void init(){
        dbm = new DatabaseManager();
        view = new Console();
        executor = new CommandExecutor(view, dbm);
    }


    // Удаляем БД, даже если ее нет, проверяем, что ее действительно нет, тогда создаем
    @Before
    public void init2() {
        dropDataBase();
    }

    @Test
    public void CreateDB() {
        executor.execute(getQueryString("connect", " ",USERNAME, PASSWORD));
        executor.execute(getQueryString("createdb", DB_NAME, USERNAME, PASSWORD));

        try {
            dbm.close();
        } catch (SQLException e) {
            System.err.println("Не удалось отключиться от SQL сервера");
            System.out.println(e.getLocalizedMessage());
        }

        connection = connectToSQL(DB_NAME);
        assertNotNull(connection);
    }

    @After
    public void fin(){
        disconnect(connection);
    }
}
