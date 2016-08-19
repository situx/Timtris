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

import com.github.situx.timtris.geometry.Circle;
import com.github.situx.timtris.geometry.GeometricObject;
import com.github.situx.timtris.geometry.Vertex;
import com.github.situx.timtris.gui.Board;
import com.github.situx.timtris.gui.Menu;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MarioMod {
	private static final long serialVersionUID = 1L;

	/**
	 * This function creates the objects that can be seen in the mod
	 * 
	 * @param versatz
	 *            indicates where the objects are created
	 */
	public void generatePills(int versatz, Board a) {
		Random r = new Random();
		Color crechts, clinks;
		int frechts = ((Math.abs(r.nextInt())) % 3) + 1, flinks = ((Math.abs(r
				.nextInt())) % 3) + 1;
		switch (frechts) {// Randomly selected color for the right square
		default:
			crechts = Color.yellow;
			break;
		case 2:
			crechts = Color.blue;
			break;
		case 3:
			crechts = Color.red;
		}
		r = new Random();
		switch (flinks) {// Randomly selected color for the left square
		default:
			clinks = Color.yellow;
			break;
		case 2:
			clinks = Color.blue;
			break;
		case 3:
			clinks = Color.red;
		}
		a.geometricObjects.add(new GeometricObject(new Vertex(versatz, 0), 19, 19, clinks,new Vertex(0, a.hop))); // The object is registered in the array
		a.geometricObjects.get(a.geometricObjects.size() - 1).cstatus = flinks;//The color status is set in the object
		a.geometricObjects.add(new GeometricObject(new Vertex(versatz + 20, 0), 19, 19,
				crechts, new Vertex(0, a.hop)));
		a.geometricObjects.get(a.geometricObjects.size() - 1).cstatus = frechts;
	}

	/**
	 * Draws the circles that need to be eliminated in the mod game
	 * 
	 * @param anzahl
	 *            the amount of circles to draw in the mod game
	 */
	protected void generateBlocks(int anzahl, Board gameboard) {// Function for generating
														// the blocks
		Color c;
		int i = 0, farbe, apox, apoy;
		Random r = new Random();
		while (i < anzahl) {// Number of the generated blocks
			farbe = (Math.abs(r.nextInt())) % 3;
			switch (farbe) {// The color is chosen
			default:
				c = Color.yellow;
				break;
			case 1:
				c = Color.blue;
				break;
			case 2:
				c = Color.red;
			}
			r = new Random();// Randomly selected position x
			apox = (Math.abs(r.nextInt())) % (gameboard.getWidth() / 20);
			apox *= 20;
			r = new Random();// Randomly selected position y
			apoy = (Math.abs(r.nextInt())) % (gameboard.merke.length - 8) + 8;
			apoy *= 20;
			gameboard.geometricObjects.add(new Circle(new Vertex(apox, apoy), 19, c, new Vertex(0,
					0)));// Add one circle
			switch (farbe) {// Paste the color in the array
			default:
				gameboard.merke[apoy / 20][apox / 20 + 1] = 1;
				break;
			case 1:
				gameboard.merke[apoy / 20][apox / 20 + 1] = 2;
				break;
			case 2:
				gameboard.merke[apoy / 20][apox / 20 + 1] = 3;
			}
			i++;
		}
	}

	/**
	 * The Rotate Function for the mod game
	 * 
	 * @param Liste
	 *            the List of Geometric Objects
	 * @param j
	 */
	public void MarioRotate(List<GeometricObject> Liste, int j, Board gameboard) {//Rotates the objects
		Vertex v;
		int l = Liste.size() - 1, i = l - 1;
		switch (Liste.get(i).status) {// Only one figure with four states exists
		case 0:
			v = new Vertex(-20, 20);
			Liste.get(l).getPosition().addmod(v);
			break;
		case 1:
			v = new Vertex(-20, -20);
			Liste.get(l).getPosition().addmod(v);
			break;
		case 2:
			v = new Vertex(20, -20);
			Liste.get(l).getPosition().addmod(v);
			break;
		case 3:
			v = new Vertex(20, 20);
			Liste.get(l).getPosition().addmod(v);
		}
		Liste.get(i).status++;
		if (Liste.get(i).status > 3)
			Liste.get(i).status = 0;
		// If the figure reaches through the right border
		if (Liste.get(i).getPosition().getX()/* +20 */>= gameboard.getWidth()
				|| Liste.get(i + 1).getPosition().getX()/* +20 */>= gameboard.getWidth()) {// Move it 20 pixel to the left
			Liste.get(i).getPosition().x -= 20;
			Liste.get(i + 1).getPosition().x -= 20;
		}
		// If the figure reaches through the left border
		if (Liste.get(i).getPosition().x/*-20*/<= 0
				|| Liste.get(i + 1).getPosition().x/*-20*/<= 0) {// Move it 20 pixel to the right
			Liste.get(i).getPosition().x += 20;
			Liste.get(i + 1).getPosition().x += 20;
		}
	}

	/**
	 * The move function for the mod game - moves the objects left or right
	 * 
	 * @param Liste
	 *            the List of GeometricObjects
	 * @param lr
	 *            indicates if we are moving left or right
	 * @return 0
	 */
	public int MarioMove(List<GeometricObject> Liste, int lr, Board a) {//Movement to the left an to the right
		Vertex v = new Vertex(20, 0);
		int i = Liste.size() - 2;
		if (lr == 1)
			v.x *= -1;
		if (Liste.get(i).getPosition().getX() + 40 >= a.getWidth() && lr == 0
				&& Liste.get(i).status == 0)// If the movement reaches through the right border, cancel it
			return 0;
		if (Liste.get(i).getPosition().getX() + 20 >= a.getWidth() && lr == 0
				&& Liste.get(i).status != 0)// If the movement reaches through the right border, cancel it
			return 0;
		if (Liste.get(i).getPosition().getX() - 20 < 0 && lr == 1
				&& Liste.get(i).status != 2)// If the movement reaches through the left border, cancel it
			return 0;
		if (Liste.get(i).getPosition().getX() - 40 < 0 && lr == 1
				&& (Liste.get(i).status == 2))// If the movement reaches through the left border, cancel it
			return 0;
		Liste.get(i).getPosition().addmod(v);// The movement is added
		Liste.get(i + 1).getPosition().addmod(v);
		return 0;
	}

	/**
	 * Function for deleting the lines in the mario mod game
	 * 
	 * @param gameboard
	 *            the Board of the game
	 * @return 0
	 */
	public int Mariodelete(Board gameboard) {// Function for deleting the circles and/or blocks
		int i = gameboard.geometricObjects.size() - 2, j, gleich;
		boolean del = false;
		GeometricObject geo;
		int[] delete = { 0, 0, 0, 0 };
		int x, y;

		do {//For all objects do
			j = 0;
			while (j < delete.length) {//Resetting the array
				delete[j] = 0;
				j++;
			}
			y = (int) (gameboard.geometricObjects.get(i).position.y) / 20;//Setting the positions
			x = (int) (gameboard.geometricObjects.get(i).position.x) / 20 + 1;
			gleich = 0;
			if (gameboard.merke[y][x] != 0) {
				j = 1;
				while ((y + j) < gameboard.merke.length) {// Check if there are more objects under the current object
					if (gameboard.merke[y][x] == gameboard.merke[y + j][x]) {
						gleich++;
					} else
						break;
					j++;
				}
				delete[0] = gleich;
				j = 1;
				gleich = 0;
				while ((y - j) >= 0) {// Check if there are more objects above the current object
					if (gameboard.merke[y][x] == gameboard.merke[y - j][x])
						gleich++;
					else
						break;
					j++;
				}
				delete[1] = gleich;
				j = 1;
				gleich = delete[0] + delete[1];
				if ((delete[3] + delete[2]) >= 3 || gleich >= 3) {// If there are four equal objects
					del = !del;
					for (Iterator<GeometricObject> it = gameboard.geometricObjects.iterator(); it.hasNext();) {// Delete them
						geo = it.next();
						j = 0;
						if (gleich >= 3) {
							while (j <= delete[0]) {
								if (geo.getPosition().y / 20 == y + j
										&& geo.getPosition().x / 20 + 1 == x) {
									if (geo instanceof Circle)
										gameboard.viren--;
									gameboard.merke[(int) (geo.getPosition().y) / 20][((int) (geo.getPosition().x) / 20) + 1] = 0;
									it.remove();
								}
								j++;
							}
							j = 0;
							while (j < delete[1]) {
								if (geo.getPosition().y / 20 == y - j
										&& geo.getPosition().x / 20 + 1 == x) {
									if (geo instanceof Circle)
										gameboard.viren--;
									gameboard.merke[(int) (geo.getPosition().y) / 20][((int) (geo.getPosition().x) / 20) + 1] = 0;
									it.remove();
								}
								j++;
							}
						}

					}
				}
			}
			i++;
		} while (i < gameboard.geometricObjects.size());
		if (gameboard.viren == 0) {//If we don'timer have any circles anymore
			gameboard.level++;//Increase the level
			gameboard.viren = gameboard.vold + 2;//Increase the number of circles
			gameboard.vold += 2;
			switch (gameboard.level) {//If gameboard certain level is reached, the speed is adjusted
			case 1:
			case 2:
				gameboard.speed = 200;
				break;
			case 3:
			case 4:
				gameboard.speed = 160;
				break;
			case 5:
			case 6:
				gameboard.speed = 120;
				break;
			default:
				gameboard.hop = 20;
				gameboard.speed = 200;
			}
			i = (gameboard.getHeight() / 20 - 1);
			for (Iterator<GeometricObject> it = gameboard.geometricObjects.iterator(); it
					.hasNext();) {
				it.next();/* Deleting of all objects that are present */
				it.remove();
			}
			do {/*
				 * The array is filled with 0
				 */
				j = (gameboard.getWidth() / 20);
				while (j >= 0) {
					gameboard.merke[i][j] = 0;
					j--;
				}
				i--;
			} while (i >= 0);
			generateBlocks(gameboard.viren, gameboard);//new circles are generated
		}
		gameboard.statusBar.setMessage(Menu.bundle.getString("leveldot") + gameboard.level + Menu.bundle.getString("remainingcircles")+ gameboard.viren);//The statusBar is refreshed
		return 0;

	}

}
