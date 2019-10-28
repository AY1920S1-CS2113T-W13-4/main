package duke.model.user;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user = new User("Foo Chi Hen", 22, 100, Gender.MALE, 0, 100, new BigDecimal("3000"));

    @Test
    void user() {
        user.setWeight(100);
        assertEquals(user.getWeight(), 100);
        assertEquals(user.getName(), "Foo Chi Hen");
        assertEquals(user.getHeight(), 100);
        assertEquals(user.getActivityLevel(), 0);
        assertEquals(user.getAge(), 22);
        assertTrue(user.getAllWeight() instanceof ArrayList);
    }
}
