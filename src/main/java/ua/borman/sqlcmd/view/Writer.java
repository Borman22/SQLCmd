package ua.borman.sqlcmd.view;

public interface Writer {
    void write(String message);

    void writeln(String message);
}
