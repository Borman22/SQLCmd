package ua.borman.sqlcmd.view;

import java.util.Scanner;

public class ConsoleReader implements Reader {

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextLine()){
            return scanner.nextLine();
        }
        return "";
    }


}
