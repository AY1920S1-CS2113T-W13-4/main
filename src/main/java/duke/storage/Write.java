package duke.storage;

import duke.exceptions.DukeException;
import duke.tasks.Goal;
import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.user.Gender;
import duke.user.Tuple;
import duke.user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Write implements StorageInterface {
    private BufferedWriter bufferedWriter = null;

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void writeFile(MealList mealData) {
        HashMap<String, ArrayList<Meal>> meals = mealData.getMealTracker();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : meals.keySet()) { //write process for stored food entries
                ArrayList<Meal> mealsInDay = meals.get(i);
                for (int j = 0; j < meals.get(i).size(); j++) {
                    Meal currentMeal = mealsInDay.get(j);
                    String status = "0";
                    if (currentMeal.getIsDone()) {
                        status = "1";
                    }
                    String toWrite = currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription()
                            + "|date|" + currentMeal.getDate();
                    HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                    if (nutritionData.size() != 0) {
                        toWrite += "|";
                        for (String k : nutritionData.keySet()) {
                            toWrite += k + "|" + nutritionData.get(k) + "|";
                        }
                        toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                    }
                    bufferedWriter.write(toWrite);
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void writeDefaults(MealList mealData) {
        HashMap<String, HashMap<String, Integer>> storedItems = mealData.getStoredList();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(defaultFile));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : storedItems.keySet()) { //write process for stored default food values
                String toWrite = "";
                toWrite += "S|0|" + i;
                HashMap<String, Integer> nutritionData = storedItems.get(i);
                if (nutritionData.size() != 0) {
                    toWrite += "|";
                    for (String k : nutritionData.keySet()) {
                        toWrite += k + "|" + nutritionData.get(k) + "|";
                    }
                    toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                }
                bufferedWriter.write(toWrite);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void writeGoal(MealList mealData) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(goalFile));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            Goal goal = mealData.getGoal();
            String toWrite = "G|0|" + goal.getEndDate() + "|" + goal.getStartDate();
            HashMap<String, Integer> nutritionData = goal.getNutritionalValue();
            if (nutritionData.size() != 0) {
                toWrite += "|";
                for (String k : nutritionData.keySet()) {
                    toWrite += k + "|" + nutritionData.get(k) + "|";
                }
                toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
            }
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }


    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     * @Author Foo Chi Hen
     */

    public void writeUser(User user) throws DukeException {
        String toWrite = user.getName() + "|" + user.getAge() + "|"
                + user.getHeight() + "|" + user.getActivityLevel() + "|" + user.getLoseWeight() + "|";
        if (user.getSex() == Gender.MALE) {
            toWrite += "M";
        } else {
            toWrite += "F";
        }
        HashMap<String, Integer> allWeight = user.getAllWeight();
        Iterator iterator = allWeight.keySet().iterator();
        while (iterator.hasNext()) {
            toWrite += "\n";
            String date = (String) iterator.next();
            int weight = allWeight.get(date);
            toWrite += date + "|" + weight;
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(userFile));
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
