package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.autocorrect.Autocorrect;
import duke.logic.commands.HistoryCommand;

/**
 * Utility class to handle pre-parsing of user inputs.
 */
public class ParserUtil {
    private Autocorrect autocorrect;
    private HistoryCommand history = new HistoryCommand();
    private String command;
    private String userInput;

    /**
     * Constructor for ParserUtil.
     * @param autocorrect the autocorrect object to be set
     */
    public ParserUtil(Autocorrect autocorrect) {
        this.autocorrect = autocorrect;
    }

    /**
     * Parse fullCommand into command and arguments.
     * @param fullCommand the full command entered by the user
     * @throws DukeException if the full command cannot be parsed
     */
    public void parse(String fullCommand) throws DukeException {
        userInput = "";
        try {
            String[] splitCommand = fullCommand.split(" ", 2);
            if (splitCommand.length != 2) {
                splitCommand = new String[]{splitCommand[0], ""};
            }
            command = splitCommand[0];
            userInput = splitCommand[1];
        } catch (Exception e) {
            throw new DukeException("A parser error has been encountered.");
        }
        command = autocorrect.runOnCommand(command);
        userInput = autocorrect.runOnArgument(userInput);
        history.addCommand(command);
    }

    /**
     * Getter for command.
     * @return command The string containing the command made by the user.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for argument.
     * @return argument The string containing the arguments made by the user.
     */
    public String getUserInput() {
        return userInput;
    }

    /**
     * Getter for history.
     * @param userInput the user input to be parsed
     * @return <code>history</code> the object containing records of all the past commands taken
     */
    public HistoryCommand getHistory(String userInput) {
        // clear history if requested
        if (!userInput.isEmpty() && userInput.equals("clear")) {
            history.clearHistory();
        }
        return history;
    }
}
