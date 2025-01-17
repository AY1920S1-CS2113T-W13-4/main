package diyeats.logic.commands;

import diyeats.logic.suggestion.MealSuggestionAnalytics;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.meal.MealType;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author HashirZahir
/**
 * Class to handle suggestion command arguments from the user and pass them to the analytic module.
 */
public class SuggestMealCommand extends Command {

    private int maxMealsToSuggest;
    private MealType mealType;
    private AddCommand addCommand;
    private ArrayList<Meal> suggestedMealList;
    private LocalDate suggestionDate;

    /**
     * Constructor of suggestion command.
     * @param suggestionDate Date on which meal suggestion is required.
     * @param maxMealsToSuggest Maximum number of suggested meals to be shown to the user.
     */
    public SuggestMealCommand(LocalDate suggestionDate, int maxMealsToSuggest, MealType mealType) {
        this.suggestionDate = suggestionDate;
        this.maxMealsToSuggest = maxMealsToSuggest;
        this.mealType = mealType;
    }

    // Constructor called when parser fails to parse arguments
    public SuggestMealCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;
    }

    private int getCalorieLimit(User user, ArrayList<Meal> meals) {
        int totalConsume = 0;
        for (Meal meal : meals) {
            // add all meals regardless whether it is done or not.
            totalConsume += meal.getNutritionalValue().get("calorie");
        }

        return user.getDailyCalorie() - totalConsume;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        switch (stage) {
            case 0:
                execute_stage_0(meals, storage, user, wallet);
                stage++;
                break;
            case 1:
                execute_stage_1(meals, storage, user, wallet, undo);
                break;
            default:
                isDone = true;
        }
    }

    public void execute_stage_0(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        MealSuggestionAnalytics mealSuggestionAnalytics = new MealSuggestionAnalytics();
        int calorieLimit = getCalorieLimit(user, meals.getMealsList(this.suggestionDate));
        suggestedMealList = mealSuggestionAnalytics.getMealSuggestions(meals, suggestionDate, calorieLimit,
                                                                        maxMealsToSuggest, mealType);

        if (suggestedMealList.size() > 0) {
            ui.showSuggestedMealList(suggestedMealList, this.suggestionDate);
            // Allow followup user action after meals are suggested.
            isDone = false;
        } else {
            ui.showMessage("No meals could be suggested by DIYeats");
            isDone = true;
        }
        ui.showLine();
    }

    // second stage user input execution
    public void execute_stage_1(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        int mealSelectedIndex;
        try {
            mealSelectedIndex = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showLine();
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            ui.showLine();
            return;
        }

        if (mealSelectedIndex == 0) {
            ui.showLine();
            ui.showMessage("Declining suggestions.");
            isDone = true;
            ui.showLine();
            return;
        } else if (mealSelectedIndex < 1 || mealSelectedIndex > suggestedMealList.size()) {
            ui.showMessage("Index out of bounds. Please try again and enter index (inclusive)"
                    + " between 1 and " + suggestedMealList.size());
            return;
        }

        Meal chosenMeal = suggestedMealList.get(mealSelectedIndex - 1);
        addCommand = new AddCommand(chosenMeal);
        addCommand.execute(meals, storage, user, wallet, undo);
        isDone = true;
    }
}
