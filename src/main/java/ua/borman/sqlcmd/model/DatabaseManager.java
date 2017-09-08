package ua.borman.sqlcmd.model;

import ua.borman.sqlcmd.controller.Table;
import ua.borman.sqlcmd.controller.Table.Row;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final String LOGGER_LEVEL_OFF = "?loggerLevel=OFF";
    private Connection connection = null;

    public void clear(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + tableName);
        }

    }

    public int close() throws SQLException {
        if (isConnected()) {
            connection.close();
            return 0;
        } else {
            return 1;
        }
    }

    public void create(List<String> columnList) throws SQLException {
        StringBuilder query;
        if (columnList.size() == 1) {
            query = new StringBuilder("CREATE TABLE ").append(columnList.get(0)).append(" ()");
        } else {
            query = new StringBuilder("CREATE TABLE ").append(columnList.get(0)).append(" ("); // первая запись - tableName, остальные - columnName
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

    public void createDB(String dbName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + dbName);
        }
    }

    public void delete(List<String> columnList) throws SQLException {
        StringBuilder query = new StringBuilder("DELETE FROM ").append(columnList.get(0)).append(" WHERE ");

        int i;
        for (i = 1; i < columnList.size() - 2; i++) {  // 0 - tableName, остальное - колонки и значения
            query.append(columnList.get(i++)).append("=").append(columnList.get(i)).append(" OR ");
        }
        query.append(columnList.get(i++)).append("=").append(columnList.get(i));
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query.toString());
        }
    }

    public void drop(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE " + tableName);
        }
    }

    public void dropDB(String dbName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE " + dbName);
        }
    }

    public Table find(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Table table = new Table(columnCount);

            for (int i = 1; i <= columnCount; i++) {
                table.addColumnName(rsmd.getColumnLabel(i));
            }

            while (rs.next()) {
                Row newRow = table.addNewRow();
                for (int i = 1; i <= columnCount; i++)
                    newRow.setElementValue(rsmd.getColumnLabel(i), rs.getObject(i));
            }

            rs.close();
            return table;
        }
    }

    public void insert(List<String> columnList) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + columnList.get(0) + " (");  // "INSERT INTO users (name, password) VALUES ('borman', '1234wedfxc')"
        List<String> columnName = new ArrayList<>();
        List<String> value = new ArrayList<>();

        for (int i = 1; i < columnList.size(); i++) {
            columnName.add(columnList.get(i++));
            value.add(columnList.get(i));
        }

        int i;
        for (i = 0; i < columnName.size() - 1; i++) {
            query.append(columnName.get(i)).append(", ");
        }
        query.append(columnName.get(i)).append(") VALUES (");

        i = 0;
        for (; i < value.size() - 1; i++) {
            query.append(value.get(i)).append(", ");
        }
        query.append(value.get(i)).append(")");

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query.toString());
        }

    }

    public List<String> tables() throws SQLException {  // TODO Сделать, чтобы можно было выбирать схему
        List<String> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "public", "%", null);
            while (rs.next()) {
                list.add(rs.getString("TABLE_NAME"));
            }
        }
        return list;
    }

    public void update(List<String> columnList) throws SQLException {
        // statement.executeUpdate("UPDATE users SET password = 123 WHERE id > 10");
        StringBuilder query = new StringBuilder("UPDATE ").append(columnList.get(0)).append(" SET ").append(columnList.get(3)).append(" = ").append(columnList.get(4)).append(" WHERE ").append(columnList.get(1)).append(" = ").append(columnList.get(2));  // "INSERT INTO users (name, password) VALUES ('borman', '1234wedfxc')"


        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query.toString());
        }
    }

    public void connect(List<String> queryList) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            System.out.println(">\tСоединение с базой данных уже установлено.\n" +
                    ">\tЧтобы установить новое соединение, сначала необходимо закрыть текущее командой close\n");
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(">\tОШИБКА! Не подключен SQL драйвер JDBC!\n");
            return;
        }
        String host = (queryList.size() == 4) ? "jdbc:postgresql://" + queryList.get(3) + "/" : "jdbc:postgresql://localhost:5432/";


        connection = DriverManager.getConnection(host + queryList.get(0) + LOGGER_LEVEL_OFF, queryList.get(1), queryList.get(2));
        if ((connection != null) && (!connection.isClosed()))
            if (queryList.get(0).equals("")) {
                System.out.println(">\tПодключение к SQL серверу установлено.\n");
            } else {
                System.out.println(">\tПодключение к базе данных установлено.\n");
            }
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Не удалось закрыть соединение с базой данных ", e);
            }
        }
    }

    public boolean isConnected() {
        try {
            if (connection != null && !connection.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    protected void finalize() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть соединение с базой данных");
            e.printStackTrace();
        }
    }

}