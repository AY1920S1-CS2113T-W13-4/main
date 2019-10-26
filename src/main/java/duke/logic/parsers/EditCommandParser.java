package duke.logic.parsers;

import duke.logic.commands.EditCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Meal;

public class EditCommandParser implements ParserInterface<EditCommand> {

    @Override
    public EditCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new EditCommand(new Meal(mealNameAndInfo[0], mealNameAndInfo[1]));
    }
}
