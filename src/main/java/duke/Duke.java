package duke;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Item;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.parsers.Parser;
import duke.user.User;
import duke.autocorrect.Autocorrect;

import java.util.Scanner;

/**
 * Duke is a public class that contains the main function to drive the program.
 * It encapsulates a Storage object, a TaskList object, and an Ui object.
 */
public class Duke {

    private Storage storage;
    private MealList tasks = new MealList();
    private Ui ui;
    private Scanner in = new Scanner(System.in);
    private User user;
    private Autocorrect autocorrect;

    /**
     * This is a constructor of Duke to start the program.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        user = new User();
        autocorrect = new Autocorrect();
        try {
            tasks = new MealList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new MealList();
        }
        try {
            user = storage.loadUser();
        } catch (DukeException e) {
            ui.showUserLoadingError();
        }
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
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
        while (user.getIsSetup() == false) {
            try {
                user.setup();
                ui.showUserSetupDone(user);
                storage.saveUser(user);
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        boolean isExit = false;
        ui.showWelcome();
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(in);
                ui.showLine();
                Command c = Parser.parse(fullCommand, autocorrect);
                c.execute(tasks, ui, storage, user);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * This is the main function.
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}














