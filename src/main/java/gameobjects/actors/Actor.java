package gameobjects.actors;

import gameobjects.ThingHolder;
import gameobjects.ThingList;
import gameobjects.objects.Treasure;
import gameobjects.rooms.Room;

public class Actor extends ThingHolder {
    private Room location;// current room where the person is
    private int hp;
    private int fightPoints;
    private boolean hasCoolBook;
    private boolean isWearingJeans;
    private boolean isWearingShades;

    public Actor(String name, String description, ThingList tl, Room room, int hp, int fightPoints){
        super(name, description, tl);
        this.location = room;
        this.hp = hp;
        this.fightPoints = fightPoints;
        this.hasCoolBook = false;
        this.isWearingJeans = false;
        this.isWearingShades = false;
    }

    public Actor(String name, String description, boolean canTake, boolean canFight, boolean canEat, boolean canDrink, ThingList tl, Room room, int hp, int fightPoints){
        super(name, description, canTake, canFight, canEat, canDrink, tl);
        this.location = room;
        this.hp = hp;
        this.fightPoints = fightPoints;
        this.hasCoolBook = false;
        this.isWearingJeans = false;
        this.isWearingShades = false;

    }


    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void reduceHp(int damage){
        this.hp -= damage;
    }

    public void increaseHP(Treasure consumable){
        if (consumable.isEatable()){
            this.hp += consumable.getValue();
        }
    }

    public int getFightPoints() {
        return fightPoints;
    }

    public boolean hasCoolBookInPossessions() {
        return hasCoolBook;
    }

    public void setHasCoolBook(boolean hasCoolBook) {
        this.hasCoolBook = hasCoolBook;
    }

    public boolean isWearingJeans() {
        return isWearingJeans;
    }

    public void setWearingJeans(boolean wearingJeans) {
        isWearingJeans = wearingJeans;
    }

    public boolean isWearingShades() {
        return isWearingShades;
    }

    public void setWearingShades(boolean wearingShades) {
        isWearingShades = wearingShades;
    }
}
