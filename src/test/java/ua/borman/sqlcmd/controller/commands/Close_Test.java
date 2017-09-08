package ua.borman.sqlcmd.controller.commands;

import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class Close_Test {

    @Test
    public void closeTest(){

        DatabaseManager dbm = new DatabaseManager();
        CommandExecutor executor = getCommandExecutor(new Console(), dbm);
        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD));
        assertTrue(dbm.isConnected());

        executor.execute("close");
        assertFalse(dbm.isConnected());
    }
}
