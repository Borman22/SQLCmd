package ua.borman;
// +

public class Main {

    public static void main(String[] args)  {
        System.out.println(">\tЧтобы подключиться к базе данных введите:");
        System.out.println(">\tconnect|database|username|password");
        System.out.println(">\tВыход - exit. Список всех команд - help\n");

        DatabaseManager dbm = new DatabaseManager();

        CommandReader commandReader = new CommandReader();
        CommandExecutor commandExecutor = new CommandExecutor();
        while(true){
            String newCommand = commandReader.readConsole();
            commandExecutor.execute(newCommand, dbm);
        }
    }


}
