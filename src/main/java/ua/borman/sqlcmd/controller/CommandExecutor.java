package ua.borman.sqlcmd.controller;
// +

import ua.borman.sqlcmd.controller.commands.*;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandExecutor {

    private Command [] commands;

    private final View view;

    public CommandExecutor(View view) {
        this.view = view;
        commands = new Command[]{new Help(view)};
    }

    public void execute(String str, DatabaseManager dbm) {
        String[] queryArray = str.split("\\|");

        for (int i = 0; i < queryArray.length; i++) {
            queryArray[i] = queryArray[i].trim();
        }
        ArrayList<String> queryList = new ArrayList<>();
        Collections.addAll(queryList, queryArray);

        queryList.set(0, queryList.get(0).toLowerCase());

        if (queryList.size() != 0) {
            if(queryList.size() == 1 && queryList.get(0).equals("")) return;

            for (Command tempCommand : commands) {
                if(tempCommand.canProcess(queryList.get(0))) {
                    tempCommand.process(queryList);
                    break;
                }
            }

            switch (queryList.get(0)) {
                case "help":
                    view.writeln("Уже еализована в паттерне Команда");
                    break;

                case "connect":
                    Connect.connect(queryList, dbm);
                    break;

                case "tables":
                    Tables.getTables(queryList, dbm);
                    break;

                case "clear":
                    Clear.clear(queryList, dbm);
                    break;

                case "drop":
                    Drop.drop(queryList, dbm);
                    break;

                case "dropdb":
                    DropDB.dropDB(queryList, dbm);
                    break;

                case "create":
                    Create.create(queryList, dbm);
                    break;

                case "createdb":
                    CreateDB.createDB(queryList, dbm);
                    break;

                case "find":
                    Find.find(queryList, dbm);
                    break;

                case "insert":
                    Insert.insert(queryList, dbm);
                    break;

                case "update":
                    Update.update(queryList, dbm);
                    break;

                case "delete":
                    Delete.delete(queryList, dbm);
                    break;

                case "exit":
                    Exit.exit(dbm);
                    break;

                case "close":
                    Close.close(dbm);
                    break;

                default:
                    view.writeln("\nОшибка! Несуществующая команда!");
                    view.writeln("Чтобы посмотреть доступные команды, напишите help");

            }
        }
    }
}
