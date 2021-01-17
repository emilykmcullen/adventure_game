import game.Game;
import gameobjects.ThingList;
import gameobjects.actors.Actor;
import gameobjects.objects.Treasure;
import gameobjects.rooms.Room;
import globals.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActorTest {

    Game game;
    Treasure food;
    ThingList circleRoomList;
    Room room;
    ThingList playerList;
    Actor player;


    @Before
    public void setUp(){
        game = new Game();
        food = new Treasure("Apple", "Crunchy Apple", true, false, true, false, 5);
        circleRoomList = new ThingList();
        room = new Room("Circle room", "A strange room with no corners.", 0, Direction.NOEXIT, Direction.NOEXIT,3, circleRoomList);
        playerList = new ThingList();
        player = new Actor("player", "a loveable game-player", playerList, room, 20, 3);
    }

    @Test
    public void canIncreaseHpByValueOfItemIfEdible(){
        player.increaseHP(food);
        assertEquals(25, player.getHp());
    }


}
