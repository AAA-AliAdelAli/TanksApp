package components;

public class Brick extends Sprite {
    public boolean canBeBroken;

    public Brick() {
        
    }
    
    public Brick(Point2D position, boolean canBeBroken) {
        this.position = position;
        this.canBeBroken = canBeBroken;
        this.index = canBeBroken ? 6 : 5;
    }
}
