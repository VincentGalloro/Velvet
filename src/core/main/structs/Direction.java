
package core.main.structs;

import java.awt.Point;

public enum Direction {
    UP (new Point(0, -1)),
    RIGHT (new Point(1, 0)),
    DOWN (new Point(0, 1)),
    LEFT (new Point(-1, 0));
    
    public Point offset;
    
    Direction(Point offset){
        this.offset = offset;
    }
    
    public Point move(Point pos){
        return new Point(pos.x + offset.x, pos.y + offset.y);
    }
    
    public static Direction getDir(Point start, Point end){
        if(start.x==end.x){ return end.y<start.y ? Direction.UP : Direction.DOWN; }
        return end.x<start.x ? Direction.LEFT : Direction.RIGHT;
    }
    
    public Direction opposite(){
        return Direction.values()[(ordinal()+2)%4];
    }
}
