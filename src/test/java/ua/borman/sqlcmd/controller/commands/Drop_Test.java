//package ua.borman.sqlcmd.controller.commands;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import ua.borman.sqlcmd.controller.CommandExecutor;
//import ua.borman.sqlcmd.model.DatabaseManager;
//import ua.borman.sqlcmd.view.Console;
//
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//import static ua.borman.sqlcmd.Templates_For_Tests.*;
//import static ua.borman.sqlcmd.Templates_For_Tests.TEST_TABLE_FALSE;
//import static ua.borman.sqlcmd.Templates_For_Tests.getQueryList;
//
//public class Drop_Test {
//
//    @BeforeClass
//    public static void init() {
//        createdbWithoutTables();
//    }
//
//    @Test
//    public void dropTest() {
//        DatabaseManager dbm = new DatabaseManager();
//        CommandExecutor executor = getCommandExecutor(new Console(), dbm);
//        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));
//
//
//        executor.execute(getQueryString("create", TEST_TABLE, "col1", "col2"));  // N + 1
//        executor.execute(getQueryString("create", TEST_TABLE_FALSE, "col1", "col2"));  // N + 2
//        int countOfTables1 = Tables.getTables(getQueryList("tables"), dbm).size();  // N + 2
//
//        executor.execute(getQueryString("drop", TEST_TABLE));
//        int countOfTables2 = Tables.getTables(getQueryList("tables"), dbm).size(); // N + 1
//
//
//        executor.execute(getQueryString("drop", TEST_TABLE_FALSE));
//        int countOfTables3 = Tables.getTables(getQueryList("tables"), dbm).size();  // N
//
//        executor.execute(getQueryString("drop", TEST_TABLE_FALSE));
//        int countOfTables4 = Tables.getTables(getQueryList("tables"), dbm).size();  // N
//
//        executor.execute("close");
//
//        assertEquals(countOfTables1 - 1, countOfTables2);
//        assertEquals(countOfTables1 - 2, countOfTables3);
//        assertEquals(countOfTables1 - 2, countOfTables4);
//
//    }
//}
