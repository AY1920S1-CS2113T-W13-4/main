package duke;

import duke.logic.commands.Command;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.model.MealList;
import duke.ui.Ui;
import duke.logic.parsers.Parser;
import duke.model.user.User;
import duke.logic.autocorrect.Autocorrect;

import java.util.Scanner;

/**
 * Main is a public class that contains the main function to drive the program.
 * It encapsulates a Storage object, a MealList object, and an Ui object.
 */
public class Main {

    private Storage storage;
    private MealList tasks = new MealList();
    private Ui ui;
    private Scanner in = new Scanner(System.in);
    private User user;
    private Autocorrect autocorrect;

    /**
     * This is a constructor of Duke to start the program.
     */
    public Main() {
        ui = new Ui();
        user = new User();
        autocorrect = new Autocorrect();
        try {
            storage = new Storage();
            storage.load(tasks);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            tasks = new MealList();
        }
        try {
            user = storage.loadUser(); //load user inf
        } catch (DukeException e) {
            ui.showUserLoadingError();
        }
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     *  Run is a function that generate the flow of duke program from beginning until the end.
     */
    public void run() {
        if (user.getIsSetup() == false) {
            ui.showWelcomeNew();
        } else {
            ui.showWelcomeBack(user);
        }
        while (user.getIsSetup() == false) { //setup user profile if it's empty
            try {
                user.setup();
                ui.showUserSetupDone(user);
                storage.saveUser(user);
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        }
        boolean isExit = false;
        ui.showWelcome();
        Parser userParser = new Parser(autocorrect);
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(in);
                ui.showLine();
                Command c = userParser.parse(fullCommand);
                c.execute(tasks, ui, storage, user, in);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            } finally {
                ui.showLine();
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