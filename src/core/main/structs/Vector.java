package core.main.structs;

import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * Stores an X and Y coordinate of type Double
 * Useful for objects with fluid motion
 * 
 * @author VGLaptop
 */
public class Vector {
    
    public double x, y;

    /**
     * 
     * @param x The X coordinate for this vector
     * @param y 
     */
    public Vector(double x, double y){ this.x = x; this.y = y; }
    /**
     * 
     * @param v The vector to copy
     */
    public Vector(double v){ this.x = v; this.y = v; }
    /**
     * Empty constructor, initializes X and Y to (0, 0)
     */
    public Vector(){}
    /**
     * 
     * @param v The vector to copy
     */
    public Vector(Vector v){ this.x = v.x; this.y = v.y; }
    public Vector(Point p){ this.x = p.x; this.y = p.y; }
    public Vector(double[] d){ this.x = d[0]; this.y = d[1]; }
    
    public Point toPoint(){ return new Point((int)x, (int)y); }
    public double[] toDoubleArr(){ return new double[]{x, y}; }

    public Vector add(Vector v){ return new Vector(x + v.x, y + v.y); }
    public Vector subtract(Vector v){ return new Vector(x - v.x, y - v.y); }
    public Vector multiply(Vector v){ return new Vector(x * v.x, y * v.y); }
    public Vector divide(Vector v){ return new Vector(x / v.x, y / v.y); }

    public Vector add(double v){ return new Vector(x + v, y + v); }
    public Vector subtract(double v){ return new Vector(x - v, y - v); }
    public Vector multiply(double v){ return new Vector(x * v, y * v); }
    public Vector divide(double v){ return new Vector(x / v, y / v); }
    
    public double dot(Vector v){ return x*v.x + y*v.y; }

    public Vector half(){ return new Vector(x / 2, y / 2); }
    public Vector twice(){ return new Vector(x * 2, y * 2); }
    
    public Vector round(){ return new Vector(Math.round(x), Math.round(y)); }
    public Vector floor(){ return new Vector(Math.floor(x), Math.floor(y)); }
    public Vector ceil(){ return new Vector(Math.ceil(x), Math.ceil(y)); }
    
    /**
     * Moves this vector by a certain amount in a certain angle
     * @param angle The direction to move
     * @param amount The amount to move
     * @return A new vector that have moved from the original
     */
    public Vector move(double angle, double amount){
        return new Vector(x+(double)(Math.cos(angle)*amount), y+(double)(Math.sin(angle)*amount));
    }
    
    /**
     * Gets the angle from this vector to another vector
     * @param v the target vector
     * @return 
     */
    public double getAngle(Vector v){
        return Math.atan2(v.y-y, v.x-x);
    }
    
    public double getAngle(){
        return Math.atan2(y, x);
    }
    
    /**
     * Gets the distance between two vectors
     * @param v 
     * @return 
     */
    public double getDistance(Vector v){
        return Math.sqrt(getDistSqr(v));
    }
    
    /**
     * Moves a percentage of the distance towards the target vector
     * @param v The target Vector
     * @param speed The percentage of distance to move (between 0 and 1)
     */
    public void track(Vector v, double speed){
        x = x*(1-speed) + v.x*speed;
        y = y*(1-speed) + v.y*speed;
    }
    /**
     * Get the squared distance between two vectors (more efficient than get dist
     * @param v
     * @return 
     */
    public double getDistSqr(Vector v){
        return Math.pow(x-v.x, 2) + Math.pow(y-v.y, 2);
    }
    
    /**
     * @return Get the smaller coordinate
     */
    public double min(){ 
        return Math.min(x, y);
    }
    
    /**
     * @return Get the larger coordinate
     */
    public double max(){
        return Math.max(x, y);
    }
    
    public Vector max(Vector v){
        return new Vector(Math.max(x, v.x), Math.max(y, v.y));
    }
    
    public Vector min(Vector v){
        return new Vector(Math.min(x, v.x), Math.min(y, v.y));
    }
    
    /**
     * Returns a vector that is bounded inside the boundary
     * If the vector is outside the boundary, it is snapped in
     * @param bounds the boundary
     * @return the new vector
     */
    
    public double magnitude(){ return Math.sqrt(x*x + y*y); }
    
    public Vector rotate(Vector c, double a){
        float nx = (float)(c.x + (Math.cos(a) * (x-c.x) - Math.sin(a) * (y-c.y)));
        float ny = (float)(c.y + (Math.sin(a) * (x-c.x) + Math.cos(a) * (y-c.y)));
        return new Vector(nx, ny);
    } 
    
    public Vector rotate(double a){
        float nx = (float)(Math.cos(a) * x - Math.sin(a) * y);
        float ny = (float)(Math.sin(a) * x + Math.cos(a) * y);
        return new Vector(nx, ny);
    } 
    
    public Vector lowerBound(Vector b){
        Vector v = new Vector(this);
        if(v.x < b.x){ v.x = b.x; }
        if(v.y < b.y){ v.y = b.y; }
        return v;
    }
    
    public Vector upperBound(Vector b){
        Vector v = new Vector(this);
        if(v.x > b.x){ v.x = b.x; }
        if(v.y > b.y){ v.y = b.y; }
        return v;
    }
    
    public boolean lessThan(Vector v){ return x <= v.x && y <= v.y; }
    public boolean greaterThan(Vector v){ return x >= v.x && y >= v.y; }
    
    /**
     * 
     * @param v
     * @param dist
     * @return True if the two vectors are at most "dist" units apart
     */
    public boolean withinRange(Vector v, double dist){
        return getDistSqr(v) <= dist*dist;
    }
    /**
     * 
     * @param v
     * @param dist
     * @param canEqual Specifies whether the two vectors can be exactly "dist" units
     * apart, or whether they must be strictly less than "dist" units apart
     * @return 
     */
    public boolean withinRange(Vector v, double dist, boolean canEqual){
        if(canEqual){ return withinRange(v, dist); }
        return getDistSqr(v) < dist*dist;
    }
    
    public Vector transform(AffineTransform at){
        double[] d = toDoubleArr();
        at.transform(d, 0, d, 0, 1);
        return new Vector(d);
    }
    
    public boolean equals(Object o){
        return o!=null && o.getClass()==Vector.class && ((Vector)o).x==x && ((Vector)o).y==y;
    }
    
    public String toString(){
        return "(x = "+x+", y = "+y+")";
    }
}
