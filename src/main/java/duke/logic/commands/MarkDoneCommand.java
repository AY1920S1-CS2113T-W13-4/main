package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.ui.Ui;
import duke.storage.Storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import duke.model.user.User;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of meal to be marked as done.
 */
public class MarkDoneCommand extends Command {
    private int index;
    private final String helpText = "Please follow: done <index> /date <date> or "
            + "done <index> to mark done the indexed meal for current day.";

    /**
     * Constructor for MarkDoneCommand.
     * @param indexStr the index of meal on the date to be marked as done.
     * @param date the date which meals are to be marked as done.
     */
    public MarkDoneCommand(String indexStr, String date) throws DukeException {
        this(indexStr);
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
            this.currentDate = dateFormat.format(parsedDate);
        } catch (ParseException e) {
            ui.showMessage("Unable to parse input" + date + " as a date. " + helpText);
        }
    }

    /**
     * Constructor for MarkDoneCommand.
     * @param indexStr the index of meal on the today to be marked as done.
     * @throws DukeException when parseInt is unable to parse the index.
     */
    public MarkDoneCommand(String indexStr) throws DukeException {
        try {
            this.index = Integer.parseInt(indexStr.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Unable to parse input " + indexStr + " as integer index. " + helpText);
        }
    }

    /**
     * Executes the MarkDoneCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        if (index <= 0 || index > meals.getMealsList(currentDate).size()) {
            ui.showMessage("Index provided out of bounds for list of meals on " + currentDate);
        }
        Meal currentMeal = meals.markDone(currentDate, index);
        storage.updateFile(meals);
        ui.showDone(currentMeal, meals.getMealsList(currentDate));
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
