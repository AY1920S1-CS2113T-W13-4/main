package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author koushireo

public class UpdateHeightCommand extends Command {
    private String description;

    /**
     * Constructor for UpdateHeightCommand.
     * @param description the data to update the user document with
     */

    public UpdateHeightCommand(String description) {
        this.description = description;
    }

    public UpdateHeightCommand(boolean isFail, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateHeightCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        int heightInt = 0;
        try {
            heightInt = Integer.parseInt(description);
            if (heightInt < 54) {
                ui.showMessage("Height cannot be less than 54cm(Unless you really are the shortest man on earth!)");
            } else if (heightInt > 272) {
                ui.showMessage("Height cannot be more than 272cm(Unless you really are the tallest man on earth!)");
            } else {
                user.setHeight(heightInt);
                ui.showSuccess("height", description);
                try {
                    storage.writeUser(user);
                } catch (ProgramException e) {
                    ui.showMessage(e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number for height");
        }
    }

    /**
     * This function updates user height during userSetup.
     * @param user the object that handles all user data
     */
    public void updateUser(User user) {
        int heightInt = 0;
        try {
            heightInt = Integer.parseInt(description);
            if (heightInt < 54) {
                ui.showMessage("Height cannot be less than 54cm(Unless you really are the shortest man on earth!)");
            } else if (heightInt > 272) {
                ui.showMessage("Height cannot be more than 272cm(Unless you really are the tallest man on earth!)");
            } else {
                user.setHeight(heightInt);
                ui.showSuccess("height", description);
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number for height");
        }
    }

    /**
     * This function facilitates undo for updating height.
     * @param meals   the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user    the object that handles all user data
     * @param wallet  the wallet object that stores transaction information
     */

    public void undo(MealList meals, Storage storage, User user, Wallet wallet) {
        int heightInt = Integer.parseInt(description);
        user.setHeight(heightInt);
        try {
            storage.writeUser(user);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }
}