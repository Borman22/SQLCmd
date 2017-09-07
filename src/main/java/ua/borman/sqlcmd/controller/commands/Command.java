package ua.borman.sqlcmd.controller.commands;

import java.util.List;

public interface Command {

    boolean canProcess(String command);

    void process(List<String> queryList);

}
