package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.util.ArrayList;

/**
 * The HelpCommand is a public class that extends from the abstract class Command.
 * It finds and shows to the UI the required help file by the user
 */
public class HelpCommand extends Command {
    private String specifiedHelp = "";

    /**
     * Constructor for HelpCommand.
     */

    public HelpCommand() {
    }

    /**
     * Constructor for HelpCommand.
     * @param specifiedHelp The type of help specified by the user
     */

    public HelpCommand(String specifiedHelp) {
        this.specifiedHelp = specifiedHelp;
    }

    public HelpCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }

    /**
     * Executes the HelpCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ArrayList<String> helpLines = new ArrayList<>();
        try {
            storage.loadHelp(helpLines, specifiedHelp);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showHelp(helpLines);
        ui.showLine();
    }
}
