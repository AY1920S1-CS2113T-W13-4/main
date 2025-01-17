package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.AddTransactionCommand;
import diyeats.model.wallet.Deposit;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * Parser class to handle deposits to the wallet.
 */
public class DepositCommandParser implements ParserInterface<AddTransactionCommand> {
    LocalDate localDate = LocalDate.now();
    private static Logger logger = Logger.getLogger(DepositCommandParser.class.getName());

    /**
     * Parse user input and return AddTransactionCommand.
     * @param userInputStr String input by user.
     * @return <code>AddTransactionCommand</code> Command object encapsulating the amount to be deposited
     */
    @Override
    public AddTransactionCommand parse(String userInputStr) {
        logger.setLevel(Level.OFF);
        try {
            InputValidator.validate(userInputStr);
            String[] amountAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");
            InputValidator.validateAmount(amountAndDate[0]);
            if (!amountAndDate[1].isBlank()) {
                try {
                    localDate = LocalDate.parse(amountAndDate[1], dateFormat);
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "date cannot be parsed");
                    return new AddTransactionCommand(true, "Unable to parse " + amountAndDate[1] + " as a date. "
                            + "Please follow DD/MM/YYYY format.");
                }
                InputValidator.validateDate(localDate);
            }

            return new AddTransactionCommand(new Deposit(amountAndDate[0], localDate));
        } catch (ProgramException e) {
            return new AddTransactionCommand(true, e.getMessage());
        }
    }
}
