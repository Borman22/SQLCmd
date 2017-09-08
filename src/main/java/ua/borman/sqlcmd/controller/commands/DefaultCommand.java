package ua.borman.sqlcmd.controller.commands;

import ua.borman.sqlcmd.view.View;

import java.util.List;

public class DefaultCommand implements Command {

    private final View view;

    public DefaultCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(List<String> queryList) {
        view.writeln("\nОшибка! Несуществующая команда!");
        view.writeln("Чтобы посмотреть доступные команды, напишите help");
    }
}
