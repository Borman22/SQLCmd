package ua.borman.sqlcmd.controller.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;

import java.sql.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ua.borman.sqlcmd.Templates_For_Tests.*;
import static ua.borman.sqlcmd.Templates_For_Tests.PASSWORD_FALSE;
import static ua.borman.sqlcmd.Templates_For_Tests.USERNAME_FALSE;

public class Connect_Test {

    private boolean connectionIsReceived(String query) {
        boolean result;
        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(query,dbm);

        result = dbm.isConnected();
        executor.execute("close", dbm);
        return result;
    }

    @Test
    public void connectWithTrueParam() {
        assertTrue(connectionIsReceived(getQueryString("connect", "", USERNAME, PASSWORD)));
    }

    @Test
    public void connectWithFalsePassword() {
            assertFalse(connectionIsReceived(getQueryString("connect", "", USERNAME, PASSWORD_FALSE)));
    }

    @Test
    public void connectWithFalseUsername() {
            assertFalse(connectionIsReceived(getQueryString("connect", "", USERNAME_FALSE, PASSWORD)));
    }

    @Test
    public void connectWithFalseUsernameAndPassword() {
            assertFalse(connectionIsReceived(getQueryString("connect", "", USERNAME_FALSE, PASSWORD_FALSE)));
    }


}
