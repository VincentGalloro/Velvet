
package core.main.structs;

import java.awt.Point;

public enum GridDirection {
    UP (new Point(0, -1)),
    RIGHT (new Point(1, 0)),
    DOWN (new Point(0, 1)),
    LEFT (new Point(-1, 0));
    
    public Point offset;
    
    GridDirection(Point offset){
        this.offset = offset;
    }
    
    public Point move(Point pos){
        return new Point(pos.x + offset.x, pos.y + offset.y);
    }
    
    public static GridDirection getDir(Point start, Point end){
        if(start.x==end.x){ return end.y<start.y ? GridDirection.UP : GridDirection.DOWN; }
        return end.x<start.x ? GridDirection.LEFT : GridDirection.RIGHT;
    }
    
    public GridDirection opposite(){
        return GridDirection.values()[(ordinal()+2)%4];
    }
}
