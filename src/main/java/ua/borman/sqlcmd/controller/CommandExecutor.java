package ua.borman.sqlcmd.controller;
// +

import ua.borman.sqlcmd.controller.commands.*;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.util.ArrayList;
import java.util.Collections;

public class CommandExecutor {

    public void execute(String str, DatabaseManager dbm) {
        String[] queryArray = str.split("\\|");

        for (int i = 0; i < queryArray.length; i++) {
            queryArray[i] = queryArray[i].trim();
        }
        ArrayList<String> queryList = new ArrayList<>();
        Collections.addAll(queryList, queryArray);

        queryList.set(0, queryList.get(0).toLowerCase());

        Writer writer = new ConsoleWriter();

        if (queryList.size() != 0) {
            if(queryList.size() == 1 && queryList.get(0).equals("")) return;
            switch (queryList.get(0)) {
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

                case "help":
                    Help.help(queryList);
                    break;

                case "exit":
                    Exit.exit(dbm);
                    break;

                case "close":
                    Close.close(dbm);
                    break;

                default:
                    writer.writeln("\nОшибка! Несуществующая команда!");
                    writer.writeln("Чтобы посмотреть доступные команды, напишите help");

            }
        }
    }
}
