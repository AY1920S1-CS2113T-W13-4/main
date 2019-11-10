package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Payment;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of meal to be marked as done.
 */
public class MarkDoneCommand extends Command {
    private int index;
    private static Logger logger = Logger.getLogger(MarkDoneCommand.class.getName());
    /**
     * Constructor for MarkDoneCommand.
     * @param indexStr the index of meal on the date to be marked as done.
     * @param dateStr the date which meals are to be marked as done.
     */
    public MarkDoneCommand(String indexStr, String dateStr) {
        this(indexStr);
        if (!dateStr.isBlank()) {
            try {
                currentDate = LocalDate.parse(dateStr, dateFormat);
                logger.log(Level.FINE, "currentDate is parsable");
            } catch (DateTimeParseException e) {
                logger.log(Level.WARNING, "the date " + currentDate + " is not parsable");
                ui.showMessage("Unable to parse input" + dateStr + " as a date. ");
            }
        }
    }

    /**
     * Constructor for MarkDoneCommand.
     * @param indexStr the index of meal on the today to be marked as done.
     * @throws NumberFormatException when parseInt is unable to parse the index.
     */
    public MarkDoneCommand(String indexStr) {
        try {
            this.index = Integer.parseInt(indexStr.trim());
            logger.log(Level.FINE, "index is a valid int");
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Unable to parse input" + indexStr);
            ui.showMessage("Unable to parse input " + indexStr + " as integer index. ");
        }
    }

    public MarkDoneCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Constructor for MarkDoneCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        if (index <= 0 || index > meals.getMealsList(currentDate).size()) {
            logger.log(Level.WARNING, "the index " + index + " is out of bound");
            ui.showMessage("Index provided out of bounds for list of meals on " + currentDate);
        } else {
            Meal currentMeal = meals.getMeal(currentDate, index);
            String foodCostStr = currentMeal.getCostStr();
            Payment payment = new Payment(foodCostStr, currentMeal.getDate());

            if (currentMeal.getIsDone()) {
                logger.log(Level.INFO, "the meal has already been marked done");
                ui.showAlreadyMarkedDone(currentMeal);
            } else if (wallet.addPaymentTransaction(payment)) {
                logger.log(Level.FINE, "Adding payment transaction");
                Meal markedDoneMeal = meals.markDone(currentDate, index);
                try {
                    storage.updateFile(meals);
                    storage.updateTransaction(wallet);
                } catch (ProgramException e) {
                    logger.log(Level.WARNING, "error in storing transactions");
                    ui.showMessage(e.getMessage());
                }
                ui.showDone(markedDoneMeal);
                ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
                ui.showCaloriesLeft(currentMeals, user, currentDate);
                ui.showPayment(payment);
                ui.showAccountBalance(wallet);
            } else {
                logger.log(Level.INFO, "There is insufficient balance, no amount deducted");
                ui.showInsufficientBalance(payment);
                ui.showNotDone(currentMeal);
                ui.showAccountBalance(wallet);
            }
        }
        ui.showLine();
    }
}
