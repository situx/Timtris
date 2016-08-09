package com.github.situx.timtris.geometry;
/**
 * @author Timo
 *
 */
public class Vertex {
	public double x;//X-coordinate
	public double y;//Y-coordinate
	public Vertex(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**Adds one Vertex to another Vertex
	 * @param that is the second Vertex which is added to the given Vertex 
	 */
	public void addmod(Vertex that) {
		this.x+=that.x;
		this.y+=that.y;
	}
	/**Subtracts one Vertex from another Vertex
	 * @param that is the second Vertex which is substracted from the given Vertex
	 */
	public void submod(Vertex that) {
		this.x-=that.x;
		this.y-=that.y;
	}
	/**Adds one Vertex to another Vertex
	 * @param that is the second Vertex to which the given Vertex will be added
	 * @return a new Vertex that represents the result of the addition
	 */
	public Vertex add(Vertex that) {
		return new Vertex(this.x+that.x,this.y+that.y);
	}
	/**Subtracts one Vertex from another Vertex
	 * @param that is the second Vertex which is substracted from the given Vertex
	 * @return a new Vertex that represents the result of the substraction
	 */
	Vertex sub (Vertex that) {
		return new Vertex(this.x-that.x,this.y-that.y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	/**Multiplicates one Vertex with a double
	 * @param s the double to multiplicate with
	 * @return new Vertex with the new coordinates
	 */
	Vertex mul (float s) {
		return new Vertex(this.x*s,this.y*s);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object that) {//Checks if Vertex 1 == Vertex 2
		if (that instanceof Vertex) {
			Vertex v = (Vertex) that;
			return (v.x == x && v.y == y);
		}
		return super.equals(that);
	}
	
	public String toString() {//The toString Method of the Vertex
		return "X:"+x+"\nY:"+y;
	}
}
