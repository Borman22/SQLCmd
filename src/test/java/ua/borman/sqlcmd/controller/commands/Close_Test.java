package ua.borman.sqlcmd.controller.commands;

import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class Close_Test {

    @Test
    public void closeTest(){

        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor();
        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD), dbm);
        assertTrue(dbm.isConnected());

        executor.execute("close", dbm);
        assertFalse(dbm.isConnected());
    }
}
