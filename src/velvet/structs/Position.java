package velvet.structs;

import java.awt.*;
import java.util.function.*;

public class Position {

    public static final Position UP = new Position(0, -1),
            RIGHT = new Position(1,0),
            DOWN = new Position(0, 1),
            LEFT = new Position(-1, 0);
    public static final Position[] DIRS = {UP,RIGHT,DOWN,LEFT};

    public final int x, y;

    public Position(int x, int y){ this.x = x; this.y = y; }
    public Position(int v){ this(v,v); }
    public Position(){ this(0,0); }

    public Position(Point p){ this(p.x,p.y); }

    public Vector toVector(){ return new Vector(x, y); }
    public Point toPoint(){ return new Point(x, y); }
    public int[] toIntArr(){ return new int[]{x, y}; }

    public Position apply(IntUnaryOperator func){
        return new Position(func.applyAsInt(x), func.applyAsInt(y));
    }
    public Position apply(IntBinaryOperator func, Position other){
        return new Position(func.applyAsInt(x, other.x), func.applyAsInt(y, other.y));
    }
    public int combine(IntBinaryOperator func){
        return func.applyAsInt(x, y);
    }

    public Position add(Position p){ return apply(Integer::sum, p); }
    public Position subtract(Position p){ return apply((a,b)->a-b, p); }
    public Position multiply(Position p){ return apply((a,b)->a*b, p); }
    public Position divide(Position p){ return apply((a,b)->a/b, p); }

    public Position add(int v){ return apply(a->a+v); }
    public Position subtract(int v){ return apply(a->a-v); }
    public Position multiply(int v){ return apply(a->a*v); }
    public Position divide(int v){ return apply(a->a/v); }

    public Position half(){ return divide(2); }
    public Position twice(){ return multiply(2); }

    public Position abs(){ return apply(Math::abs); }
    public Position max(Position p){
        return apply(Math::max, p);
    }
    public Position min(Position p){
        return apply(Math::min, p);
    }

    public Position negate(){ return multiply(-1); }
    public Position square(){ return apply(a->a*a); }
    public double magnitude(){ return Math.sqrt(x*x + y*y); }
    public Position flip(){ return new Position(y, x); }

    public Position move(int dir){
        return move(dir, 1);
    }
    public Position move(int dir, int amount){
        return add(DIRS[dir].multiply(amount));
    }

    public void gridIterate(Consumer<Position> action){
        for(int y = 0; y < this.y; y++){
            for(int x = 0; x < this.x; x++){
                action.accept(new Position(x, y));
            }
        }
    }

    public int getGridDistance(Position p){
        return subtract(p).abs().combine(Integer::sum);
    }
    public double getDistance(Position p){
        return subtract(p).magnitude();
    }

    public boolean lessThan(Position p){ return x <= p.x && y <= p.y; }
    public boolean greaterThan(Position p){ return x >= p.x && y >= p.y; }

    public boolean equals(Object o){
        return o!=null && o.getClass()==Position.class && ((Position)o).x==x && ((Position)o).y==y;
    }

    public int hashCode(){
        return x*22 + y*7;
    }

    public String toString(){
        return "(x = "+x+", y = "+y+")";
    }
}
