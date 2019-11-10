package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * InputValidator is a public class that deals with validating user input.
 */
public class InputValidator {

    private static final String numericValidator = "-?\\d+(\\.\\d+)?";
    private static final String positiveValidator = "^[0-9]\\d*(\\.\\d+)?$";
    private static final String intValidator = "^[0-9]\\d*";
    private static final String positiveIntValidator = "^[1-9]\\d*";
    private static Logger logger = Logger.getLogger(InputValidator.class.getName());

    /**
     * validate the user input to check whether it's empty.
     * @param userInputStr String input by user.
     * @throws ProgramException If the userInput is empty.
     */
    public static void validate(String userInputStr) throws ProgramException {
        if (userInputStr.trim().length() == 0) {
            logger.log(Level.WARNING, "user input is empty");
            throw new ProgramException("OOPS!!! The description of the command cannot be empty.");
        }
        logger.log(Level.FINE, "user input is not empty");
    }

    /**
     * validate the amount the user input (nutritional value or food cost) is numeric.
     * @param amountInputStr amount input by user.
     * @throws ProgramException If the amount input by the user is not numeric or not positive.
     */
    public static void validateAmount(String amountInputStr) throws ProgramException {
        if (!amountInputStr.matches(numericValidator)) {
            logger.log(Level.WARNING, "food cost is not specified or it is not numeric");
            throw new ProgramException("The food cost must be specified and must be numeric.");
        }
        if (!amountInputStr.matches(positiveValidator)) {
            logger.log(Level.WARNING, "food cost is not positive");
            throw new ProgramException("Only positive value is accepted.");
        }
        logger.log(Level.FINE, "the amount is validated");
    }

    /**
     * validate the date so that transaction that happens in the future will not be recorded.
     * @param localDate the date of transaction.
     * @throws ProgramException if <code>LocalDate</code> is after <code>LocalDate.now()</code>.
     */
    public static void validateDate(LocalDate localDate) throws ProgramException {
        if (localDate.isAfter(LocalDate.now())) {
            logger.log(Level.WARNING, "date specified is in the future");
            throw new ProgramException("Cannot add transaction that happens in the future.");
        }
        logger.log(Level.FINE, "date specified is valid (it's in the past)");
    }

    /**
     * validate input index
     * @param userInputStr input list index from user
     * @throws ProgramException if <code>userInputStr</code> is not a positive integer
     */
    public static void validateIndex(String  userInputStr) throws ProgramException {
        if (!userInputStr.matches(positiveIntValidator)) {
            logger.log(Level.WARNING, "Index specified is not a positive integer.");
            throw new ProgramException(userInputStr + " is not a positive integer");
        }

        logger.log(Level.FINE, "Index is a positive integer");
    }

}
