package ua.borman.sqlcmd.controller.commands;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ua.borman.sqlcmd.Templates_For_Tests.*;
import static ua.borman.sqlcmd.Templates_For_Tests.getQueryList;

public class Create_Test {

    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @BeforeClass
    public static void init(){
        dbm = new DatabaseManager();
        view = new Console();
        executor = new CommandExecutor(new Console(), dbm);
        createdbWithoutTables(dbm, view);
    }


    @Test
    public void createTest() {

        List<String> tempList = new ArrayList<>();
        Tables tables = new Tables(dbm, view, tempList);
        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));
        tables.process(getQueryList("tables"));

        int countOfTables1 = tempList.size();  // N

        executor.execute(getQueryString("create", TEST_TABLE, "col1", "col2"));  // N + 1
        tables.process(getQueryList("tables"));

        int countOfTables2 = tempList.size();


        executor.execute(getQueryString("create", TEST_TABLE_FALSE, "col1", "col2"));  // N + 2
        tables.process(getQueryList("tables"));

        int countOfTables3 = tempList.size();

        executor.execute(getQueryString("create", TEST_TABLE_FALSE, "col1", "col2"));  // N + 2 (с таким именем уже есть)
        tables.process(getQueryList("tables"));
        int countOfTables4 = tempList.size();


        executor.execute("close");

        assertEquals(countOfTables1 + 1, countOfTables2);
        assertEquals(countOfTables1 + 2, countOfTables3);
        assertEquals(countOfTables1 + 2, countOfTables4);

    }

}
