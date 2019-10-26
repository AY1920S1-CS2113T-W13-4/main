package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddItemCommand;
import duke.model.Item;

public class AddItemCommandParser implements ParserInterface<AddItemCommand> {

    public AddItemCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new AddItemCommand(new Item(mealNameAndInfo[0], mealNameAndInfo[1]));
    }
}
