package ua.borman.Commands;

import ua.borman.DatabaseManager;

public class Exit {
    public static void exit(DatabaseManager dbm){
        System.out.println("Спасибо, что воспользовались нашей программой. Adios!");
        dbm.closeConnection();
        System.exit(0);
    }
}
