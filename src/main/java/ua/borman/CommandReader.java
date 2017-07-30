package ua.borman;
// +

import java.util.Scanner;

// Класс для чтения оманд из консоли
public class CommandReader {

    public String readConsole() {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextLine()){
            return scanner.nextLine();
        }
        return "";
    }


}
