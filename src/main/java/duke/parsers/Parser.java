package duke.parsers;

import duke.autocorrect.Autocorrect;
import duke.commands.*;
import duke.exceptions.DukeException;
import duke.tasks.*;

import java.lang.reflect.Array;
import java.util.Calendar;

/**
 * Parser is a public class that help to parse the command that is inputted from the user.
 * And generate the appropriate command with their appropriate arguments
 */
public class Parser {
    private static Calendar currentDate = Calendar.getInstance();
    private static HistoryCommand historyCommand = new HistoryCommand();
    private static Autocorrect autocorrect;

    public Parser(Autocorrect autocorrect) {
        this.autocorrect = autocorrect;
    }

    /**
     * This is the main function that parse the command inputted by the user.
     * @param fullCommand the string the user input in the CLI
     * @return <code>new ExitCommand()</code>
     *         if the user input "bye"
     *         <code>new AddCommand(new Breakfast())</code> if the user input
     *         "breakfast" followed by the description of the meal
     *         <code>new AddCommand(new Lunch()</code> if the user input
     *         "lunch" followed by the description of the meal
     *         <code>new AddCommand(new Dinner()</code> if the user input
     *         "dinner" followed by the description of the meal
     *         <code>new ListCommand()</code> if the user input
     *         list
     *         <code>new MarkDoneCommand(index)</code> if the user input
     *         "done" followed by the index of the meal to be marked completed
     *         <code>new FindCommand(description)</code> if the user input
     *         "find" followed by the string that needs to be added
     *         <code>new DeleteCommand(index) </code> if the sure input
     *         "delete" followed by the index of the task to be deleted
     * @throws DukeException when the command is not recognized or command syntax is invalid
     */
    public Command parse(String fullCommand) throws DukeException {
        String UserInput = "";
        String[] splitCommand = fullCommand.split(" ", 2);
        if (splitCommand.length != 2) {
            splitCommand = new String[] {splitCommand[0], ""};
        }
        String command = splitCommand[0];
        command = autocorrect.runOnCommand(command);
        UserInput = autocorrect.runOnArgument(splitCommand[1]);
        historyCommand.addCommand(command);

        switch (command) {
            case "bye":
                return new ExitCommand();
            case "breakfast":
                return new AddBreakfastCommandParser().parse(UserInput);
            case "lunch":
                return new AddLunchCommandParser().parse(UserInput);
            case "dinner":
                return new AddDinnerCommandParser().parse(UserInput);
            case "add" :
                return new AddItemCommandParser().parse(UserInput);
            case "list":
                return new ListCommandParser().parse(UserInput);
            case "done":
                return new DoneCommandParser().parse(UserInput);
            case "find":
                return new FindCommandParser().parse(UserInput);
            case "delete":
                return new DeleteCommandParser().parse(UserInput);
            case "update":
                return new UpdateWeightCommand(UserInput);
            case "clear":
                return new ClearCommandParser().parse(UserInput);
            case "edit":
                return new EditCommandParser().parse(UserInput);
            case "setgoal":
                return new SetgoalCommandParser().parse(UserInput);
            case "help":
                return new HelpCommandParser().parse(UserInput);
            case "history":
                // clear history if requested
                if (!UserInput.isEmpty() && UserInput.equals("clear")) {
                    historyCommand.clearHistory();
                }
                return historyCommand;
            case "graph":
                return new GUICommandParser().parse(UserInput);
            default:
                throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what " + command + " means :-(");
        }
    }
}
