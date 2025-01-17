package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.MarkDoneCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * Parser class to handle the marking of a meal object as done in the model.
 */
public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {
    private static Logger logger = Logger.getLogger(DoneCommandParser.class.getName());

    /**
     * Parse user input and return MarkDoneCommand.
     * @param userInputStr String input by user.
     * @return <code>MarkDoneCommand</code> Command object encapsulating the index of the entry to be marked done
     */
    @Override
    public MarkDoneCommand parse(String userInputStr) {
        logger.setLevel(Level.OFF);
        try {
            InputValidator.validate(userInputStr);
        } catch (ProgramException e) {
            logger.log(Level.WARNING, "Unable to parse MarkDoneCommand");
            return new MarkDoneCommand(false, "Please enter index of meal to be marked done on today's list or "
                    + "date and index of meal to be marked done");
        }
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");

        return new MarkDoneCommand(indexAndDate[0], indexAndDate[1]);
    }
}
