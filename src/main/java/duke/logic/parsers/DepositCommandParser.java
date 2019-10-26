package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.Deposit;

public class DepositCommandParser implements ParserInterface<AddTransactionCommand> {

    @Override
    public AddTransactionCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] amountAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new AddTransactionCommand(new Deposit(amountAndDate[0], amountAndDate[1]));
    }
}
