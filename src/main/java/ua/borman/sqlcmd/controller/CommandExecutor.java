package ua.borman.sqlcmd.controller;
// +

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
        commands = new Command[]{new Help(view), new Exit(dbm, view)};
    }

    public void execute(String str) {
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

                case "exit":
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