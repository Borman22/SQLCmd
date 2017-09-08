package ua.borman.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static ua.borman.sqlcmd.Templates_For_Tests.*;
import static ua.borman.sqlcmd.Templates_For_Tests.PASSWORD;
import static ua.borman.sqlcmd.Templates_For_Tests.USERNAME;

public class Tables_Test {

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

    @Before
    public void initTableWith3Rows(){
        createTablesAnd3Rows(dbm, view);
    }

    @Test
    public void tablesTest(){

        List<String> tempList = new ArrayList<>();
        Tables tables = new Tables(dbm, view, tempList);
        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));
        tables.process(getQueryList("tables"));

        Set<String> tablesSet1 = new HashSet<>(tempList); // [testTableByBorman]
        executor.execute(getQueryString("create", TEST_TABLE_FALSE, "col1", "col2"));

        tables.process(getQueryList("tables"));
        Set<String> tablesSet2 = new HashSet<>(tempList); // [testTableByBorman, testTableFalseByBorman]
        assertFalse(tablesSet1.equals(tablesSet2));

        tablesSet2.remove(TEST_TABLE_FALSE);  // [testTableByBorman]
        assertTrue(tablesSet1.equals(tablesSet2));

        executor.execute("close");

    }

}
