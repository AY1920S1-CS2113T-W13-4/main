package duke.storage;

import duke.tasks.Meal;
import duke.tasks.MealList;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadMealUtil {

    public static void load(MealList meals, Meal newMeal) {
        HashMap<String, ArrayList<Meal>> mealTracker = meals.getMealTracker();
        if (newMeal.getIsDone()) {
            newMeal.markAsDone();
        }
        String mealDate = newMeal.getDate();
        if (!mealTracker.containsKey(mealDate)) {
            mealTracker.put(mealDate, new ArrayList<Meal>());
            mealTracker.get(mealDate).add(newMeal);
        } else {
            mealTracker.get(mealDate).add(newMeal);
        }
    }
}