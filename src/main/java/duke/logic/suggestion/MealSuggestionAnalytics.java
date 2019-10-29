package duke.logic.suggestion;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.meal.SuggestMeal;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * This class handles all the data analytics aspect of the meal suggestion feature.
 * It calculates the meal preference score based on the meal parameters to be considered
 * and returns the highest meal preference score meal to the user.
 */
public class MealSuggestionAnalytics {

    private static ArrayList<SuggestMeal> defaultSuggestionMealList = new ArrayList<>();

    public MealSuggestionAnalytics() {
    }

    // get meal suggestion given the calorie limit and the max number of meals to suggest.
    public ArrayList<Meal> getMealSuggestions(MealList meals, Calendar suggestionDate,
                                              int calorieLimit, int maxMealsToSuggest) {

        setDefaultSuggestionMealList(meals.getStoredList(), suggestionDate);

        ArrayList<SuggestMeal> suggestionMealList = getMealListWithinCalories(calorieLimit);
        filterDisplayList(suggestionMealList, maxMealsToSuggest);
        return new ArrayList<Meal>(suggestionMealList);
    }

    // set the default meal list from which meals are suggested.
    private void setDefaultSuggestionMealList(HashMap<String, HashMap<String, Integer>>
                                                      defaultMealSuggestionList, Calendar suggestionDate) {
        defaultSuggestionMealList.clear();
        for (String mealNameStr : defaultMealSuggestionList.keySet()) {
            HashMap<String, Integer> mealNutrients  = defaultMealSuggestionList.get(mealNameStr);
            defaultSuggestionMealList.add(new SuggestMeal(mealNameStr, mealNutrients, suggestionDate));
        }
    }

    /**
     * Get all the meals that satisfy calorie requirement.
     * @param calorieLimit Calorie limit that user has for the day.
     * @return List of meals that meet the calorie limit.
     */
    private ArrayList<SuggestMeal> getMealListWithinCalories(int calorieLimit) {
        ArrayList<SuggestMeal> tempSuggestionMealList = new ArrayList(defaultSuggestionMealList);
        Collections.sort(tempSuggestionMealList);
        SuggestMeal calorieLimitMeal = new SuggestMeal();
        calorieLimitMeal.addNutritionalValue("calorie", calorieLimit);

        int startIdx, endIdx, compareIdx;
        compareIdx = Collections.binarySearch(tempSuggestionMealList, calorieLimitMeal);

        // a meal with the exact calorie limit exists
        if (compareIdx >= 0) {
            startIdx = 0;
            endIdx = compareIdx + 1;
        } else {
            // meal with same calories as calorie limit not found.
            // negative index returned by subList.
            startIdx = 0;
            endIdx = -1*(compareIdx + 1);
        }
        return new ArrayList<SuggestMeal>(tempSuggestionMealList.subList(startIdx, endIdx));
    }

    /**
     * Filter the list of suggested meals based on user requirement.
     * @param mealList Input meal list that is modified by reference.
     * @param maxMealsToSuggest Maximum number of meals to be suggested as set by user.
     */
    private void filterDisplayList(ArrayList<SuggestMeal> mealList, int maxMealsToSuggest) {
        int endIdx = mealList.size();
        int startIdx = endIdx - maxMealsToSuggest;

        if (startIdx < 0) {
            startIdx = 0;
        }

        mealList = new ArrayList<SuggestMeal>(mealList.subList(startIdx, endIdx));
    }
}
