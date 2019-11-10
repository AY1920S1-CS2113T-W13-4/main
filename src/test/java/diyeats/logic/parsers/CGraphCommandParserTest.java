package diyeats.logic.parsers;

import diyeats.logic.commands.CGraphCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

//@@author koushireo
public class CGraphCommandParserTest {

    private final String emptyTestStr = "";
    private final String invalidTestStr = "/type weight /month asdf /year 2019";
    private final String normalTestStr = "/type weight /month 11 /year 2019";

    // test empty input string
    @Test
    public void EmptyArgumentTest() {
        CGraphCommand c1 = new CGraphCommandParser().parse(emptyTestStr);
        assertTrue(c1.isFail());
    }
    // test invalid input string
    @Test
    public void InvalidArgumentTest() {
        CGraphCommand c1 = new CGraphCommandParser().parse(invalidTestStr);
        assertTrue(c1.isFail());
    }
    // test invalid input string
    @Test
    public void ValidArgumentTest() {
        CGraphCommand c1 = new CGraphCommandParser().parse(normalTestStr);
        assertFalse(c1.isFail());
    }
}