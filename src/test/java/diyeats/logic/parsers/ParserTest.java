package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.logic.commands.AddCommand;
import diyeats.logic.commands.AddDefaultValueCommand;
import diyeats.logic.commands.ExitCommand;
import diyeats.logic.commands.HelpCommand;
import diyeats.logic.commands.ListCommand;
import diyeats.logic.commands.MarkDoneCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    private Autocorrect autocorrect = new Autocorrect();

    @Test
    void parse() {
        try {
            Parser parser = new Parser(autocorrect);
            assertTrue(parser.parse("bye") instanceof ExitCommand);
            assertTrue(parser.parse("breakfast burger") instanceof AddCommand);
            assertTrue(parser.parse("breakfast burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("breakfast burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("lunch burger") instanceof AddCommand);
            assertTrue(parser.parse("lunch burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("lunch burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("dinner burger") instanceof AddCommand);
            assertTrue(parser.parse("dinner burger /calories 100") instanceof AddCommand);
            assertTrue(parser.parse("dinner burger /calories 100 /date 1/1/2019")
                    instanceof AddCommand);
            assertTrue(parser.parse("add burger /calorie 100 /sodium 100 /fats 100")
                    instanceof AddDefaultValueCommand);
            assertTrue(parser.parse("list") instanceof ListCommand);
            assertTrue(parser.parse("done 1") instanceof MarkDoneCommand);
            assertTrue(parser.parse("help") instanceof HelpCommand);
            assertTrue(parser.parse("help breakfast") instanceof HelpCommand);
        } catch (ProgramException e) {
            System.out.println("Something is wrong with the parser");
        }
    }
}

