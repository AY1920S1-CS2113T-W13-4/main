package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.SuggestCommand;

public class SuggestCommandParser implements ParserInterface<SuggestCommand> {
    @Override
    public SuggestCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        // TODO: Finalize suggest command input format and update UG/DG
        return new SuggestCommand();
    }
}
