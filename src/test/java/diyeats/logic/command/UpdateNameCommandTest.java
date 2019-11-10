package diyeats.logic.command;

import diyeats.logic.commands.UpdateAgeCommand;
import diyeats.logic.commands.UpdateNameCommand;
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
public class UpdateNameCommandTest {
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
    public void updateNameCommandTest() {
        setProfile(this.user);                                         //setting up profile
        assertEquals(this.user.getName(), "Foo Chi Hen");

        UpdateNameCommand c1 = new UpdateNameCommand("asdf");
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getName(), "asdf");

        c1 = new UpdateNameCommand("-1");
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getName(), "-1");

        c1 = new UpdateNameCommand("   ");
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getName(), "   ");
    }
}
