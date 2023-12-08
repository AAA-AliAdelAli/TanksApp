package Tanks;

public class Bullet {
    TanksGLEventListener.Directions directions;
    int x,y;

    boolean fired;

    public Bullet(TanksGLEventListener.Directions directions, int x, int y) {
        this.directions =directions;
        this.x = x;
        this.y = y;
        this.fired =true;

    }
}
