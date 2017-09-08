package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.util.List;

public class Exit implements Command{
    private final DatabaseManager dbm;
    private final View view;

    public Exit(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }

    @Override
    public void process(List<String> queryList) {
        view.writeln("Спасибо, что воспользовались нашей программой. Adios!");
        try {
            dbm.closeConnection();
        } catch (Exception a){
            view.writeln(a.getMessage());
            if(a.getCause() != null){
                view.writeln(a.getCause().getLocalizedMessage());
            }
        }
        System.exit(0);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

}
