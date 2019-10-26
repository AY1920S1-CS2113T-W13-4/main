package duke.logic.parsers;

import duke.logic.commands.ListCommand;
import duke.commons.exceptions.DukeException;

public class ListCommandParser implements ParserInterface<ListCommand> {

    @Override
    public ListCommand parse(String userInput) throws DukeException {
        return new ListCommand(userInput);
    }
}
