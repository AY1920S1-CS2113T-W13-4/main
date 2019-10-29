package duke.logic.commands;

import java.util.ArrayList;

import duke.commons.exceptions.DukeException;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

/**
 * AddCommand is a public class that inherits from abstract class Command.
 * An AddCommand object encapsulates the current meal that is to be added.
 */
public class AddCommand extends Command {
    private Meal meal;
    private String cost;
    /**
     * Constructor for AddCommand.
     * the meal specified as the instance field meal.
     * @param meal The meal to be added.
     */

    public AddCommand(Meal meal, String cost) {
        this.meal = meal;
        this.cost = cost;
    }

    public AddCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes add command.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        try {
            meals.addMeals(this.meal);
            wallet.addTransactions(cost,this.meal.getDate());
            ArrayList<Meal> mealData = meals.getMealTracker().get(this.meal.getDate());
            ui.showAdded(this.meal, mealData, user, this.meal.getDate());
            storage.updateFile(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}
