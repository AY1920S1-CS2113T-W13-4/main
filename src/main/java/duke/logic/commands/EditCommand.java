package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;

public class EditCommand extends Command {
    private Meal updatedMeal;

    /**
     * Constructor for EditCommand.
     * @param meal The meal object to be edited to
     */
    public EditCommand(Meal meal) {
        this.updatedMeal = meal;
    }

    public EditCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }
    /**
     * Executes the EditCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @throws DukeException if there is a parsing error for the date
     */

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        try {
            updatedMeal = meals.updateMeal(updatedMeal);
            String dateStr = updatedMeal.getDate();
            ui.showUpdated(this.updatedMeal, meals.getMealsList(this.updatedMeal.getDate()), user, dateStr);
            storage.updateFile(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
