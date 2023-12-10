package components;

import Tanks.TanksGLEventListener;


public class Bullet {
    public TanksGLEventListener.Directions directions;
    public int x,y;

    public boolean fired;

    public Bullet(TanksGLEventListener.Directions directions, int x, int y) {
        this.directions =directions;
        this.x = x;
        this.y = y;
        this.fired =true;

    }
}
