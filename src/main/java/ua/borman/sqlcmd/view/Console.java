package ua.borman.sqlcmd.view;

import java.util.Scanner;

public class Console implements View {

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextLine()){
            return scanner.nextLine();
        }
        return "";
    }

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeln(String message) {
        System.out.println(message);
    }
}
