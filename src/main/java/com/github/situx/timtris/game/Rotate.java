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

package com.github.situx.timtris.game;
import com.github.situx.timtris.geometry.GeometricObject;
import com.github.situx.timtris.geometry.Vertex;

import java.util.List;
public class Rotate {
	
	/**
	 * Provides functionality for rotating the Tetris objects
	 * @param j the object id
	 * @param Liste the list of GeometricObjects
	 * @param width represents the width of the Board
	 */
	public void rotation(int j, List <GeometricObject>Liste, int width){
		Vertex v=new Vertex(0,0),y=new Vertex(0,0);
		int l=Liste.size()-1,i=l-3;//Only the last four objects are recognized because the rest is not moving any more
		switch (j){
		case 0:break;// If the figure is a square it does not rotate
		case 1:v=new Vertex(-20,20);//If the figure is an "I", there are two possibilities
			   if (Liste.get(i).status!=0){//Status 0: The figure has not been moved
				   v.x*=-1;				   //Oder Status 1: The figure has been moved
			   	   v.y*=-1;
				   Liste.get(i).status=0;
				   if (Liste.get(i).position.x+20>=width){/*If there is not enough room to rotate the figure, it has to be moved*/
				   	   Liste.get(i).position.x-=60;
				   	   y.x-=60;
				   }
				   else if (Liste.get(i).position.x+40>=width){/*If there is not enough room to rotate the figure, it has to be moved*/
				   	   Liste.get(i).position.x-=40;
				   	   y.x-=40;
				   }
				   else if (Liste.get(i).position.x+60>=width){/*Ist nicht genügend Platz um die Figur zu drehen muss sie erst verschoben werden*/
				   	   Liste.get(i).position.x-=20;
				   	   y.x-=20;
				   }
			   }
			   else
				   Liste.get(i).status=1;
			   // Depending on the status the appropriate vertex is chosen and the figure is rotated
			   y=y.add(v);
			   Liste.get(i+1).position.addmod(y);
			   y=y.add(v);
			   Liste.get(i+2).position.addmod(y);
			   y=y.add(v);
			   Liste.get(i+3).position.addmod(y);
			   break;
		case 2:	Liste.get(i).status++;//If the figure is an "L", there are 4 possibilities
				if (Liste.get(i).status>4)
					Liste.get(i).status=1;
				switch (Liste.get(i).status){//Setting the Vektor depending on the status
				case 1:	v=new Vertex(40,-20); 
						break;
				case 2:	v=new Vertex(-20,-40);
						if (Liste.get(i+2).position.x-20<=0){
							v.x+=20;
							Liste.get(i).position.x+=20;
							Liste.get(i+1).position.x+=20;
						}
						i+=2;
						break;
				case 3: v=new Vertex(-40,20); break;
				case 4: v=new Vertex(20,40);
						if (Liste.get(i+3).position.x+20>=width){
							v.x-=20;
							Liste.get(i).position.x-=20;
							Liste.get(i+1).position.x-=20;
						}
						i+=2;
				}
				Liste.get(i).position.addmod(v);//Adding the vertexes
				Liste.get(i+1).position.addmod(v);
				break;
		case 3: Liste.get(i).status++;//If the figure is an "L", there are 4 possibilities
				if (Liste.get(i).status>4)
					Liste.get(i).status=1;
				switch (Liste.get(i).status){
				case 1: v=new Vertex(-40,-20);Liste.get(i).position.addmod(v); i+=3; break; //Setting the Vektor depending on the status
				case 2: v=new Vertex(20,-40);
						if (Liste.get(i).position.x+40>=width){
							v.x-=20;
							Liste.get(i).position.x-=20;
							Liste.get(i+3).position.x-=20;
						}
						i++; 
						Liste.get(i+1).position.addmod(v);
						break;
				case 3: v=new Vertex(40,20); Liste.get(i).position.addmod(v); i+=3;  break;
				case 4: v=new Vertex(-20,40); 
						if (Liste.get(i+2).position.x-20<=0){
							v.x+=20;
							Liste.get(i).position.x+=20;
							Liste.get(i+3).position.x+=20;
						}
						i++; 
						Liste.get(i+1).position.addmod(v);
				}
				Liste.get(i).position.addmod(v);//Adding the vertexes
				break;
		case 4:v=new Vertex(20,-40);//If the figure is a "Z", there are two possibilities
		   	   if (Liste.get(i).status!=0){//Setting the Vektor depending on the status
		   		   v.x*=-1;
		   		   v.y*=-1;
		   		   Liste.get(i).status=0;
		   		   if (Liste.get(i).position.x-20<=0){
		   			   v.x+=20;
		   			   Liste.get(i).position.x+=20;
		   			   Liste.get(i+1).position.x+=20;
		   		   }
		       }
		   	   else
	   			   Liste.get(i).status=1;
		   	   Liste.get(i+2).position.addmod(v);//Adding the vertexes
		   	   v.y=0;
		   	   Liste.get(i+3).position.addmod(v);
		   	  break;
		case 5://IF the figure is a "T" there are 4 possibilities
			   i++;//The object in the middle does not have to be moved
			   Liste.get(i).status++;
			   if (Liste.get(i).status>4)
				 Liste.get(i).status=1;
			   switch (Liste.get(i).status){//Setting the Vektor depending on the status
			   case 1:v=new Vertex(40,-40);
			   		  Liste.get(i).position.addmod(v);
			   		  v=new Vertex(20,-20); 
			   		  break;
			   case 2:i++;
			   		  v=new Vertex(-20,-20);
			   		  if (Liste.get(i-2).position.x-40<=0){
				   		  Liste.get(i-2).position.x+=20;
				   		  Liste.get(i-1).position.x+=20;
				   		  Liste.get(i).position.x+=20;
				   		  Liste.get(i+1).position.x+=20;
				   	  }
			   		  Liste.get(i).position.addmod(v);
			   		  v.addmod(v);
			   		  break;
			   case 3:v=new Vertex(-40,40);
			   		  Liste.get(i).position.addmod(v);
			   		  v=new Vertex(-20,20);
			   		  break;
			   case 4:i++;
		   		  	  v=new Vertex(20,20);
				   	  if (Liste.get(i-2).position.x+40>=width){
				   		  Liste.get(i-2).position.x-=20;
				   		  Liste.get(i-1).position.x-=20;
				   		  Liste.get(i).position.x-=20;
				   		  Liste.get(i+1).position.x-=20;
				   	  }
			   		  Liste.get(i).position.addmod(v);
			   		  v.addmod(v);
			   }
			   Liste.get(i+1).position.addmod(v);//Adding the vertexes
			   break;
		case 6:v=new Vertex(-20,-40);//If the figure is a "Z", there are 2 possibilities
			   if (Liste.get(i).status!=0){//Setting the Vektor depending on the status
				   v.x*=-1;
				   v.y*=-1;
				   Liste.get(i).status=0;
				   if (Liste.get(i+1).position.x+40>=width){
					   v.x-=20;
				   	   Liste.get(i).position.x-=20;
				   	   Liste.get(i+1).position.x-=20;
				   }
			   }
			   else
				   Liste.get(i).status=1;
			   i+=2;
			   Liste.get(i).position.addmod(v);//adding the vertexes
			   v.y=0;
			   Liste.get(i+1).position.addmod(v);
		}
	}
}
