package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteDefaultValueCommand extends Command {
    private String keywordStr;
    private boolean isInstantDelete = false;
    private ArrayList<String> deleteCandidateKeys = new ArrayList<>();

    /**
     * Constructor for DeleteDefaultValueCommand.
     * @param keywordStr the keyword of meal to be deleted.
     */
    public DeleteDefaultValueCommand(String keywordStr) {
        this.keywordStr = keywordStr;
    }

    public DeleteDefaultValueCommand(boolean isFail, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        isDone = false;

        switch (stage) {
            case 0:
                execute_stage_0(meals, storage);
                stage++;
                break;
            case 1:
                execute_stage_1(meals, storage);
                break;
            default:
                isDone = true;
        }
    }

    private void execute_stage_0(MealList meals, Storage storage) {
        HashMap<String, HashMap<String, Integer>> defaultValues = meals.getDefaultValues();

        for (String itr : defaultValues.keySet()) {
            if (keywordStr.equals(itr)) {
                isInstantDelete = true;
                deleteCandidateKeys.add(itr);
                break;
            }

            if (itr.toLowerCase().contains(keywordStr.toLowerCase())) {
                deleteCandidateKeys.add(itr);
            }
        }

        if (isInstantDelete || deleteCandidateKeys.size() == 1) {
            int lastIdx = deleteCandidateKeys.size() - 1;
            ui.showMessage("Success! " + deleteCandidateKeys.get(lastIdx)
                    + " has been deleted from the list of default values.");
            meals.getDefaultValues().remove(deleteCandidateKeys.get(lastIdx));

            try {
                storage.updateFile(meals);
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
            }
            isDone = true;
        } else if (deleteCandidateKeys.size() == 0) {
            ui.showMessage("It appears there are no meals associated with the keyword provided.");
            isDone = true;
        } else {
            ui.showDeleteCandidateKeys(deleteCandidateKeys);
            ui.showMessage("Input 0 to cancel selection.");
        }
    }

    private void execute_stage_1(MealList meals, Storage storage) {
        int deleteIdx;

        try {
            deleteIdx = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (deleteIdx == 0) {
            ui.showMessage("The delete default command has been canceled.");
            isDone = true;
            return;
        }

        if (deleteIdx < 0 || deleteIdx > deleteCandidateKeys.size()) {
            ui.showMessage(responseStr + " is out of bounds. Please input a valid index.");
            return;
        }

        ui.showMessage("Success! " + deleteCandidateKeys.get(deleteIdx - 1)
                + " has been deleted from the list of default values.");
        meals.getDefaultValues().remove(deleteCandidateKeys.get(deleteIdx - 1));

        try {
            storage.updateFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }

        isDone = true;
    }
}
