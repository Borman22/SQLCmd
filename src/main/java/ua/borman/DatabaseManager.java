package ua.borman;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private String host = "jdbc:postgresql://localhost:5432/";
    private String database;        // = "myPostgresDB"
    private String user;            // = "postgres"
    private String password;        // = "root"
    private String loggerLevelOff = "?loggerLevel=OFF";

    private Connection connection = null;

    public void connect(String database, String user, String password) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            System.out.println(">\tСоединение с базой данных " + this.database + " уже установлено.\n" +
                    ">\tЧтобы установить новое соединение, сначала необходимо закрыть текущее\n");
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(">\tОШИБКА! Не подключен SQL драйвер JDBC!\n");
            return;
        }

        this.database = database;
        this.user = user;
        this.password = password;

        connection = DriverManager.getConnection(host + database + loggerLevelOff, user, password);
        if ((connection != null) && (!connection.isClosed()))
            System.out.println(">\tПодключение к базе данных установлено.\n");

    }

    public ArrayList<String> tables() throws SQLException {  // TODO Сделать, чтобы можно было выбирать схему
        ArrayList<String> list = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "public", "%", null);
            while(rs.next()){
                list.add(rs.getString("TABLE_NAME"));
            }
        }
        return list;
    }

    public void clear(String tableName) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + tableName);
        }

    }


    public void drop(String tableName) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE " + tableName);
        }
    }

    public void create(ArrayList<String> columnList) throws SQLException {
        StringBuilder query;
        if(columnList.size() == 1) {
            query = new StringBuilder("CREATE TABLE " + columnList.get(0) + " ()");
        } else {
            query = new StringBuilder("CREATE TABLE " + columnList.get(0) + " ("); // первая запись - tableName, остальные - columnName
            int i;
            for (i = 1; i < columnList.size() - 1; i++) {
                query.append(columnList.get(i)).append(" text, ");
            }
            query.append(columnList.get(i)).append(" text)");
        }

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query.toString());
            }

    }

    public void find(String tableName){
        System.out.println("Выполнился метод find(tableName)");
//        ResultSet rs = statement.executeQuery("SELECT * FROM users");
//
//        rs = statement.executeQuery("select * from information_schema.tables");
//        while (rs.next()){
//            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "   \t" + rs.getString("password"));
//        }
//        rs.close();
    }

    public void insert(ArrayList<String> columnList) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + columnList.get(0) + " (");  // "INSERT INTO users (name, password) VALUES ('borman', '1234wedfxc')"
        ArrayList<String> columName = new ArrayList<>();
        ArrayList<String> value = new ArrayList<>();

        for (int i = 1; i < columnList.size(); i++) {
            columName.add(columnList.get(i++));
            value.add(columnList.get(i));
        }

        int i;
        for (i = 0; i < columName.size() - 1; i++) {
            query.append(columName.get(i)).append(", ");
        }
        query.append(columName.get(i)).append(") VALUES (");

        i = 0;
        for(i = 0; i < value.size() - 1; i++){
            query.append(value.get(i)).append(", ");
        }
        query.append(value.get(i)).append(")");

//        System.out.println("query = " + query.toString());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query.toString());
        }

    }

    public void update(ArrayList<String> columnList) throws SQLException {
       // statement.executeUpdate("UPDATE users SET password = 123 WHERE id > 10");
        StringBuilder query = new StringBuilder("UPDATE ").append(columnList.get(0)).append(" SET ").append(columnList.get(3)).append(" = ").append(columnList.get(4)).append(" WHERE ").append(columnList.get(1)).append(" = ").append(columnList.get(2));  // "INSERT INTO users (name, password) VALUES ('borman', '1234wedfxc')"

        System.out.println("query = " + query.toString());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query.toString());
        }
    }

    public void delete(ArrayList<String> columnList) throws SQLException {
        StringBuilder query = new StringBuilder("DELETE FROM " + columnList.get(0) + " WHERE ");

        int i;
        for (i = 1; i < columnList.size() - 2; i++) {  // 0 - tableName, остальное - колонки и значения
                query.append(columnList.get(i++)).append("=").append(columnList.get(i)).append(" OR ");
        }
        query.append(columnList.get(i++)).append("=").append(columnList.get(i));
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query.toString());
        }
    }

    public void closeConnection() {
        database = null;
        user = null;
        password = null;
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Не удалось закрыть соединение с базой данных " + this.database);
                e.printStackTrace();
            }
        }
    }

    public void close() throws SQLException {
        if(isConnected()){
            connection.close();
        }
    }

    public boolean isConnected(){
        try {
            if(connection != null && !connection.isClosed()){
                return true;
            }
        } catch (SQLException e) {
            // return false;
        }
        return false;
    }





    // методы для того, чтобы все методы пораскладывать по соответствующим классам
    public Statement getStatement() throws SQLException {
        if(isConnected()){
            return connection.createStatement();
        }
        return null;
    }

    public DatabaseMetaData getMetadata() throws SQLException {
        if(isConnected()){
            return connection.getMetaData();
        }
        return null;
    }

    protected void finalize() {
        System.out.println("Выполнился метод finalize()");  // TODO отладочная строка
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть соединение с базой данных " + this.database);
            e.printStackTrace();
        }
    }

}