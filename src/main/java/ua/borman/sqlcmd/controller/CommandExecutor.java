package ua.borman.sqlcmd.controller;

import ua.borman.sqlcmd.controller.commands.*;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class CommandExecutor {

    private final DatabaseManager dbm;
    private Command [] commands;
    private final View view;

    public CommandExecutor(View view, DatabaseManager dbm) {
        this.view = view;
        this.dbm = dbm;
        commands = new Command[]{new Clear(dbm, view), new Close(dbm, view), new Connect(dbm, view), new Create(dbm, view), new CreateDB(dbm, view),
                new Delete(dbm, view), new Drop(dbm, view), new DropDB(dbm, view), new Exit(dbm, view), new Find(dbm, view), new Help(view),
                new Insert(dbm, view), new Tables(dbm, view), new Update(dbm, view), new DefaultCommand(view)};
    }

    public void execute(String str) {

        String[] queryArray = str.split("\\|");

        for (int i = 0; i < queryArray.length; i++)
            queryArray[i] = queryArray[i].trim();

        ArrayList<String> queryList = new ArrayList<>();
        Collections.addAll(queryList, queryArray);

        queryList.set(0, queryList.get(0).toLowerCase());

        if (queryList.size() != 0) {

            if(queryList.size() == 1 && queryList.get(0).equals(""))
                return;

            for (Command tempCommand : commands) {
                if(tempCommand.canProcess(queryList.get(0))) {
                    tempCommand.process(queryList);
                    break;
                }
            }
        }

    }

}
