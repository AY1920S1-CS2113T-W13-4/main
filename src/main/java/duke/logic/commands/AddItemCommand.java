package duke.logic.commands;

import duke.model.Meal;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

import java.util.Scanner;

/**
 * AddItemCommand is a public class that inherits from abstract class Command.
 * An AddItemCommand object encapsulates the current meal that is to be added.
 */
public class AddItemCommand extends Command {
    private Meal meal;

    /**
     * Constructor for AddItemCommand.
     * @param meal The meal to be added.
     */
    public AddItemCommand(Meal meal) {
        this.meal = meal;
    }

    /**
     * Execute the AddItemCommand.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in, TransactionList transactions) {
        meals.addStoredItem(this.meal);
        ui.showAddedItem(this.meal);
        storage.updateDefaults(meals);
    }
}