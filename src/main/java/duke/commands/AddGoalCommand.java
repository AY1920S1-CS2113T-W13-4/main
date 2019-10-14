package duke.commands;

import duke.exceptions.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.tasks.Goal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class AddGoalCommand extends Command {
    private Goal goal;

    /**
     * This is a constructor for AddGoalCommand which create a new AddCommand object with
     * the goal specified as the instance field goal.
     * @param goal The goal to be added.
     */
    public AddGoalCommand(Goal goal) {
        this.goal = goal;
    }

    /**
     * The object will execute the "add" command, updating the current tasks, ui, and storage in the process.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in) throws DukeException {
        try {
            meals.addGoal(this.goal, false);
            ui.showAddedGoal(goal);
            storage.updateGoal(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            ui.showLine();
            String response = ui.readCommand(in);
            if (response.trim().equals("y")  || response.trim().equals("Y")) {
                meals.addGoal(this.goal, true);
                ui.showLine();
                ui.showAddedGoal(goal);
                storage.updateGoal(meals);
            } else if (response.trim().equals("n")  || response.trim().equals("N")){
                ui.showLine();
                throw new DukeException("The set goal command has been canceled");
            } else {
                meals.addGoal(this.goal, true);
                ui.showLine();
                ui.showAddedGoal(goal);
                storage.updateGoal(meals);
            }
        }
    }
}
