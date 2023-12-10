package components;

import java.util.ArrayList;

public class Map {
    public ArrayList<Brick> bricks = new ArrayList<>();
    
    
    public ArrayList<Point2D> getWhiteBricksPositions() {
        ArrayList<Point2D> whiteBricksPositions = new ArrayList<>();
        for (Brick brick: bricks)
            if (brick.canBeBroken)
                whiteBricksPositions.add(brick.position);
            
        return whiteBricksPositions;
    }
}
