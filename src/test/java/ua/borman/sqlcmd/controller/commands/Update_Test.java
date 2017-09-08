package ua.borman.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;
import static ua.borman.sqlcmd.Templates_For_Tests.*;
import static ua.borman.sqlcmd.Templates_For_Tests.DB_NAME;
import static ua.borman.sqlcmd.Templates_For_Tests.TEST_TABLE;

public class Update_Test {

    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @BeforeClass
    public static void init(){
        view = new Console();
        dbm = new DatabaseManager(view);
        executor = new CommandExecutor(view, dbm);
        createdbWithoutTables(dbm, view);
    }

    @Before
    public void initTableWith3Rows(){
        createTablesAnd3Rows(dbm, view);
    }

    @Test
    public void updateTest() {

        // заменить значение в column2 на value2, если в column1 содержится value1. Формат: update | tableName | column1 | value1 | column2 | value2
        executor.execute(getQueryString("connect", DB_NAME, USERNAME, PASSWORD));

        Connection connection = connectToSQL(DB_NAME);
        Statement statement = null;
        ResultSet rs;

        String value1 = "r2_c1_value";
        String value2 = "new_value";

        Set<String> result1 = new HashSet<>();
        Set<String> result2 = new HashSet<>();

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT \"col2\" FROM \"" + TEST_TABLE + "\" WHERE  \"col1\" = \'" + value1 + "\'");

            while (rs.next()) {
                result1.add(rs.getString(1));
            }
        } catch (SQLException e) {
            view.writeln("Не могу получить стейтмент или выполнить executeQuery");
            view.writeln(e.getLocalizedMessage());
        }

        // Если во второй колонке нет ни одного значения, которое надо поменять на value2, то нет смысла дальше тестить
        boolean haveValueForUpdate = false;
        for (String valueInCol2 : result1) {
            if (!valueInCol2.equals(value2))
                haveValueForUpdate = true;
        }
        if (!haveValueForUpdate) {
            fail("Во второй колонке нет ни одного значения, которое надо поменять на value2 - нет смысла дальше тестить");
            return;
        }


        executor.execute(getQueryString("update", TEST_TABLE, "col1", value1, "col2", value2));


        try {

            rs = statement.executeQuery("SELECT \"col2\" FROM \"" + TEST_TABLE + "\" WHERE  \"col1\" = \'" + value1 + "\'");
            while (rs.next()) {
                result2.add(rs.getString(1));
            }
        } catch (SQLException e) {
            view.writeln("Не могу получить стейтмент или выполнить executeQuery");
            view.writeln(e.getLocalizedMessage());
        }


        // Если во второй колонке есть хоть одно значение, которое неравно value2, это ошибка
        boolean haveAllCorrectsValues = false;
        if(result2.size() != 1) {
            fail("Во второй колонке не все значения такие, как должны быть");
            return;
        }

        for (String valueInCol2 : result2) {
            if(valueInCol2.equals(value2))
                haveAllCorrectsValues = true;
        }

        assertTrue(haveAllCorrectsValues);

        disconnect(connection);
        executor.execute("close");
    }
}
