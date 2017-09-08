package ua.borman.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ua.borman.sqlcmd.Templates_For_Tests.*;

public class Close_Test {

    private static DatabaseManager dbm;
    private static View view;
    private static CommandExecutor executor;

    @Before
    public void init(){
        dbm = new DatabaseManager();
        view = new Console();
        executor = new CommandExecutor(new Console(), dbm);
    }

    @Test
    public void closeTest(){

        executor.execute(getQueryString("connect", "", USERNAME, PASSWORD));
        assertTrue(dbm.isConnected());

        if(dbm.isConnected()) {
            executor.execute("close");
            assertFalse(dbm.isConnected());
        }
    }
}
