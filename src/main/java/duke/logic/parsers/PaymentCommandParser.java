package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.Payment;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class PaymentCommandParser implements ParserInterface<AddTransactionCommand> {

    @Override
    public AddTransactionCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] amountAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new AddTransactionCommand(new Payment(amountAndDate[0], amountAndDate[1]));
    }
}
