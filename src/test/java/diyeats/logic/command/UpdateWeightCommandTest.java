package diyeats.logic.command;

import diyeats.logic.commands.UpdateWeightCommand;
import diyeats.logic.commands.UserSetup;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;
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
    private Undo undo = new Undo();

    public void setProfile(User user) {
        setup.initialise("/name Foo Chi Hen /age 22 /height 100 /weight 100 /gender male /activity 2");
        user = setup.getUser();
    }

    @Test
    public void updateWeightCommandTest() {
        setProfile(this.user);                                 //setting up profile
        assertEquals(this.user.getWeight(), 100);

        UpdateWeightCommand c1 = new UpdateWeightCommand("asdfasdf"); //test for invalid input
        c1.execute(meals, storage, user, wallet, undo);
        assertTrue(setup.getIsDone());
        assertEquals(this.user.getWeight(), 100);

        c1 = new UpdateWeightCommand("0");                     //test for boundary
        c1.execute(meals, storage, user, wallet, undo);
        c1.setResponseStr("y");
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getWeight(), 100);

        c1 = new UpdateWeightCommand("95");                    //test for acceptance
        c1.execute(meals, storage, user, wallet, undo);
        c1.setResponseStr("y");
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getWeight(), 95);
    }
}
