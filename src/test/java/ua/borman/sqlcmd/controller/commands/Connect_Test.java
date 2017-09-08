package ua.borman.sqlcmd.controller.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ua.borman.sqlcmd.Templates_For_Tests.*;
import static ua.borman.sqlcmd.Templates_For_Tests.PASSWORD_FALSE;
import static ua.borman.sqlcmd.Templates_For_Tests.USERNAME_FALSE;

public class Connect_Test {

    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @Before
    public void init(){
        view = new Console();
        dbm = new DatabaseManager(view);
        executor = new CommandExecutor(view, dbm);
    }

    private boolean connectionIsReceived(String query) {
        boolean result;
        executor.execute(query);

        result = dbm.isConnected();
        if(result == false)
            return result;
        executor.execute("close");
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
