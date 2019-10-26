package duke.logic.parsers;

import duke.logic.commands.ClearCommand;
import duke.commons.exceptions.DukeException;

public class ClearCommandParser implements ParserInterface<ClearCommand> {

    @Override
    public ClearCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] startAndEndDates = ArgumentSplitter.splitArguments(userInput, " ");
        return new ClearCommand(startAndEndDates[0], startAndEndDates[1]);
    }
}
