package duke.logic.command;

import duke.logic.commands.UpdateAgeCommand;
import duke.logic.commands.UpdateNameCommand;
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
public class UpdateNameCommandTest {
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
    public void updateNameCommandTest() {
        setProfile(this.user);                                         //setting up profile
        assertEquals(this.user.getName(), "Foo Chi Hen");

        UpdateNameCommand c1 = new UpdateNameCommand("asdf");
        c1.execute(meals, storage, user, wallet);
        assertEquals(this.user.getName(), "asdf");

        c1 = new UpdateNameCommand("-1");
        c1.execute(meals, storage, user, wallet);
        assertEquals(this.user.getName(), "-1");

        c1 = new UpdateNameCommand("   ");
        c1.execute(meals, storage, user, wallet);
        assertEquals(this.user.getName(), "   ");
    }
}
