package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.FindCommand;

public class FindCommandParser implements ParserInterface<FindCommand> {

    @Override
    public FindCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] nameAndDate = ArgumentSplitter.splitArguments(userInput, " /date ");
        return new FindCommand(nameAndDate[0], nameAndDate[1]);
    }
}
