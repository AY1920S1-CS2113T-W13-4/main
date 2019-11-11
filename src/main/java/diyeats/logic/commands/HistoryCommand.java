package diyeats.logic.commands;

import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;

//@@author HashirZahir
/**
 * HistoryCommand is a public class that inherits form abstract class Command.
 * Tracks the history of all commands executed in the current session
 */
public class HistoryCommand extends Command {
    private static ArrayList<String> historyCommandsList = new ArrayList<String>();

    /**
     * Constructor for HistoryCommand.
     */
    public HistoryCommand(boolean isClear) {
        if (isClear) {
            clearHistory();
        }
    }

    /**
     * Add command to history of commands.
     * @param commandStr the command to be added to the list of executed commands thus far
     */

    public void addCommand(String commandStr) {
        if (!commandStr.equals("history")) {
            historyCommandsList.add(commandStr);
        }
    }

    /**
     * Clears the history of the commands executed thus far.
     */

    public void clearHistory() {
        historyCommandsList.clear();
    }


    /**
     * Executes the HistoryCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        ui.showHistory(historyCommandsList);
    }
}
