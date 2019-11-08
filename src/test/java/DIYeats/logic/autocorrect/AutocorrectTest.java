package DIYeats.logic.autocorrect;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutocorrectTest {
    private Storage storage = new Storage();
    private Autocorrect autocorrect = new Autocorrect();

    @Test
    void autocorrectTest() {
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            System.out.println("Error");
        }
        autocorrect.setWord("calorei");
        autocorrect.execute();
        assertEquals(autocorrect.getWord(), "calorie");
        autocorrect.setWord("lunxh");
        autocorrect.execute();
        assertEquals(autocorrect.getWord(), "lunch");
        autocorrect.setWord("cacium");
        autocorrect.execute();
        assertEquals(autocorrect.getWord(), "calcium");
    }
}