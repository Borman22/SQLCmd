package ua.borman.sqlcmd.controller.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class CreateDB_Test {
    private static Connection connection;

    // Удаляем БД, даже если ее нет, проверяем, что ее действительно нет, тогда создаем
    @Before
    public void init() {
        dropDataBase();
    }

    @Test
    public void CreateDB() {
        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor(new Console(), dbm);
        executor.execute(getQueryString("createdb", DB_NAME, USERNAME, PASSWORD));

        try {
            dbm.close();
        } catch (SQLException e) {
            System.err.println("Не удалось отключиться от БД");
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
