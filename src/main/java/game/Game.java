package game;

import gameobjects.Thing;
import gameobjects.ThingList;
import gameobjects.actors.Actor;
import gameobjects.actors.Enemy;
import gameobjects.objects.Treasure;
import gameobjects.rooms.Room;
import globals.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private ArrayList<Room> map; // the map - an ArrayList of Rooms
    private ArrayList<Enemy> enemies;
    private Actor player;  // the player - provides 'first person perspective'

    private List<String> commands = new ArrayList<>(Arrays.asList(
            "take", "drop", "look", "l", "i", "inventory", "fight", "eat", "drink", "wear",
            "n", "s", "w", "e"
             ));
    private List<String> objects = new ArrayList<>(Arrays.asList("shades", "jeans", "potion",
            "key", "book", "sword", "pop-tart", "warlock", "goblin", "chips"));

    public Game() {
        this.map = new ArrayList<Room>(); // TODO: Make map a Generic list of Rooms
        this.enemies = new ArrayList<Enemy>();

        //-- create Enemies -- //
        ThingList warlockList = new ThingList();
        ThingList goblinList = new ThingList();
        goblinList.add(new Treasure("pop-tart", "the pop tart is still warm and smells delicious.", false, false, true, false, 3));
        goblinList.add(new Treasure("chips", "5 chips", false, false, true, false, 3));


        Enemy grahamTheWarlock = new Enemy("warlock", "a fearsome warlock", false, true, false, false, warlockList,10, 3 );
        Enemy banjoTheGoblin = new Enemy("goblin", "a mean looking goblin stares at you. It looks like there's a pop-tart in it's hand.", false, true, false, false, goblinList, 2, 2);;

        //add enemies to list //
        addEnemyToList(grahamTheWarlock);
        addEnemyToList(banjoTheGoblin);

        // --- construct a new adventure ---


        ThingList coolRoomList = new ThingList();
        Treasure shades = new Treasure("shades", "A pair of stunning designer shades", true, false, false, false, 5);
        Treasure jeans = new Treasure("jeans", "Some stylishly ripped jeans with studs near the pockets", true, false, false, false, 4);
        shades.setWearable(true);
        jeans.setWearable(true);
        coolRoomList.add(shades);
        coolRoomList.add(jeans);

        ThingList hutList = new ThingList();
        hutList.add(new Treasure("key", "a small key, how interesting", true, false, false , false,10));
        hutList.add(grahamTheWarlock);

        ThingList circleRoomList = new ThingList();
        Treasure bookOfCool = new Treasure("book", "the book is titled \"How To Be Cool\".This could be useful", true, false, false, false , 5);
        circleRoomList.add(bookOfCool);

        ThingList startRoomList = new ThingList();
        startRoomList.add(new Treasure("sword", "the sword is very rusty but it still looks sharp", true, false, false, false, 6));
        // Still to implement method kill the goblin set canTake to true
        startRoomList.add(banjoTheGoblin);

        ThingList forestList = new ThingList();
        forestList.add(new Treasure("potion", "a potion bubbling in a glass in the bushes", true, false, false, true, 3));


        ThingList playerlist = new ThingList();

        //Create rooms with special blocked exits
        Room starterRoom = new Room("Street outside of the hero party", "It is really cold but the bouncer isn't moving.", Direction.NOEXIT, Direction.NOEXIT, 2, Direction.NOEXIT, startRoomList, true);
        starterRoom.setSpecialBlockedExit(true);

        Room hutRoom = new Room("Tiny hut", "A tiny hut", Direction.NOEXIT, 0, 4, Direction.NOEXIT, hutList, true);
        hutRoom.setSpecialBlockedExit(true);

       // Add Rooms to the map
//                 Room( name,   description,                             N,        S,      W,      E )
        map.add(new Room("Forest", "A deep dark forest, there is an owl tit twooing somewhere", 1, 2, Direction.NOEXIT, Direction.NOEXIT, forestList, false));
        map.add(hutRoom);
        map.add(new Room("Circle room", "A strange room with no corners.", 0, Direction.NOEXIT, Direction.NOEXIT,3, circleRoomList, false));
        map.add(starterRoom);
        map.add(new Room("Coolest Place Ever", "A glorious assortment of really, really cool things litter the room", Direction.NOEXIT, Direction.NOEXIT, Direction.NOEXIT, 1, coolRoomList, false));


        // create player and place in Room 0 (i.e. the Room at 0 index of map)

        player = new Actor("player", "a loveable game-player", playerlist, map.get(3), 20, 3);
    }

    // access methods
    // map
    private ArrayList getMap() {
        return map;
    }

    private void setMap(ArrayList aMap) {
        map = aMap;
    }

    // player
    private Actor getPlayer() {
        return player;
    }

    public void setPlayer(Actor aPlayer) {
        player = aPlayer;
    }

    // take and drop
    private void transferOb(Thing t, ThingList fromlist, ThingList tolist) {
        fromlist.remove(t);
        tolist.add(t);
    }

    private void removeObFromList(Thing t, ThingList list){
        list.remove(t);
    }

    private String takeOb(String obname) {
        String retStr = "";
        Thing t = player.getLocation().getThings().thisOb(obname);
        if (obname.equals("")) {
            obname = "nameless object"; // if no object specified
        }
        if (t == null) {
            retStr = "There is no " + obname + " here!";
        }
        if (t.isTakeable()) {
            transferOb(t, player.getLocation().getThings(), player.getThings());
            retStr = obname + " taken!";
            if (t.getName() == "key"){
                map.get(1).setSpecialBlockedExit(false);
            }
        }
        else {
            retStr = "You cannot take " + obname + "!";
        }
        return retStr;
    }

    // Eat object had to cast thing to treasure to access value
    private String eatOb(String obname) {
        String retStr = "";
        Treasure t = (Treasure) player.getThings().thisOb(obname);

        if (obname.equals("")){
            obname = "nameless object";
        } if (t == null) {
            retStr = "You don't have a " + obname + " in your backpack.";
        } else {
            if (t.isEatable()) {
                player.increaseHP(t);
                removeObFromList(t, player.getThings());
                retStr = obname + " eaten! HP increase by " + t.getValue() + " to " + player.getHp();
            }
            else {
                retStr = "You can't eat" + obname;
            }
        }
        return retStr;
    }

    private String drinkOb(String obname){
        String retStr = "";
        Treasure t = (Treasure) player.getThings().thisOb(obname);

        if (obname.equals("")){
            obname = "nameless object";
        } if (t == null) {
            retStr = "You don't have a " + obname + " in your backpack.";
        } else {
            if (t.isDrinkable()) {
                player.increaseHP(t);
                removeObFromList(t, player.getThings());
                retStr = obname + " eaten! HP increase by " + t.getValue() + " to " + player.getHp();
            }
            else {
                retStr = "You can't drink" + obname;
            }
        }
        return retStr;
    }

    

    private String dropOb(String obname) {
        String retStr = "";
        Thing t = player.getThings().thisOb(obname);
        if (obname.equals("")) {
            retStr = "You'll have to tell me which object you want to drop!"; // if no object specified
        } else if (t == null) {
            retStr = "You haven't got one of those!";
        } else {
            transferOb(t, player.getThings(), player.getLocation().getThings());
            retStr = obname + " dropped!";
            if (t.getName() == "key"){
                map.get(1).setSpecialBlockedExit(true);
            }
        }
        return retStr;
    }

    private String wearOb(String obname) {
        String retStr = "";
        Thing t = player.getThings().thisOb(obname);
        if (obname.equals("")){
            obname = "nameless object";
        }
        if (t == null) {
            retStr = "You don't have a " + obname + " in your backpack.";
        }
        else {
            if (t.isWearable() == false){
                retStr = "You can't wear " + obname;
            }
            if (t.isWearable() && player.hasCoolBookInPossessions() == true) {
                retStr = "You put on the " + obname + ".\n" +
                        "Looking good!";
                if (t.getName() == "jeans"){
                    player.setWearingJeans(true);
                }
                if (t.getName() == "shades"){
                    player.setWearingShades(true);
                }
            }
            if (t.isWearable() && player.hasCoolBookInPossessions() == false){
                retStr = "You look at the " + obname + "but you don't know what to do with them! \n"
                        + "If only you had some sort of guide to show you the way....";
            }
        }
        return retStr;

    }



    private String fightEnemy(String obname) {
        String retStr = "";
        Thing enemyName = player.getLocation().getThings().thisOb(obname);
        if (obname.equals("")){
            retStr = "You'll have to tell me who you want to fight!";
        } else if (enemyName == null){
            retStr = "That enemy isn't here!";
        }
        else {
            Enemy enemy = returnEnemyFromList(obname);
            if (enemy.isFightable()){
                if (isAnyoneDefeated(player, enemy).equals("no")){
                player.reduceHp(enemy.getAttackPoints());
                enemy.reduceHp(player.getFightPoints());
                retStr = "You engage in a fierce battle with " + enemy.getName() + ".\n"
                        + "You hit " + enemy.getName() + " and they lose " + player.getFightPoints() + " health points. \n"
                        + enemy.getName() + " hits you and you lose " + enemy.getAttackPoints() + " health points. \n"
                        + "Your HP: " + player.getHp() + " " + enemy.getName() + " HP: " + enemy.getHp();
                }
                else if (isAnyoneDefeated(player, enemy).equals("enemy")){
                    //enemy is removed from room
                    removeObFromList(enemyName, player.getLocation().getThings());
                    player.getLocation().setEnemyPresent(false);
                    retStr = "You engage in a fierce battle with " + enemy.getName() + ".\n" +
                            "You defeat them with one blow!" + "\n"
                     + takeDeadEnemiesTreasure(player, enemy);
                            }
                else{
                    //player is defeated
                    retStr = "You engage in a fierce battle with " + enemy.getName() + ".\n" +
                            "You are defeated. You die.";
                    //add end game scenario here
                }

            }
            else{
                retStr = "You can't fight" + enemy.getName() + "!";
            }
        }
        return retStr;
    }

    public String takeDeadEnemiesTreasure(Actor player, Enemy enemy){
        String retStr= "";
        String s = "";
        ThingList enemiesThings = enemy.getThings();
        if (enemiesThings.size() > 0)
            {
                for(Thing t : enemy.getThings()) {
                    s = s + t.getName() + ":" + t.getDescription() + "\n";
                }

                retStr = enemy.getName() + " dropped : \n"
                        + s + "\n" + "You take dead enemies treasure!";

                for (Thing t : enemiesThings){
                    player.getThings().add(t);
                }
            }
        return retStr;
    }

    private String isAnyoneDefeated(Actor player, Enemy enemy){
        if (player.getFightPoints() >= enemy.getHp()){
            return "enemy";
        }
        else if (enemy.getAttackPoints() >= player.getHp()){
            return "player";
        }
        else {
            return "no";
        }
    }

    public void addEnemyToList(Enemy enemy){
        this.enemies.add(enemy);
    }

    public Enemy returnEnemyFromList(String obname) {
        Enemy enemy = null;
        String thingName = "";
        String enemyNameLowCase = obname.trim().toLowerCase();

        for(Enemy e : this.enemies) {
            thingName = e.getName().trim().toLowerCase();
            if(thingName.equals(enemyNameLowCase)){
                enemy = e;
            }
        }
        return enemy;
    }

    // move a Person (typically the player) to a Room
    private void moveActorTo(Actor p, Room aRoom) {
        p.setLocation(aRoom);
    }

    // move an Actor in direction 'dir'
    private int moveTo(Actor anActor, Direction dir) {
        // return: Constant representing the room number moved to
        // or NOEXIT
        //
        // try to move any Person (typically but not necessarily player)
        // in direction indicated by dir
        Room r = anActor.getLocation();
        int exit;

        switch (dir) {
            case NORTH:
                exit = r.getN();
                break;
            case SOUTH:
                exit = r.getS();
                break;
            case EAST:
                exit = r.getE();
                break;
            case WEST:
                exit = r.getW();
                break;
            default:
                exit = Direction.NOEXIT; // noexit - stay in same room
                break;
        }
        if (exit != Direction.NOEXIT) {
            moveActorTo(anActor, map.get(exit));
        }
        return exit;
    }

    private void movePlayerTo(Direction dir) {
        // if roomNumber = NOEXIT, display a special message, otherwise
        // display text (e.g. name and description of room)
        if (moveTo(player, dir) == Direction.NOEXIT) {
            showStr("No Exit!");
        } else {
            look();
        }
    }

    private void goN() {
        if (player.getLocation().isEnemyPresent() == true){cannotLeaveRoom();}
        else {movePlayerTo(Direction.NORTH);}
    }

    private void goS() {
        if (player.getLocation().isEnemyPresent() == true){cannotLeaveRoom();}
        else {movePlayerTo(Direction.SOUTH);}
    }

    private void goW() {
        if(player.getLocation() == map.get(1)){lockedCoolRoom();}

        else if (player.getLocation().isEnemyPresent() == true){cannotLeaveRoom();}

        else {movePlayerTo(Direction.WEST);}
    }

    private void goE() {
        if (player.getLocation()== map.get(3)){talkToBouncer();}

        else if (player.getLocation().isEnemyPresent() == true){cannotLeaveRoom();}

        else {movePlayerTo(Direction.EAST);}
    }

    private void cannotLeaveRoom(){
        showStr("You must first defeat the enemy in the room!");
    }

    private void talkToBouncer(){
        if (map.get(3).hasSpecialBlockedExit() == true){
            showStr("The bouncer says: 'Sorry pal, you're not cool enough for this crazy shindig, hit the road. STAT.'");
        }
        //NEED TO ADD END GAME FUNCTION HERE IN ELSE STATEMENT!!!!!!!
    }

    private void lockedCoolRoom(){
        if(map.get(1).hasSpecialBlockedExit() == true){
            showStr("There's a big wooden door, you push it .... it's locked!!");
        }
        else {
            showStr("You try the key in the door, it opens!");
            movePlayerTo(Direction.WEST);
        }
    }

    private void look() {
        showStr("You are in the " + getPlayer().getLocation().describe());
    }

    private void showStr(String s) {
        System.out.println(s);
    }

    private void showInventory() {
        showStr("You have " + getPlayer().getThings().describeThings());
    }

    private String processVerb(List<String> wordlist) {
        String verb;
        String msg = "";
        verb = wordlist.get(0);
        if (!commands.contains(verb)) {
            msg = verb + " is not a known verb! ";
        } else {
            switch (verb) {
                case "n":
                    goN();
                    break;
                case "s":
                    goS();
                    break;
                case "w":
                    goW();
                    break;
                case "e":
                    goE();
                    break;
                case "l":
                case "look":
                    look();
                    break;
                case "inventory":
                case "i":
                    showInventory();
                    break;
                default:
                    msg = verb + " (not yet implemented)";
                    break;
            }
        }
        return msg;
    }

    private String processVerbNoun(List<String> wordlist) {
        String verb;
        String noun;
        String msg = "";
        boolean error = false;
        verb = wordlist.get(0);
        noun = wordlist.get(1);
        if (!commands.contains(verb)) {
            msg = verb + " is not a known verb! ";
            error = true;
        }
        if (!objects.contains(noun)) {
            msg += (noun + " is not a known noun!");
            error = true;
        }
        if (!error) {
            switch (verb) {
                case "take":
                    msg = takeOb(noun);
                    break;
                case "drop":
                    msg = dropOb(noun);
                    break;
                case "fight":
                    msg = fightEnemy(noun);
                    break;
                case "eat":
                    msg = eatOb(noun);
                    break;
                case "drink":
                    msg = drinkOb(noun);
                    break;
                case "wear":
                    msg = wearOb(noun);
                    break;
                default:
                    msg += " (not yet implemented)";
                    break;
            }
        }
        return msg;
    }

    private String parseCommand(List<String> wordlist) {
        String msg;
        if (wordlist.size() == 1) {
            msg = processVerb(wordlist);
        } else if (wordlist.size() == 2) {
            msg = processVerbNoun(wordlist);
        } else {
            msg = "Only 2 word commands allowed!";
        }
        return msg;
    }

    private static List<String> wordList(String input) {
        String delims = "[ \t,.:;?!\"']+";
        List<String> strlist = new ArrayList<>();
        String[] words = input.split(delims);

        for (String word : words) {
            strlist.add(word);
        }
        return strlist;
    }

    public void showIntro() {
        String s;
        s = "You have just arrived at the entrance to the hero party but the bouncer won't let you in!\n"
                + "You've gotta get yourself looking the part!.\n"
                + "Where do you want to go? [Enter n, s, w or e]?\n"
                + "(or enter q to quit)";
        showStr(s);
        look();
    }

    public String runCommand(String inputstr) {
        List<String> wordlist;
        String s = "ok";
        String lowstr = inputstr.trim().toLowerCase();
        if (!lowstr.equals("q")) {
            if (lowstr.equals("")) {
                s = "You must enter a command";
            } else {
                wordlist = wordList(lowstr);
                s = parseCommand(wordlist);
            }
        }
        return s;
    }

}