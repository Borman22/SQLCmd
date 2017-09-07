package ua.borman.sqlcmd.view;

public interface View {
    String read();

    void write(String message);

    void writeln(String message);
}
