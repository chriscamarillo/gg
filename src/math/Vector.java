package math;

import java.awt.geom.Point2D;

public class Vector extends Point2D.Double {
	double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this(0, 0);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + a.x, b.y + b.y);
	}
	
	public static Vector sub(Vector a, Vector b) {
		return add(a, mult(b, -1));
	}
	
	public static Vector mult(Vector v, double m) {
		return new Vector(m * v.x, m * v.y);
	}
	
	public static Vector hadamard(Vector a, Vector b) {
		return new Vector(a.x * b.x, a.y * b.y);
	}
	
	public static double mag(Vector v) {
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
	
	public static double dist(Vector a, Vector b) {
		Vector manhattan = sub(a, b);
		return mag(manhattan);
	}
	
	public static double dot(Vector a, Vector b) {
		return a.x * b.x + a.y * b.y;
	}
	
	public static double angleBetween(Vector a, Vector b) {
		try {
			return Math.acos(Vector.dot(a, b) / (Vector.mag(a) * Vector.mag(b)));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
