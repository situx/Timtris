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

import java.awt.*;


public class Circle extends GeometricObject {//Class for creating a circle
	private double radius;//the radius of the circle
	public Circle(Vertex position, double width, Color c, Vertex direction) {
		super(position,width,width,c,direction);
		this.radius=width/2;
	}
	@Override
	public String toString() {//printing out the radius + toString of the GeometricObject in the console
		return super.toString()+ " Radius: " + radius;
	}
	@Override
	double area() {//function to calculate the area of the circle
		return radius*radius*Math.PI; 
	}
	
	@Override
	public boolean equals(Object that) {//checks if two circles are equal
		if (that instanceof Circle) {
			Circle kreis = (Circle) that;
			return (kreis.width == width && kreis.height == height && position.equals(kreis.position));
		}
		return false;
	}
	@Override
	public void paintMeTo(Graphics g) {//Painting the circle as a GeometricObject
		g.setColor(c);
		g.fillOval((int)this.position.x, (int)this.position.y, (int)height, (int)width);
		g.setColor(Color.black);
		g.drawOval((int)this.position.x, (int)this.position.y, (int)height, (int)width);
	}
}
