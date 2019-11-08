package duke.logic.command;

import duke.logic.commands.UpdateWeightCommand;
import duke.logic.commands.UserSetup;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author koushireo
public class UpdateWeightCommandTest {
    private User user = new User();
    private UserSetup setup = new UserSetup(user);
    private Wallet wallet = new Wallet();
    private Storage storage = new Storage();
    private MealList meals = new MealList();

    public void setProfile(User user) {
        setup.initialise("/name Foo Chi Hen /age 22 /height 100 /weight 100 /gender male /activity 2");
        user = setup.getUser();
    }

    @Test
    public void updateWeightCommandTest() {
        setProfile(this.user);                                 //setting up profile
        assertEquals(this.user.getWeight(), 100);

        UpdateWeightCommand c1 = new UpdateWeightCommand("asdfasdf"); //test for invalid input
        c1.execute(meals, storage, user, wallet);
        assertTrue(setup.getIsDone());
        assertEquals(this.user.getWeight(), 100);

        c1 = new UpdateWeightCommand("0");                     //test for boundary
        c1.execute(meals, storage, user, wallet);
        c1.setResponseStr("y");
        c1.execute(meals, storage, user, wallet);
        assertEquals(this.user.getWeight(), 100);

        c1 = new UpdateWeightCommand("95");                    //test for acceptance
        c1.execute(meals, storage, user, wallet);
        c1.setResponseStr("y");
        c1.execute(meals, storage, user, wallet);
        assertEquals(this.user.getWeight(), 95);
    }
}
