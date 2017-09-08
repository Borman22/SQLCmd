//package ua.borman.sqlcmd.controller.commands;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import ua.borman.sqlcmd.controller.CommandExecutor;
//import ua.borman.sqlcmd.model.DatabaseManager;
//import ua.borman.sqlcmd.view.Console;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import static org.junit.Assert.assertEquals;
//import static ua.borman.sqlcmd.Templates_For_Tests.*;
//
//public class Delete_Test {
//
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
//    public void deleteTest() {
//        DatabaseManager dbm = new DatabaseManager();
//        CommandExecutor executor = getCommandExecutor(new Console(), dbm);
//        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));
//
//
//        Connection connection = connectToSQL(DB_NAME);
//        Statement statement = null;
//        int countOfRows1 = 0;
//        ResultSet rs;
//        try {
//            statement = connection.createStatement();
//            rs = statement.executeQuery("SELECT COUNT(*) FROM \"" + TEST_TABLE + "\"");
//            rs.next();
//            countOfRows1 = rs.getInt(1);
//        } catch (SQLException e) {
//            System.err.println("Не могу получить стейтмент");
//            System.err.println(e.getLocalizedMessage());
//        }
//
//        assertEquals(countOfRows1, 3);
//
//        executor.execute(getQueryString("delete", TEST_TABLE, "col1", "r1_c1_value"));
//
//        int countOfRows2 = -100;
//        try {
//            rs = statement.executeQuery("SELECT COUNT(*) FROM \"" + TEST_TABLE + "\"");
//            rs.next();
//            countOfRows2 = rs.getInt(1);
//        } catch (SQLException e) {
//            System.err.println("Опять не могу получить стейтмент");
//            e.printStackTrace();
//        }
//
//        assertEquals(countOfRows1 - 1, countOfRows2);
//
//        disconnect(connection);
//        executor.execute("close");
//
//    }
//
//}
