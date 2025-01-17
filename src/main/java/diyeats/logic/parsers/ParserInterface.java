package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.Command;
import diyeats.ui.Ui;

import java.time.format.DateTimeFormatter;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

//@@author Fractalisk

/**
 * Represents a parser that processes user input into a Command of type T.
 * @param <T> type of Command returned.
 */
public interface ParserInterface<T extends Command> {

    Ui ui = new Ui();
    DateTimeFormatter dateFormat = LOCAL_DATE_FORMATTER;

    /**
     * Parses user input into a Command of type T and returns it.
     * @param userInputStr String input by user.
     * @return Command Returns a Command.
     * @throws ProgramException If the user input has syntactic errors.
     */
    T parse(String userInputStr) throws ProgramException;
}

