package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.logic.commands.Command;
import diyeats.logic.commands.ExitCommand;
import diyeats.logic.commands.StatsCommand;
import diyeats.logic.commands.UndoCommand;

import static diyeats.commons.constants.CommandSyntax.PARSER_ADD_DEFAULT_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_ADD_EXERCISE_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_ADD_GOAL_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_BREAKFAST_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_CGRAPH_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_CLEAR_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DELETE_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DELETE_DEFAULT_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DELETE_EXERCISE_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DELETE_TRANSACTION_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DEPOSIT_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DINNER_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_DONE_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_EDIT_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_EXIT_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_FIND_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_HELP_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_HISTORY_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_LIST_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_LIST_TRANSACTION_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_LUNCH_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_PAYMENT_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_STATS_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_SUGGEST_EXERCISE_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_SUGGEST_MEAL_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_UNDO_COMMAND;
import static diyeats.commons.constants.CommandSyntax.PARSER_UPDATE_COMMAND;

//@@author
/**
 * Parser is a public class that help to parse the command that is inputted from the user.
 * And generate the appropriate command with their appropriate arguments
 */
public class Parser {
    private ParserUtil parserUtil;

    public Parser(Autocorrect autocorrect) {
        this.parserUtil = new ParserUtil(autocorrect);
    }

    /**
     * This is the main function that parse the command inputted by the user.
     * @param fullCommandStr the string the user input in the CLI
     * @return <code>new ExitCommand()</code>
     *         if the user input "bye"
     *         <code>new AddCommand(new Breakfast())</code> if the user input
     *         "breakfast" followed by the description of the meal
     *         <code>new AddCommand(new Lunch()</code> if the user input
     *         "lunch" followed by the description of the meal
     *         <code>new AddCommand(new Dinner()</code> if the user input
     *         "dinner" followed by the description of the meal
     *         <code>new ListCommand()</code> if the user input
     *         list
     *         <code>new MarkDoneCommand(index)</code> if the user input
     *         "done" followed by the index of the meal to be marked completed
     *         <code>new FindCommand(description)</code> if the user input
     *         "find" followed by the string that needs to be added
     *         <code>new DeleteCommand(index) </code> if the sure input
     *         "delete" followed by the index of the task to be deleted
     * @throws ProgramException when the command is not recognized or command syntax is invalid
     */
    public Command parse(String fullCommandStr) throws ProgramException {

        this.parserUtil.parse(fullCommandStr.trim());
        String commandStr = this.parserUtil.getCommand();
        String argumentStr = this.parserUtil.getArgument();
        switch (commandStr) {
            case PARSER_EXIT_COMMAND:
                return new ExitCommand();
            case PARSER_BREAKFAST_COMMAND:
                return new AddBreakfastCommandParser().parse(argumentStr);
            case PARSER_LUNCH_COMMAND:
                return new AddLunchCommandParser().parse(argumentStr);
            case PARSER_DINNER_COMMAND:
                return new AddDinnerCommandParser().parse(argumentStr);
            case PARSER_ADD_DEFAULT_COMMAND:
                return new AddDefaultValueCommandParser().parse(argumentStr);
            case PARSER_DELETE_DEFAULT_COMMAND:
                return new DeleteDefaultValueCommandParser().parse(argumentStr);
            case PARSER_LIST_COMMAND:
                return new ListCommandParser().parse(argumentStr);
            case PARSER_DONE_COMMAND:
                return new DoneCommandParser().parse(argumentStr);
            case PARSER_FIND_COMMAND:
                return new FindCommandParser().parse(argumentStr);
            case PARSER_DELETE_COMMAND:
                return new DeleteCommandParser().parse(argumentStr);
            case PARSER_UPDATE_COMMAND:
                return new UpdateCommandParser().parse(argumentStr);
            case PARSER_CLEAR_COMMAND:
                return new ClearCommandParser().parse(argumentStr);
            case PARSER_EDIT_COMMAND:
                return new EditCommandParser().parse(argumentStr);
            case PARSER_ADD_GOAL_COMMAND:
                return new AddGoalCommandParser().parse(argumentStr);
            case PARSER_HELP_COMMAND:
                return new HelpCommandParser().parse(argumentStr);
            case PARSER_DEPOSIT_COMMAND:
                return new DepositCommandParser().parse(argumentStr);
            case PARSER_PAYMENT_COMMAND:
                return new PaymentCommandParser().parse(argumentStr);
            case PARSER_HISTORY_COMMAND:
                return new HistoryCommandParser().parse(argumentStr);
            case PARSER_SUGGEST_MEAL_COMMAND:
                return new SuggestMealCommandParser().parse(argumentStr);
            case PARSER_STATS_COMMAND:
                return new StatsCommand();
            case PARSER_CGRAPH_COMMAND:
                return new GraphCommandParser().parse(argumentStr);
            case PARSER_SUGGEST_EXERCISE_COMMAND:
                return new SuggestExerciseCommandParser().parse(argumentStr);
            case PARSER_ADD_EXERCISE_COMMAND:
                return new AddExerciseCommandParser().parse(argumentStr);
            case PARSER_DELETE_EXERCISE_COMMAND:
                return new DeleteExerciseCommandParser().parse(argumentStr);
            case PARSER_LIST_TRANSACTION_COMMAND:
                return new ListTransactionCommandParser().parse(argumentStr);
            case PARSER_UNDO_COMMAND:
                return new UndoCommand();
            case PARSER_DELETE_TRANSACTION_COMMAND:
                return new DeleteTransactionCommandParser().parse(argumentStr);
            default:
                throw new ProgramException("I'm sorry, but I don't know what the command "
                        + "you have just entered means");
        }
    }
}
