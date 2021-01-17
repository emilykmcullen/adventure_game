import game.Game;
import gameobjects.Thing;
import gameobjects.ThingList;
import gameobjects.actors.Actor;
import gameobjects.actors.Enemy;
import gameobjects.objects.Treasure;
import gameobjects.rooms.Room;
import globals.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    Game game;
    Room room;
    Treasure treasure;
    Actor player;
    Enemy enemy1;
    Enemy enemy2;
    ThingList circleRoomList;
    ThingList warlockList;
    ThingList playerList;

    @Before
    public void setUp(){
        game = new Game();
        circleRoomList = new ThingList();
        room = new Room("Circle room", "A strange room with no corners.", 0, Direction.NOEXIT, Direction.NOEXIT,3, circleRoomList);
        treasure = new Treasure("Gold", "Shiny gold coin", true, false, false, false, 100 );
        playerList = new ThingList();
        player = new Actor("player", "a loveable game-player", playerList, room, 20, 3);
        warlockList = new ThingList();
        enemy1 = new Enemy("warlock", "a fearsome warlock", false, true, false, false, warlockList,10, 3 );
        enemy2 = new Enemy("troll", "a troll", false, true, false, false, warlockList, 10, 4);

    }

    @Test
    public void canReturnEnemyFromList(){
        game.addEnemyToList(enemy1);
        game.addEnemyToList(enemy2);
        assertEquals(enemy1, game.returnEnemyFromList("warlock"));
    }

    @Test
    public void canAddTreasureToEnemy(){
        warlockList.add(0, treasure);
        enemy1.setThings(warlockList);
        assertEquals(warlockList, enemy1.getThings());
        assertEquals(1, enemy1.getThings().size());
    }
//    @Test
//    public void ifEnemyKilledDropsTreasure(){
//        warlockList.add(0, treasure);
//        enemy1.setThings(warlockList);
//        game.addEnemyToList(enemy1);
//        player.
//        enemy1.reduceHp(10);
//    }

    @Test
    public void playerCanEatFood(){

    }

}
