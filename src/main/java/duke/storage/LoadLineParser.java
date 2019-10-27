package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.model.*;
import duke.model.user.Gender;
import duke.model.user.User;

import java.util.List;

public class LoadLineParser {

    //TODO: Fix SLAP issues parsing and other stuff

    /**
     * This function acts as a parser from the text file which is used to store data from the previous session.
     * @param lines the list of lines from the input file
     * @param meals the structure that encapsulates the meal data for this session
     */
    public static void parseMealList(MealList meals, List<String> lines) throws DukeException {
        if (lines.isEmpty()) {
            return;
        }
        for (String lineStr : lines) {
            if (lineStr == null) {
                continue;
            }
            String[] splitLine = lineStr.split("\\|", 4);
            String taskType = splitLine[0];
            String status = splitLine[1];
            String description = splitLine[2];
            String[] nutritionalValue = splitLine[3].split("\\|");
            Meal newMeal;
            if (taskType.equals("B")) {
                newMeal = new Breakfast(description, nutritionalValue);
                if (status.equals("1")) {
                    newMeal.markAsDone();
                }
                LoadMealUtil.load(meals, newMeal);
            } else if (taskType.equals("L")) {
                newMeal = new Lunch(description, nutritionalValue);
                if (status.equals("1")) {
                    newMeal.markAsDone();
                }
                LoadMealUtil.load(meals, newMeal);
            } else if (taskType.equals("D")) {
                newMeal = new Dinner(description, nutritionalValue);
                if (status.equals("1")) {
                    newMeal.markAsDone();
                }
                LoadMealUtil.load(meals, newMeal);
            } else if (taskType.equals("S")) {
                newMeal = new Item(description, nutritionalValue);
                meals.addStoredItem(newMeal);
            } else if (taskType.equals("G")) {
                meals.addGoal(new Goal(description, nutritionalValue[0], nutritionalValue), true);
            }
        }
    }

    public static User parseUser(String line) {
        String[] splitLine = line.split("\\|");
        String name = splitLine[0];
        int age = Integer.parseInt(splitLine[1]);
        int height = Integer.parseInt(splitLine[2]);
        int activityLevel = Integer.parseInt(splitLine[3]);
        boolean loseWeight = Boolean.parseBoolean(splitLine[4]);
        String sex = splitLine[5];
        if (sex.equals("M")) {
            return new User(name, age, height, Gender.MALE, activityLevel, loseWeight);
        } else {
            return new User(name, age, height, Gender.FEMALE, activityLevel, loseWeight);
        }
    }
}
