package gameobjects.rooms;

import gameobjects.ThingHolder;
import gameobjects.ThingList;

public class Room extends ThingHolder {
    private int n, s, w, e;
    private boolean enemyPresent;
    private boolean specialBlockedExit;

    public Room(String name, String description, int n, int s, int w, int e, ThingList tl, boolean enemyPresent){
        super(name, description, tl);
        this.n = n;
        this.s = s;
        this.w = w;
        this.e = e;
        this.enemyPresent = enemyPresent;
        this.specialBlockedExit = false;

    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public String describe(){
        return String.format("%s. %s.", getName(), getDescription())
                + "\nThings here:\n" + getThings().describeThings();
    }

    public boolean isEnemyPresent() {
        return enemyPresent;
    }

    public void setEnemyPresent(boolean enemyPresent) {
        this.enemyPresent = enemyPresent;
    }

    public boolean hasSpecialBlockedExit() {
        return specialBlockedExit;
    }

    public void setSpecialBlockedExit(boolean specialBlockedExit) {
        this.specialBlockedExit = specialBlockedExit;
    }
}
