import gameobjects.Thing;
import gameobjects.objects.Treasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThingTest {
    Thing thing;

    @Before
    public void setUp() {
        thing = new Thing("Gold", "Shiny gold coin", false, false, false, false);

    }
    @Test
    public void canGetTakeable(){
        assertEquals(false, thing.isTakeable());
    }

    @Test
    public void canSetTakeableToTrue(){
        thing.setTakeableToTrue();
        assertEquals(true, thing.isTakeable());
    }
}
