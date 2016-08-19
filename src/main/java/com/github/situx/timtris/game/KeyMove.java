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
import com.github.situx.timtris.gui.Board;

import java.util.List;

public class KeyMove{
private static final long serialVersionUID = 1L;

/**
 * Function for moving the Objects left or right
 * @param j contains the number of the Object to move
 * @param lr indicates the direction in which the object is moved
 * @param Liste the List of Objects
 * @param width The width of the current object
 * @return 0 if the function has ended
 */
public int move(int j, boolean lr, List<GeometricObject> Liste, int width, int[][] merke){
		int l=Liste.size(),i=l-4;/*l is the size of the Objectlist, i is set to the first Element of the Last figure in the List*/
		Vertex v=new Vertex(20,0);/*Vertex for moving the object*/
		if (lr)/*Checks in which direction the object should be moved*/
			v.x*=-1;
		switch (j){/*Check which object has to be moved*/
		case 0:if ((Liste.get(i).position.x+v.x*2)>=width || (Liste.get(i).position.x+v.x)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
					return 0;
		       break;
		case 1:if (((Liste.get(l-1).position.x+v.x)>=width) || (Liste.get(i).position.x+v.x)<0)
					return 0;
			    break;
		case 2: switch (Liste.get(i).status){
				default:if (((Liste.get(i).position.x+v.x*3)>=width) || ((Liste.get(i).position.x+v.x)<0) || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
						return 0; break;
				case 1:if ((Liste.get(i).position.x+v.x)>=width || (Liste.get(i).position.x+v.x*2)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0; break;
				case 2:if ((Liste.get(i).position.x+v.x)>=width || (Liste.get(i).position.x+v.x*3)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0; break;
				case 3:if ((Liste.get(i).position.x+v.x*2)>=width || (Liste.get(i).position.x+v.x)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0;
				}
				break;
		case 3: switch (Liste.get(i).status){
				default:if (((Liste.get(i).position.x+v.x)>=width) || ((Liste.get(i).position.x+v.x*3)<0) || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0; break;
				case 1:if ((Liste.get(i).position.x+v.x*2)>=width || (Liste.get(i).position.x+v.x)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0; break;
				case 2:if ((Liste.get(i).position.x+v.x*3)>=width || (Liste.get(i).position.x+v.x)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0; break;
				case 3:if ((Liste.get(i).position.x+v.x)>=width || (Liste.get(i).position.x+v.x*2)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0;
				}
				break;
		case 4: if (Liste.get(i).status==0){
					if (((Liste.get(i).position.x+v.x*2)>=width) || ((Liste.get(i).position.x+v.x*2)<0) || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
						return 0;
					break;
					}
				else{
					if (((Liste.get(i).position.x+v.x*2)>=width) || ((Liste.get(i).position.x+v.x)<0) || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
						return 0;
					break;
				}
		case 5:	switch (Liste.get(i+1).status){
				default:if ((Liste.get(i).position.x+v.x*2>=width) || (Liste.get(i).position.x+v.x*2)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
						return 0; break;
				case 1: if ((Liste.get(i).position.x+v.x*2>=width) || (Liste.get(i).position.x+v.x)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1) 
						return 0;break;
				case 2:	if ((Liste.get(i).position.x+v.x*2>=width) || (Liste.get(i).position.x+v.x*2)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
						return 0; break;
				case 3: if ((Liste.get(i).position.x+v.x>=width) || (Liste.get(i).position.x+v.x*2)<0 || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
						return 0; break;	
	       		}
				break;
		case 6:if (((Liste.get(i+2).position.x+v.x)>=width) || (((Liste.get(i).position.x)<=0)&& lr) || merke[((int)(Liste.get(i).position.y)/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1 || merke[((int)(Liste.get(l-1).position.y)/20)][(((int)(Liste.get(l-1).position.x+v.x)/20)+1)]==1)
				return 0;
		}
		do{
			   if (merke[(int)(Liste.get(i).position.y/20)][(((int)(Liste.get(i).position.x+v.x)/20)+1)]==1)
			   	return 0;
			   i++;
		}while (i<l);
		i=l-4;
		Liste.get(i).position.addmod(v);
		Liste.get(i+1).position.addmod(v);
		Liste.get(i+2).position.addmod(v);
		Liste.get(i+3).position.addmod(v);
		return 0;
	}
/**Moves the figure as far to the bottom as possible at the current position
 * @param gameboard The currently used Board
 * @return true when the function has ended
 */
public boolean down(Board gameboard){//Moves the figure to the lowest position possible
	int	l,i=gameboard.geometricObjects.size()-4;
	Game g = new Game();
	int x,y=0;
	if (gameboard.mod)//If we are playing the mod we have only to objects to deal with
		i+=2;
	l=i;
	if (gameboard.geometricObjects.get(i).position.y==0 && gameboard.merke[((int)(gameboard.geometricObjects.get(i).position.y)/20)][(((int)(gameboard.geometricObjects.get(i).position.x)/20)+1)]==1){//Checks if the object ends the game if moved
		g.over(gameboard,false,gameboard.client);
	}
	do{//Checks when an object is not allowed to fall any more
		x=0;
		while(((int)(gameboard.geometricObjects.get(i).position.y+20*x)/20)<gameboard.merke.length)//While the current position of the object + x*20 is smaller than the length of the array
		{
			if ((gameboard.merke[((int)(gameboard.geometricObjects.get(i).position.y+20*x)/20)][(((int)(gameboard.geometricObjects.get(i).position.x)/20)+1)]==0))//Check if there is something under the object
				x++;//If yes we have to check 20 pixel lower and increase x
			else
				break;//Otherwise quit the loop
		}
		if (x<y || i==l)//If the first object is checked or the x is smaller than gameboard aforementioned x
			y=x;//y should be x to achieve the lowest value possible
	i++;
	} while (i<gameboard.geometricObjects.size());//Check till all objects have been checked
	y-=2;
	if (!gameboard.mod){
	gameboard.geometricObjects.get(i-4).position.y+=y*20;//Move the object y*20 pixel down
	gameboard.geometricObjects.get(i-3).position.y+=y*20;
	}
	gameboard.geometricObjects.get(i-2).position.y+=y*20;
	gameboard.geometricObjects.get(i-1).position.y+=y*20;
	return true;
}
}
