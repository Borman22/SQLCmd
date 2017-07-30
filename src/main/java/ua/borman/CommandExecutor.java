package ua.borman;
// +

import ua.borman.Commands.*;

import java.util.ArrayList;
import java.util.Collections;

public class CommandExecutor {

    public void execute(String str, DatabaseManager dbm) {
        String[] queryArry = str.split("\\|");

        ArrayList<String> queryList = new ArrayList<>();
        Collections.addAll(queryList, queryArry);

        queryList.set(0, queryList.get(0).trim().toLowerCase());

        if (queryList.size() != 0) {
            if(queryList.size() == 1 && queryList.get(0).equals("")) return;
            switch (queryList.get(0)) {
                case "connect":
                    Сonnect.connect(queryList, dbm);
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

                case "create":
                    Create.create(queryList, dbm);
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
                    System.out.println("\nОшибка! Несуществующая команда!");
                    System.out.println("Чтобы посмотреть доступные команды, напишите help");

            }
        }
    }
}
