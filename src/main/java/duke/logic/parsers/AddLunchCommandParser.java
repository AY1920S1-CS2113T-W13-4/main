package duke.logic.parsers;

import duke.logic.commands.AddCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Lunch;

public class AddLunchCommandParser implements ParserInterface<AddCommand> {

    @Override
    public AddCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new AddCommand(new Lunch(mealNameAndInfo[0], mealNameAndInfo[1]));
    }
}
