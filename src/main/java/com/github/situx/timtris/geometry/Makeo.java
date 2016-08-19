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

import com.github.situx.timtris.game.KeyMove;
import com.github.situx.timtris.game.Rotate;
import com.github.situx.timtris.gui.Board;

import java.awt.*;
import java.util.Random;

public class Makeo {
	private Rotate rotate = new Rotate();
	private KeyMove key = new KeyMove();
	/**
	 * Creates the movable Tetris Objects which can be seen in the game
	 * @param gameboard The Board given with which the function has to deal with
	 * @return the object id (0-6)
	 */
	public int generateObject(Board gameboard){
		Random random=new Random();
		int i=(Math.abs(random.nextInt()))%7,j,versatz=gameboard.max;
		if (gameboard.level>1 && !gameboard.netplay && gameboard.verschoben){//If the level is greater than 1 it is possible that the figure will be deplaced
			switch((Math.abs(random.nextInt()))%5){//Randomly generated displacement
				case 1: versatz=0;break;
				case 2: versatz-=((gameboard.getWidth()/20)/3)*20;break;
				case 3: versatz+=((gameboard.getWidth()/20)/3)*20;break;
				case 4: if(i==0) versatz=gameboard.getWidth()-40; else if(i==1) versatz=gameboard.getWidth()-80; else versatz=gameboard.getWidth()-60;
				default:
			}
		}
		j=(Math.abs(random.nextInt()))%(10-gameboard.level);//A new random number is generated
		if (gameboard.level>4 && !gameboard.netplay && gameboard.rand){//If the level is greater than 4
			if (j==2)//A new line may be added
				gameboard.netw.addLine(1,gameboard,null);
			if (j==3){//A MarioMod figure may be thrown into the Board
				gameboard.ma.generatePills(versatz,gameboard);
				gameboard.mod=true;
				gameboard.key.down(gameboard);
				gameboard.geometricObjects.get(gameboard.geometricObjects.size()-2).position.y+=20;
				gameboard.geometricObjects.get(gameboard.geometricObjects.size()-1).position.y+=20;
				gameboard.merke[(int)gameboard.geometricObjects.get(gameboard.geometricObjects.size()-2).position.y/20][(int)gameboard.geometricObjects.get(gameboard.geometricObjects.size()-2).position.x/20+1]=1;
				gameboard.merke[(int)gameboard.geometricObjects.get(gameboard.geometricObjects.size()-1).position.y/20][(int)gameboard.geometricObjects.get(gameboard.geometricObjects.size()-1).position.x/20+1]=1;
				gameboard.mod=false;
			}
		}
		switch (i){//A randomly selected figure will be created
		case 0:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,0),19,19,Color.yellow,new Vertex(0,gameboard.hop)));	//Je nach Fall Eintrag in die geometricObjects der Objekte
		   	   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,20),19,19,Color.yellow,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,0),19,19,Color.yellow,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,20),19,19,Color.yellow,new Vertex(0,gameboard.hop)));
			   break;
		case 1:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,0),19,19,Color.cyan,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,0),19,19,Color.cyan,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,0),19,19,Color.cyan,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+60,0),19,19,Color.cyan,new Vertex(0,gameboard.hop)));
			   break;
		case 2:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,0),19,19,Color.blue,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,20),19,19,Color.blue,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,20),19,19,Color.blue,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,20),19,19,Color.blue,new Vertex(0,gameboard.hop)));
			   break;
		case 3:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,0),19,19,Color.orange,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,20),19,19,Color.orange,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,20),19,19,Color.orange,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,20),19,19,Color.orange,new Vertex(0,gameboard.hop)));
			   break;
		case 4:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,0),19,19,Color.green,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,0),19,19,Color.green,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,20),19,19,Color.green,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,20),19,19,Color.green,new Vertex(0,gameboard.hop)));
			   break;
		case 5:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,0),19,19,Color.magenta,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,20),19,19,Color.magenta,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,20),19,19,Color.magenta,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,20),19,19,Color.magenta,new Vertex(0,gameboard.hop)));
			   break;
		case 6:gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz,0),19,19,Color.red,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,0),19,19,Color.red,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+40,20),19,19,Color.red,new Vertex(0,gameboard.hop)));
			   gameboard.geometricObjects.add(new GeometricObject(new Vertex(versatz+20,20),19,19,Color.red,new Vertex(0,gameboard.hop)));
		}
		if (gameboard.level>3 && !gameboard.netplay && gameboard.rand){//If the level is greater than 3 a random event is generated
			switch(j){
			case 0:	j=(Math.abs(random.nextInt()))%gameboard.geometricObjects.size(); gameboard.merke[(int)gameboard.geometricObjects.get(j).position.y/20][(int)gameboard.geometricObjects.get(j).position.x/20+1]=0;gameboard.geometricObjects.remove(j);break;
			case 1: key.down(gameboard);
			default:
			}
		}
		if (gameboard.level>2 && !gameboard.netplay && gameboard.rot){//If the level is greater than 2 it is possible that the figure will be rotate several times after creating
		j=(Math.abs(random.nextInt()))%4;
		while (j>=0){
			rotate.rotation(i,gameboard.geometricObjects,gameboard.getWidth());
			j--;
		}
		}
	return i;//The number of the created figure is returned
	}
}
