package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * The HelpCommand is a public class that extends from the abstract class Command.
 * It finds and shows to the UI the required help file by the user
 */
public class HelpCommand extends Command {
    private String specifiedHelp = "";
    private static Logger logger = Logger.getLogger(HelpCommand.class.getName());

    /**
     * Constructor for HelpCommand.
     * @param specifiedHelp The type of help specified by the user
     */
    public HelpCommand(String specifiedHelp) {
        this.specifiedHelp = specifiedHelp;
    }

    public HelpCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the HelpCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        ui.showLine();
        logger.setLevel(Level.OFF);
        ArrayList<String> helpLines = new ArrayList<>();
        try {
            storage.loadHelp(helpLines, specifiedHelp);
        } catch (ProgramException e) {
            logger.log(Level.WARNING, "there is a problem loading the help file");
            ui.showMessage(e.getMessage());
        }
        logger.log(Level.FINE, "help is loaded successfully");
        ui.showHelp(helpLines);
        ui.showLine();
    }
}
