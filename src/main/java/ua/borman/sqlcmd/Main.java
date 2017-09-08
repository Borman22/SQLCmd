package ua.borman.sqlcmd;
// TODO Сделать форматированный вывод в Find
// TODO Написать тесты к Find
// TODO Написать тесты к Table

import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.Console;
import ua.borman.sqlcmd.view.View;

public class Main {

    public static void main(String[] args)  {

        View view = new Console();
        view.writeln(">\tЧтобы подключиться к базе данных введите:");
        view.writeln(">\tconnect|database|username|password");
        view.writeln(">\tВыход - exit. Список всех команд - help\n");

        DatabaseManager dbm = new DatabaseManager(view);

        CommandExecutor commandExecutor = new CommandExecutor(view, dbm);
        while(true){
            String newCommand = view.read();
            commandExecutor.execute(newCommand);
        }
    }


}
