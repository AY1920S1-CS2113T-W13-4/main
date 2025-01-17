package diyeats.model.user;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;

import java.time.LocalDate;
import java.util.HashMap;

//@@author

/**
 * This is a class that will store user information to be used for processing.
 */
public class User {
    public transient Goal goal = null;

    private HashMap<LocalDate, Double> weight = new HashMap();
    private  LocalDate lastDate = null;
    private int height = -1;
    private int age = -1;
    private Gender gender = null;
    private boolean isSetup;
    private String name = null;
    private int activityLevel = 5;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private double originalWeight = 0;


    /**
     * This is a contructor to create an empty user profile.
     */
    public User() {
        this.isSetup = false;
    }

    /**
     * This is a contructor to create an user profile with all the info.
     * Used during loading.
     * @param name name of user
     * @param age age of user
     * @param height height of user
     * @param gender biological gender of user
     * @param activityLevel how active the user is
     * @param originalWeight their original weight
     */
    public User(String name, int age, int height, Gender gender, int activityLevel,
                double originalWeight, LocalDate lastDate) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.originalWeight = originalWeight;
        this.lastDate = lastDate;
    }

    /**
     * Checks whether user is completely setup.
     * @return true if user is completely setup, false otherwise
     */
    public boolean isValid() {
        if (name != null && age != -1 && getAllWeight().size() != 0
                && height != -1 && gender != null && activityLevel != 5) {
            isSetup = true;
            return true;
        } else {
            isSetup = false;
            return false;
        }
    }

    /**
     * This is a function to update weight at time of input.
     * @param weight Weight at time of input
     */
    public void setWeight(double weight) {
        LocalDate currentDate = LocalDate.now();
        this.weight.put(currentDate, weight);
        if (lastDate == null || lastDate.isBefore(currentDate)) {
            this.lastDate = currentDate;
        }
    }

    /**
     * This is a function to update weight at input date.
     * @param weight Weight at time of input
     * @param inputDate LocalDate of the date in DD/MM/YYYY format
     */
    public void setWeight(double weight, LocalDate inputDate) {
        this.weight.put(inputDate, weight);
        if (lastDate == null || lastDate.isBefore(inputDate)) {
            this.lastDate = inputDate;
        }
    }

    /**
     * Setter for a goal object inside user.
     * @param goal the goal object to be added
     * @throws ProgramException if the goal to be added is not valid
     */
    public void setGoal(Goal goal) throws ProgramException {
        if (goal.getActivityLevelTarget() < activityLevel && activityLevel != 5) {
            throw new ProgramException("Set goal failed, cannot set target activity level to\n"
                    + "     be lower than current activity level.");
        } else if (activityLevel == 4) {
            throw new ProgramException("It appears that you are already pursuing a very active lifestyle. \n"
                    + "     The application cannot suggest something to add on to your routine.");
        } else if (!checkGoalFeasibility(goal)) {
            throw new ProgramException("Set goal failed. Average calorie loss in a day\n"
                    + "     must not exceed 40% of your current calorie expenditure!");
        } else {
            this.goal = goal;
            if (isSetup) {
                this.goal.setOriginalWeight(originalWeight);
                calculateTargetCalories();
            }
        }
    }

    /**
     * Sets goal as null.
     */
    public void clearGoal() {
        this.goal = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void updateStats(MealList meals) {
        this.goal.updateStats(meals);
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public double getWeight() {
        return this.weight.get(getLastDate());
    }

    public double getOriginalWeight() {
        return this.originalWeight;
    }

    /**
     * getter for weight target in goal.
     * @return the target weight
     */
    public double getWeightTarget() {
        return goal.getWeightTarget();
    }

    /**
     * Getter for days left to enddate of goal.
     * @return number of days left to goal
     */
    public int getDaysLeftToGoal() {
        return goal.daysLeftToGoal();
    }

    /**
     * This is a function to obtain all the weight at different date.
     */
    public HashMap<LocalDate, Double> getAllWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getActivityLevel() {
        return this.activityLevel;
    }

    /**
     * Calculates the static calorie expenditure of user per day.
     * @return The number of calories required per day
     */
    public int getDailyCalorie() {
        double calorie;
        if (this.gender == Gender.MALE) {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() + 5;
        } else {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() - 161;
        }
        if (goal == null || goal.getActivityLevelTarget() == 5) {
            return (int) (this.factor[this.activityLevel] * calorie);
        } else {
            return (int) (this.factor[goal.getActivityLevelTarget()] * calorie);
        }
    }

    /**
     * Calories required per day accounting for diet plan.
     * @return the amount of calories user must take in per day to reach diet goal
     */
    public int getCalorieBalance() {
        return getDailyCalorie() + getAvgCalorieChangeToReachTarget();
    }

    /**
     * Factor difference in energy expenditure between current and target activity level.
     * @return a double value corresponding to factor difference in energy expenditure
     */
    public double getActivityLevelDifference() {
        return this.factor[goal.getActivityLevelTarget()] - this.factor[this.activityLevel];
    }

    public Goal getGoal() {
        return goal;
    }

    public Gender getGender() {
        return this.gender;
    }

    /**
     * This is a function to check if it's an empty profile.
     */
    public boolean getIsSetup() {
        return this.isSetup;
    }

    /**
     * Check whether goal to be set is feasible.
     * @param goal the goal to be checked against current user data
     * @return true if goal is feasible, false otherwise
     */
    private boolean checkGoalFeasibility(Goal goal) {
        double calorieLossPerDayToReachWeight = 7700 * (getWeight() - goal.getWeightTarget()) / goal.durationOfGoal();
        double maximumCalorieLossPerDay = 0.4 * getDailyCalorie();
        return calorieLossPerDayToReachWeight < maximumCalorieLossPerDay;
    }

    /**
     * Calculate total calorie loss to achieve target weight.
     * @return value of total calories that must be lost
     */
    private int getCalorieChangeToReachTarget() {
        return (int) (7700 * (getWeight() - getWeightTarget()));
    }

    /**
     * Calculate total calorie loss to achieve target weight per day.
     * @return value of calories that must be lost per day
     */
    private int getAvgCalorieChangeToReachTarget() {
        if (getDaysLeftToGoal() == 0) {
            return 0;
        } else {
            return getCalorieChangeToReachTarget() / getDaysLeftToGoal();
        }
    }

    /**
     * Calculate total calorie intake to reach target. Set value in goal.
     */
    private void calculateTargetCalories() {
        int target = getDailyCalorie() * this.goal.durationOfGoal()
                - getCalorieChangeToReachTarget();
        this.goal.setCalorieTarget(target);
    }

    /**
     * getter for last date weight was updated.
     * @return the last date
     */
    private LocalDate getLastDate() {
        if (this.lastDate == null) {
            lastDate = LocalDate.now();
            return lastDate;
        } else {
            return this.lastDate;
        }
    }

    /**
     * Getter for target activity level.
     * @return target activity level
     */
    public int getTargetActivityLevel() {
        if (goal != null) {
            return goal.getActivityLevelTarget();
        } else {
            return 5;
        }
    }

    @Override
    public String toString() {
        return this.name + "|" + this.height + "|" + this.age + "|" + this.gender  + "|" + this.isSetup
                + "|" + this.activityLevel + "|" + this.originalWeight + "|" + this.lastDate;
    }
}
