package duke.logic.parsers;

import duke.logic.commands.MarkDoneCommand;
import duke.commons.exceptions.DukeException;

public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {

    @Override
    public MarkDoneCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new MarkDoneCommand(indexAndDate[0], indexAndDate[1]);
    }
}
