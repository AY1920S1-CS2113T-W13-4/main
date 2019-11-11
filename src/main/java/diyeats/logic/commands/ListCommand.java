package diyeats.logic.commands;

import diyeats.logic.sort.SortMealByCalorie;
import diyeats.logic.sort.SortMealByCost;
import diyeats.logic.sort.SortMealByDefault;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author GaryStu
/**
 * ListCommand is a public class that inherits from abstract class Command.
 * It displays all the meal(s) in a relevant day in a list to the user
 */
public class ListCommand extends Command {
    private String sortBy = "default";

    /**
     * Constructor for ListCommand.
     */
    public ListCommand() {
    }

    /**
     * Constructor for ListCommand.
     * @param date The date of the meal(s) to List
     */
    public ListCommand(LocalDate date) {
        if (date != null) {
            currentDate = date;
        }
    }

    /**
     * Constructor for ListCommand.
     * @param date the date concerned.
     * @param sortBy the type of sorting acceptable.
     */
    public ListCommand(LocalDate date, String sortBy) {
        this(date);
        this.sortBy = sortBy;
    }
    
    /**
     * Constructor when ListCommand encounters error.
     * @param flag the flag to indicate whether the parser fails.
     * @param messageStr the error message concerned.
     */
    public ListCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;

        if (!this.isFail) {
            ui.showMessage(messageStr);
            ui.showMessage("Showing list of meals from " + currentDate.format(dateFormat) + " instead.");
        }
    }

    /**
     * Executes the ListCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        ui.showLine();
        ui.showCalorie(user);
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        if (!meals.checkDate(currentDate)) {
            ui.showMessage("There isn't any food on " + currentDate.format(dateFormat));
        }
        undo.undoSort(currentDate);
        switch (sortBy) {
            case "calorieAscending":
                currentMeals.sort(new SortMealByCalorie());
                break;
            case "calorieDescending":
                currentMeals.sort(new SortMealByCalorie().reversed());
                break;
            case "costAscending":
                currentMeals.sort(new SortMealByCost());
                break;
            case "costDescending":
                currentMeals.sort(new SortMealByCost().reversed());
                break;
            default:
                currentMeals.sort(new SortMealByDefault());
        }
        ui.showMealList(currentMeals);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
        ui.showExerciseOnDate(meals.getExerciseList(), currentDate);
        ui.showLine();
    }

    public void undo(MealList meals,  Storage storage, User user, Wallet wallet) {
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        currentMeals.sort(new SortMealByDefault());
    }
}
