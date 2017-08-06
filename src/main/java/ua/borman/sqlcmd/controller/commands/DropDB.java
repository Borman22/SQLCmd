package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Writer;

import java.sql.SQLException;
import java.util.ArrayList;

public class DropDB {
    public static void dropDB(ArrayList<String> queryList, DatabaseManager dbm) { // createDB | dbName | username | password
        Writer writer = new ConsoleWriter();
        queryList.remove(0); // удаляем команду dropDB

        if(queryList.size() == 1){
            if(!dbm.isConnected()){
                writer.writeln(">\tОперация не выполнена. Причина: для того, чтобы удалить базу данных, необходимо подключиться к SQL серверу. \n" +
                        ">\tДля этого или введите запрос в формате dropDB | dbName | username | password и программа сама подключится к SQL серверу\n" +
                        ">\tили подключитесь к существующей БД (connect | myDB | username | password) или подключитесь к SQL серверу (connect |   | username | password)");
                return;
            }
        } else if( queryList.size() == 3) {
            if (!dbm.isConnected()) {
                ArrayList<String> tempList = new ArrayList<>();
                tempList.add("connect");
                tempList.add("");
                tempList.add(queryList.get(1));
                tempList.add(queryList.get(2));
                Connect.connect(tempList, dbm);
            }
            if (!dbm.isConnected()) {
                writer.writeln(">\tОперация не выполнена. Причина: не удается подключиться к базе данных.\n");
                return;
            }
        } else { // не 1 и не 3
            writer.writeln(">\tОперация не выполнена. Причина: неправильный формат команды.\n");
            return;
        }

        try {
            dbm.dropDB(queryList.get(0));
            writer.writeln(">\tБаза данных " + queryList.get(0)+ " успешно удалена\n");
        } catch (SQLException e) {
            writer.writeln(">\tНе удалось удалить базу данных.");
            writer.writeln(">t" + e.getLocalizedMessage());
        }



    }
}
