package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

public class Exit {
    public static void exit(DatabaseManager dbm){
        View writer = new Console();
        writer.writeln("Спасибо, что воспользовались нашей программой. Adios!");
        try {
            dbm.closeConnection();
        } catch (Exception a){
            writer.writeln(a.getMessage());
            if(a.getCause() != null){
                writer.writeln(a.getCause().getLocalizedMessage());
            }
        }
        System.exit(0);
    }
}
