package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateDB implements Command{

    private final DatabaseManager dbm;
    private final View view;

    public CreateDB(DatabaseManager dbm, View view) {
        this.dbm = dbm;
        this.view = view;
    }

    @Override
    public void process(List<String> queryList) {  // createDB | dbName | username | password

        queryList.remove(0); // удаляем команду createDB

        if(queryList.size() == 1){
            if(!dbm.isConnected()){
                view.writeln(">\tОперация не выполнена. Причина: для того, чтобы создать базу данных, необходимо подключиться к SQL серверу. \n" +
                        ">\tДля этого или введите запрос в формате createDB | dbName | username | password и программа сама подключится к SQL серверу\n" +
                        ">\tили подключитесь к существующей БД (connect | myDB | username | password) или подключитесь к SQL серверу (connect |   | username | password)");
                return;
            }
        } else if( queryList.size() == 3) {

            if (!dbm.isConnected()) {
                List<String> tempList = new ArrayList<>();
                tempList.add("connect");
                tempList.add("");
                tempList.add(queryList.get(1));
                tempList.add(queryList.get(2));

                new Connect(dbm, view).process(queryList);
            }
            if (!dbm.isConnected()) {
                view.writeln(">\tОперация не выполнена. Причина: не удается подключиться к базе данных.\n");
                return;
            }
        } else { // не 1 и не 3
            view.writeln(">\tОперация не выполнена. Причина: неправильный формат команды.\n");
            return;
        }
        queryList.set(0, "\"" + queryList.get(0) + "\"");
        try {
            dbm.createDB(queryList.get(0));
            view.writeln(">\tБаза данных " + queryList.get(0)+ " успешно создана\n");
        } catch (SQLException e) {
            view.writeln(">\tНе удалось создать базу данных.");
            view.writeln(">t" + e.getLocalizedMessage());
        }



    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("createdb");
    }

}
