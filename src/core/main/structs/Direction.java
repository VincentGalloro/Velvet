package core.main.structs;

import java.awt.Point;

public enum Direction {
    NORTH_WEST (new Point(-1, -1)),
    NORTH (new Point(0, -1)),
    NORTH_EAST (new Point(1, -1)),
    WEST (new Point(0, -1)),
    NONE (new Point(0, 0)),
    EAST (new Point(1, 0)),
    SOUTH_WEST (new Point(1, -1)),
    SOUTH (new Point(0, 1)),
    SOUTH_EAST (new Point(1, 1));
    
    public Point offset;
    
    Direction(Point offset){
        this.offset = offset;
    }
    
    public Point move(Point pos){
        return new Point(pos.x + offset.x, pos.y + offset.y);
    }
    
    public Direction opposite(){
        return Direction.values()[8-ordinal()];
    }
}