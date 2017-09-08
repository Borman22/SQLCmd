//package ua.borman.sqlcmd.controller.commands;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import ua.borman.sqlcmd.controller.CommandExecutor;
//import ua.borman.sqlcmd.model.DatabaseManager;
//import ua.borman.sqlcmd.view.Console;
//
//import java.sql.SQLException;
//import java.util.HashSet;
//import java.util.Set;
//
//import static junit.framework.TestCase.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static ua.borman.sqlcmd.Templates_For_Tests.*;
//import static ua.borman.sqlcmd.Templates_For_Tests.PASSWORD;
//import static ua.borman.sqlcmd.Templates_For_Tests.USERNAME;
//
//public class Tables_Test {
//    @BeforeClass
//    public static void init(){
//        createdbWithoutTables();
//    }
//
//    @Before
//    public void initTableWith3Rows(){
//        createTablesAnd3Rows();
//    }
//
//    @Test
//    public void tablesTest(){
//        DatabaseManager dbm = new DatabaseManager();
//        CommandExecutor executor = getCommandExecutor(new Console(), dbm);
//        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));
//
//        Set<String> tablesSet1 = new HashSet<>(Tables.getTables(getQueryList("tables"), dbm)); // [testTableByBorman]
//        executor.execute(getQueryString("create", TEST_TABLE_FALSE, "col1", "col2"));
//
//        Set<String> tablesSet2 = new HashSet<>(Tables.getTables(getQueryList("tables"),dbm));  // [testTableByBorman, testTableFalseByBorman]
//        assertFalse(tablesSet1.equals(tablesSet2));
//
//        tablesSet2.remove(TEST_TABLE_FALSE);  // [testTableByBorman]
//        assertTrue(tablesSet1.equals(tablesSet2));
//
//        executor.execute("close");
//
//    }
//
//}
