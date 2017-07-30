package ua.borman.Commands;
// +

import ua.borman.DatabaseManager;

import java.sql.SQLException;

public class Close {
    public static void close(DatabaseManager dbm){
        if(!dbm.isConnected()) {
            System.out.println(">\tКоманда не выполнена, посколькy программа не была подключена ни к одной БД");
            return;
        }

        try {
            dbm.close();
            System.out.println(">\tУспешно отключились от базы данныых ");
        } catch (SQLException e) {
            System.out.println(">\tНе удалось отключиться от базы данных ");
            e.printStackTrace();
        }
    }
}
