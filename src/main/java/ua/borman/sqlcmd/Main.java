package ua.borman.sqlcmd;
// TODO Переделать DataSet
// TODO Сделать форматированный вывод в Find
// TODO Написать тесты к Find
// TODO Написать тесты к DataSet

import ua.borman.sqlcmd.controller.CommandExecutor;
import ua.borman.sqlcmd.model.DatabaseManager;
import ua.borman.sqlcmd.view.ConsoleReader;
import ua.borman.sqlcmd.view.ConsoleWriter;
import ua.borman.sqlcmd.view.Reader;
import ua.borman.sqlcmd.view.Writer;

import java.io.*;

public class Main {

    public static void main(String[] args)  {

        Writer writer = new ConsoleWriter();
        writer.writeln(">\tЧтобы подключиться к базе данных введите:");
        writer.writeln(">\tconnect|database|username|password");
        writer.writeln(">\tВыход - exit. Список всех команд - help\n");

        DatabaseManager dbm = new DatabaseManager();

        Reader reader = new ConsoleReader();
        CommandExecutor commandExecutor = new CommandExecutor();
        while(true){
            String newCommand = reader.read();
            commandExecutor.execute(newCommand, dbm);
        }
    }


}
