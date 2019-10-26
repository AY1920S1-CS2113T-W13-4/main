package duke.logic.parsers;

import duke.logic.commands.DeleteCommand;
import duke.commons.exceptions.DukeException;

public class DeleteCommandParser implements ParserInterface<DeleteCommand> {

    @Override
    public DeleteCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new DeleteCommand(indexAndDate[0], indexAndDate[1]);
    }
}
