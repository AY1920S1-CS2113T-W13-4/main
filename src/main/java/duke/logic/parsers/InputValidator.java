package duke.logic.parsers;

import duke.commons.exceptions.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * InputValidator is a public class that deals with validating user input.
 */
public class InputValidator {

    /**
     * validate the user input to check whether it's empty.
     * @param userInput String input by user.
     * @throws DukeException If the userInput is empty.
     */
    public static void validate(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("OOPS!!! The description of the command cannot be empty.");
        }
    }

    /**
     * validate the amount the user input (nutritional value or food cost) is numeric.
     * @param userInput String input by user.
     * @throws DukeException If the userInput is not numeric.
     */
    public static void validateAmount(String userInput) throws DukeException {
        if (!userInput.matches("-?\\d+(\\.\\d+)?")) {
            throw new DukeException("The nutritional value or food cost must be numeric.");
        }
    }

}
