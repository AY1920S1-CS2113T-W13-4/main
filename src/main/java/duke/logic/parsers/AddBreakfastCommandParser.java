package duke.logic.parsers;

import duke.logic.commands.AddCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Breakfast;

public class AddBreakfastCommandParser implements ParserInterface<AddCommand> {

    @Override
    public AddCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new AddCommand(new Breakfast(mealNameAndInfo[0], mealNameAndInfo[1]));
        //todo: handle trailing userInput without "/"

    }

}
