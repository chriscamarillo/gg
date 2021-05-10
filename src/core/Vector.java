package core;

public class Vector {
	double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this(0, 0);
	}
	
	static Vector add(Vector a, Vector b) {
		return new Vector(a.x + a.x, b.y + b.y);
	}
	
	static Vector sub(Vector a, Vector b) {
		return add(a, mult(b, -1));
	}
	
	static Vector mult(Vector v, double m) {
		return new Vector(m * v.x, m * v.y);
	}
	
	static double mag(Vector v) {
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
	
	static double dist(Vector a, Vector b) {
		Vector manhattan = sub(a, b);
		return mag(manhattan);
	}
}
