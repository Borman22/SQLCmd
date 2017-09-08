package ua.borman.sqlcmd;

import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.controller.commands.*;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

public class Templates_For_Tests {

    private static Connection connection = null;
    private static final String HOST = "jdbc:postgresql://localhost:5432/";
    private static final String LOGGER_LEVEL_OFF = "?loggerLevel=OFF";
    public static final String DB_NAME = "TestDB";
    public static final String DB_NAME_FALSE = "TestDBFalse";
    public static final String TEST_TABLE = "testTableByBorman";
    public static final String TEST_TABLE_FALSE = "testTableFalseByBorman";
    public static final String USERNAME = "postgres";
    public static final String USERNAME_FALSE = "postgreS";
    public static final String PASSWORD = "root";
    public static final String PASSWORD_FALSE = "rooT";
    private static View view = new Console();
    private static DatabaseManager dbm = new DatabaseManager();
    private static CommandExecutor executor = new CommandExecutor(view, dbm);

    public static Connection connectToSQL(String... dbNameArray){
        String dbName;
        if(dbNameArray.length == 0){
            dbName = "";
        } else {
            dbName = dbNameArray[0];
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(">\tОШИБКА! Не подключен SQL драйвер JDBC!\n");
            return null;
        }

        try {
            connection = DriverManager.getConnection(HOST + dbName + LOGGER_LEVEL_OFF, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // System.err.println("Не удается подключиться к БД. Скорее всего ее нет.");
            return null;
        }
        try {
            if ((connection != null) && (!connection.isClosed()))
                return connection;
        } catch (SQLException e) {
            System.err.println("Не удается подключиться к БД");
            return null;
        }
        return null;
    }

    public static void disconnect(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Не удается отключиться от SQL сервера");
        }
    }

    public static void createdbWithoutTables(){
        Connect connect = new Connect(dbm, view);
        connect.process(getQueryList("connect", " ", USERNAME, PASSWORD));

        new CreateDB(dbm, view).process(getQueryList("createDB", DB_NAME, USERNAME, PASSWORD));
        // Если не удалось создать БД, значит она уже есть
        try {
            dbm.close();
        } catch (SQLException e) {
            //DO NOTHING
        }

        connect.process(getQueryList("connect", DB_NAME, USERNAME, PASSWORD));
//        Drop.drop(getQueryList("drop", TEST_TABLE), dbm);
//        Drop.drop(getQueryList("drop", TEST_TABLE_FALSE), dbm);
        try {
            dbm.close();
        } catch (SQLException e) {
            //DO NOTHING
        }
    }

    public static List<String> getQueryList(String... elementsOfQuery){
        List<String> queryList = new ArrayList<>();
        Collections.addAll(queryList, elementsOfQuery);
        return queryList;
    }

    public static String getQueryString(String... elementsOfQuery){
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < elementsOfQuery.length - 1; i++) {
            sb.append(elementsOfQuery[i]);
            sb.append("|");
        }
        sb.append(elementsOfQuery[i]);
        return sb.toString();
    }

    public static void dropDataBase(){
        Connection connection = connectToSQL();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Не удалось создать Statement");
            System.err.println(e.getLocalizedMessage());
            return;
        }
        try {
            statement.executeUpdate("DROP DATABASE " + "\"" + DB_NAME + "\"");
        } catch (SQLException e) {
            System.out.println("Не удается удалить базу данных");
            System.out.println(e.getLocalizedMessage());
        }

        try {
            connection.close();
        } catch (SQLException e) { /* не удалось закрыть соединение с БД  */  }

        connection = connectToSQL(DB_NAME);
        try {
            if(connection == null || connection.isClosed()){
                // БД нет - то, чего мы и добивались
            } else {
                fail("Ошибка тестов, БД существует до ее создания");
                disconnect(connection);
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static CommandExecutor getCommandExecutor(View view, DatabaseManager dbm){
        if(executor == null)
            executor = new CommandExecutor(view, dbm);
        return executor;
    }

    public static void createTablesAnd3Rows(){
        DatabaseManager dbm = new DatabaseManager();
        View view = new Console();
        createdbWithoutTables();

        CommandExecutor executor = getCommandExecutor(view, dbm);
        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));
        executor.execute(getQueryString("create", TEST_TABLE, "col1", "col2"));
        executor.execute(getQueryString("insert", TEST_TABLE, "col1", "r1_c1_value", "col2", "r1_c2_value"));
        executor.execute(getQueryString("insert", TEST_TABLE, "col1", "r2_c1_value", "col2", "r2_c2_value"));
        executor.execute(getQueryString("insert", TEST_TABLE, "col1", "r3_c1_value", "col2", "r3_c2_value"));
    }

}
