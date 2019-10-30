package duke;

import duke.commons.exceptions.DukeException;
import duke.logic.autocorrect.Autocorrect;
import duke.logic.commands.Command;
import duke.logic.commands.UserSetup;
import duke.logic.parsers.Parser;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Scanner;

/**
 * Main is a public class that contains the main function to drive the program.
 * It encapsulates a Storage object, a MealList object, and an Ui object.
 */
public class Main {
    private Storage storage;
    private MealList meals = new MealList();
    private Ui ui;
    private Scanner in = new Scanner(System.in);
    private User user;
    private Autocorrect autocorrect;
    private TransactionList transactions = new TransactionList();
    private UserSetup setup;
    private Wallet wallet;

    /**
     * This is a constructor of Duke to start the program.
     */
    public Main() {
        ui = new Ui();
        user = new User();
        autocorrect = new Autocorrect();
        wallet = new Wallet();
        try {
            storage = new Storage();
            storage.loadMealInfo(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            meals = new MealList();
        }
        try {
            user = storage.loadUser(); //load user info
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            ui.showUserLoadingError();
        }
        setup = new UserSetup(user);
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        try {
            storage.loadTransactions(transactions, wallet);
        } catch (DukeException e) {
            ui.showLoadingTransactionError();
        }
    }

    /**
     *  Run is a function that generate the flow of duke program from beginning until the end.
     */
    public void run() {
        setup.start();
        while (!setup.getIsDone()) { //setup user profile if it's empty
            String info = in.nextLine();
            setup.initialise(info);
        }
        user = setup.getUser();
        boolean isExit = false;
        Parser userParser = new Parser(autocorrect);
        while (!isExit) {
            try {
                String fullCommand = in.nextLine();
                Command c = userParser.parse(fullCommand);
                if (c.isFail()) {
                    c.failure();
                } else {
                    c.execute(meals, storage, user, wallet);
                    while (!c.isDone()) {
                        String word = in.nextLine();
                        c.setResponseStr(word);
                        c.execute(meals, storage, user, wallet);
                    }
                }
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        }
    }

    /**
     * This is the main function.
     */
    public static void main(String[] args) {
        new Main().run();
    }
}