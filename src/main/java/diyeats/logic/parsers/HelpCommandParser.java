package diyeats.logic.parsers;

import diyeats.logic.commands.HelpCommand;

//@@author GaryStu
/**
 * Parser class to handle the parsing of a help command.
 */
public class HelpCommandParser implements ParserInterface<HelpCommand> {

    /**
     * Parse user input and return HelpCommand.
     * @param userInputStr String input by user.
     * @return <code>HelpCommand</code> Command object containing the type of help sought by the user
     */
    @Override

    public HelpCommand parse(String userInputStr) {
        return new HelpCommand(userInputStr);
    }
}
