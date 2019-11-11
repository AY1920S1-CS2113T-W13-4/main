package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;

//@@author

/**
 * AddCommand is a public class that inherits from abstract class Command.
 * An AddCommand object encapsulates the current meal that is to be added.
 */
public class AddCommand extends Command {
    private Meal meal;

    public AddCommand() {
    }

    /**
     * Constructor for AddCommand.
     * the meal specified as the instance field meal.
     * @param meal The meal to be added.
     */

    public AddCommand(Meal meal) {
        this.meal = meal;
    }

    public AddCommand(boolean isFail, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes add command.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        try {
            if (!meals.getMealTracker().containsKey(this.meal.getDate())) {
                undo.undoAdd(this.meal, new ArrayList<Meal>());
            } else {
                undo.undoAdd(this.meal, (ArrayList) meals.getMealTracker().get(this.meal.getDate()).clone());
            }
            meals.addMeals(this.meal);
            ArrayList<Meal> mealData = meals.getMealTracker().get(this.meal.getDate());
            ui.showAdded(this.meal, mealData, user, this.meal.getDate());
            storage.writeFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * This function is to recreate a meal object for undo and check if the meal is done.
     * @param flag the flag that indicates if meal is done
     */

    public void setIsDone(String flag) {
        if (flag.equals("1")) {
            this.meal.markAsDone();
        }
    }

    /**
     * This function is to facilitate undo for delete command.
     * A meal object will be initialised and inserted into the list
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    public void undo(MealList meals, Storage storage, User user, Wallet wallet) {
        try {
            meals.addMeals(this.meal);
            ArrayList<Meal> mealData = meals.getMealTracker().get(this.meal.getDate());
            storage.writeFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }

    public Meal getMeal() {
        return this.meal;
    }
}
