package velvet.structs;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class Vector {

    public static final Vector ZERO = new Vector(0);
    public static final Vector ONE = new Vector(1);

    public static Vector unitVector(double angle){
        return new Vector(Math.cos(angle), Math.sin(angle));
    }

    public final double x, y;

    public Vector(double x, double y){ this.x = x; this.y = y; }
    public Vector(double v){ this(v,v); }

    public Vector(Point p){ this(p.x,p.y); }

    public Position toPosition(){ return new Position((int)x,(int)y); }
    public Point toPoint(){ return new Point((int)x, (int)y); }
    public double[] toDoubleArr(){ return new double[]{x, y}; }

    public Vector apply(DoubleUnaryOperator func){
        return new Vector(func.applyAsDouble(x), func.applyAsDouble(y));
    }
    public Vector apply(DoubleBinaryOperator func, Vector other){
        return new Vector(func.applyAsDouble(x, other.x), func.applyAsDouble(y, other.y));
    }
    public double combine(DoubleBinaryOperator func){
        return func.applyAsDouble(x, y);
    }

    public Vector add(Vector v){ return apply(Double::sum, v); }
    public Vector subtract(Vector v){ return apply((a,b)->a-b, v); }
    public Vector multiply(Vector v){ return apply((a,b)->a*b, v); }
    public Vector divide(Vector v){ return apply((a,b)->a/b, v); }

    public Vector add(double v){ return apply(a->a+v); }
    public Vector subtract(double v){ return apply(a->a-v); }
    public Vector multiply(double v){ return apply(a->a*v); }
    public Vector divide(double v){ return apply(a->a/v); }

    public Vector half(){ return divide(2); }
    public Vector twice(){ return multiply(2); }
    
    public Vector round(){ return apply(Math::round); }
    public Vector floor(){ return apply(Math::floor); }
    public Vector ceil(){ return apply(Math::ceil); }
    public Vector abs(){ return apply(Math::abs); }
    public Vector max(Vector v){
        return apply(Math::max, v);
    }
    public Vector min(Vector v){
        return apply(Math::min, v);
    }

    public Vector negate(){ return multiply(-1); }
    public Vector invert(){ return apply(a->1/a); }
    public Vector square(){ return apply(a->a*a); }
    public double magnitude(){ return square().combine(Double::sum); }
    public Vector flip(){ return new Vector(y, x); }

    public Vector move(double angle, double amount){
        return add(unitVector(angle).multiply(amount));
    }

    public double getAngle(Vector v){
        return v.subtract(this).getAngle();
    }
    public double getAngle(){
        return flip().combine(Math::atan2);
    }

    public double getDistance(Vector v){
        return Math.sqrt(getDistSqr(v));
    }
    public double getDistSqr(Vector v){
        return subtract(v).square().combine(Double::sum);
    }

    public Vector rotate(Vector c, double a){
        return subtract(c).rotate(a).add(c);
    }
    public Vector rotate(double a){
        return unitVector(a).multiply(x).add(unitVector(a+Math.PI/2).multiply(y));
    } 

    public boolean lessThan(Vector v){ return x <= v.x && y <= v.y; }
    public boolean greaterThan(Vector v){ return x >= v.x && y >= v.y; }
    
    public Vector transform(AffineTransform at){
        double[] d = toDoubleArr();
        at.transform(d, 0, d, 0, 1);
        return new Vector(d[0],d[1]);
    }

    public Vector inverseTransform(AffineTransform at){
        double[] d = toDoubleArr();
        try {
            at.inverseTransform(d, 0, d, 0, 1);
        } catch (NoninvertibleTransformException ignored) {}
        return new Vector(d[0],d[1]);
    }
    
    public boolean equals(Object o){
        return o!=null && o.getClass()==Vector.class && ((Vector)o).x==x && ((Vector)o).y==y;
    }
    
    public String toString(){
        return "(x = "+x+", y = "+y+")";
    }
}
