package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;
import diyeats.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

/**
 * Command is the abstract base class for all the command objects.
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 */
public abstract class Command {
    protected DateTimeFormatter dateFormat = LOCAL_DATE_FORMATTER;
    protected LocalDate currentDate = LocalDate.now();
    protected Ui ui = new Ui();
    protected String responseStr;
    protected boolean isDone = true;
    protected boolean isFail = false;
    protected String errorStr;
    protected int stage = 0;


    /**
     * This class is an abstract class that will change according to the inheritor.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @throws ProgramException when there is an error
     */
    public abstract void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo)
            throws ProgramException;

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public boolean isExit() {
        return false;
    }

    // Called when command parsing fails
    public void failure() {
        // Support multi-line error messages with padding
        String[] errorStrList = this.errorStr.split("\n");
        for (String errorStr : errorStrList) {
            ui.showMessage(errorStr);
        }
    }

    public boolean isFail() {
        return this.isFail;
    }
}
