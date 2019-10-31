package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddCommand;
import duke.model.meal.Breakfast;

/**
 * Parser class to handle addition of breakfast item to model.
 */
public class AddBreakfastCommandParser implements ParserInterface<AddCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a breakfast object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public AddCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
            String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInputStr);
            return new AddCommand(new Breakfast(mealNameAndInfo[0], mealNameAndInfo[1]),"0");
        } catch (DukeException e) {
            return new AddCommand(false,e.getMessage());
        }
        //todo: handle trailing userInput without "/"
    }
}
