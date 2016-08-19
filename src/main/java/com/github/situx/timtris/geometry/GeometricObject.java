/*
 *
 *  * This file is part of Timtris.
 *  *
 *  *   Timtris is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *    Timtris is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with Timtris.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *
 */

package com.github.situx.timtris.geometry;

import com.github.situx.timtris.gui.Paintable;

import java.awt.*;

/**
 * @author Timo
 *
 */
public class GeometricObject implements Paintable, MoveableObject {//Ein geometrisches Objekt
	public 	Vertex position;//Linke obere Ecke des Objektes
	public Vertex direction = new Vertex(0,10);//Bewerungsrichtung des geometrischen Objektes
	public int status=0;//Rotationsstatus des gemometrischen Objektes
	public int cstatus=1;//Hilfsvariable für den Farbstatus
	protected double height;//Höhe des Objectes
	protected double width;//Breite des Objektes
	protected Color c;//Farbe des geometrischen Objektes
	/**
	 * @param position the left upper edge of the GeometricObject
	 * @param height the height of the GeometricObject
	 * @param width the width of the GeometricObject
	 * @param c the color of the GeometricObject
	 * @param direction the direction in which the GeometricObject should be moved
	 */
	public GeometricObject(Vertex position,double height, double width,Color c,Vertex direction) {
		this.height = height;
		this.width = width;
		this.position = position;
		this.c=c;
		this.direction=direction;
	}

	public String toString() {//Ausgabe der Höhe und Breite des geometrischen Objektes
		return "Hoehe: " + height + " Weite:" + width;
	}

	/**Moves the position of the GeometricObject to the position of the given Vertex
	 * @param p the position to which the position hat to be moved
	 */
	void moveTo(Vertex p) {//Geometric Object zu einer bestimmten Position verschieben
		this.position = p;
	}

	/**Adds Vertex v to the current position of the GeometricObject
	 * @param v the Vertex to add to the current position
	 */
	void move(Vertex v) {//Position durch Addition verändern
		this.position.addmod(v);
	}
	
	/**Function to calculate the area of the GoemetricObject
	 * @return the area of the GeometricObject
	 */
	double area(){//Flächenberechnung des GeometricObject
		return height*width;	
	}
	
	/**Checks if the GeometricObject contains Vertex p
	 * @param p the Vertex to check if it is contained in the GeometricObject
	 * @return true if the Vertex is in the GeometricObject, false if not
	 */
	boolean hasWithin(Vertex p) {//Prüfung ob ein Punkt im GeoObject liegt
		if (p.x<=(position.x+width) && (p.x>=position.x)){
			if ((p.y<=(position.y+height)) && (p.y>=position.y))
				return true;
			return false;
		}
		return false;

	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object that) {//Prüfung ob ein Object gleich dem GeoObject ist
	 	if (that instanceof GeometricObject) {
			GeometricObject geo = (GeometricObject) that;
			return (geo.width == width && geo.height == height && position.equals(geo.position));
		}
		return false;
	}

	public Vertex getPosition() {
		return position;
	}

	/**Checks if the given GeometricObject touches another GeometricObject
	 * @param that the other GeometricObject
	 * @return true if the two GeometricObjects touch each other, false if not
	 */
	boolean touches(GeometricObject that)//Prüfung ob ein Object das GeoObject berührt
	{
		if ((this.position.x+this.width+this.direction.x)<(that.position.x))
			return false;
		if ((this.position.y+this.height+this.direction.y)<(that.position.y))
			return false;
		if ((this.position.y)>(that.position.y+that.height+that.direction.y))
			return false;
		if ((this.position.x)>(that.position.x+that.width+that.direction.x))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see de.fh_wiesbaden.informatik.thomb001.gui.Paintable#paintMeTo(java.awt.Graphics)
	 */
	public void paintMeTo(Graphics g) {//Zeichnen eines geometrischen Objektes
		g.setColor(c);
		g.fillRect((int)this.position.x, (int)this.position.y, (int)width, (int)height);
		g.setColor(Color.black);
		g.drawRect((int)this.position.x, (int)this.position.y, (int)width, (int)height);
	}

	public void move() {//Verschiebung um die Bewegungsrichtung des GeoObjects
		this.position.addmod(direction);
	}
}
