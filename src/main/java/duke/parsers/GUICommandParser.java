package duke.parsers;

import duke.commands.GUICommand;
import duke.commands.ListCommand;
import duke.exceptions.DukeException;

public class GUICommandParser implements ParserInterface<GUICommand> {

    @Override
    public GUICommand parse(String UserInput) throws DukeException {
        String[] temp = UserInput.split(" ");
        return new GUICommand(temp[0], temp[1], temp[2]);
    }
}