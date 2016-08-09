package com.github.situx.timtris.game;

import com.github.situx.timtris.geometry.GeometricObject;
import com.github.situx.timtris.gui.Board;

import java.net.Socket;
import java.util.Iterator;

public class Delete{
	private static final long serialVersionUID = 1L;
	private Game game = new Game();
	/** Function for deleting lines in the game
	 * @param j the start point of the last object in the list
	 * @param gameboard the Board the function has to deal with
	 * @return 0 if nothing has been deleted,
	 * 		   1 if something has been deleted
	 */
	public int delete(int j, Board gameboard, Socket ugnclient){//Function to check if deleting is necessary and to delete them
		GeometricObject geo;//GeometricObject as buffer for the list
		int temp,del=0,k;//Useful ints
		int[] i={0,0,0,0};//Array to calculate the deleting order
		do{
			temp=((int)(gameboard.geometricObjects.get(j).position.y)/20);
			if (temp>=gameboard.merke.length)
				temp=gameboard.merke.length-1;
			if (gameboard.merke[temp][0]>=gameboard.getWidth()/20){//If the value at position 0 in the Array is greater or equal the width of the board/20
					k=0;
					while (k<i.length){//the number of the row is written ordered into the array i
						if (temp==i[k])
							k=i.length;
						else if (temp>i[k]){
							switch (del-k){
							case 3: i[k+3]=i[k+2];
							case 2: i[k+2]=i[k+1];
							case 1: i[k+1]=i[k];
							default: i[k]=temp;
									 k=i.length;
									 if (del>0)
										 gameboard.punkte+=4;
									 del++;//del indicates how many rows are deleted
							}
						}
						else
							k++;
				}
			}
			j++;
			} while (j<gameboard.geometricObjects.size());
		if (del>0){/*If rows have to be deleted*/
			if (gameboard.netplay)//If gameboard network game is active, the current number of deleted rows is send to the opponent
				gameboard.netw.client((del+3),gameboard,ugnclient);
			for (Iterator <GeometricObject> it = gameboard.geometricObjects.iterator(); it.hasNext();){//For all objects do
				geo=it.next();
				temp=((int)(geo.position.y)/20);
				if (temp>=gameboard.merke.length)
					temp=gameboard.merke.length-1;
				if (gameboard.merke[temp][0]>=gameboard.getWidth()/20){/*If the object is in one of the rows that have to be deleted*/
					it.remove();/*Delete the object*/
					gameboard.punkte++;/*Increase the points by 1*/
				}
				else{/*All other objects are moved down*/
					if (((int)(geo.position.y)/20)<i[3])
						geo.position.y+=20;
					if (((int)(geo.position.y)/20)<i[2])
						geo.position.y+=20;
					if (((int)(geo.position.y)/20)<i[1])
						geo.position.y+=20;
					if (((int)(geo.position.y)/20)<=i[0])
						geo.position.y+=20;
					}
				}
			if (gameboard.punkte>gameboard.grenze){/*If the new points are greater than the limit for the next level*/
				gameboard.level++;/*The level is increased by 1*/
				gameboard.grenze+=(gameboard.grenze/2*3);/*The limit is increased*/
				if (gameboard.level==6 && gameboard.hw)/*If the level is 5 and the appropriate option is activated the extent of the objects by moving is increased*/
					gameboard.hop=20;
				else if (gameboard.speed>30)
					gameboard.speed-=30;/*Otherwise the speed is increased*/
			}
			if (gameboard.pointgame && gameboard.punkte>=gameboard.networkgrenze && gameboard.netplay){//If gameboard network pointgame is played we must check if we have already won
				gameboard.statusBar.setMessage("Punkte: "+gameboard.punkte+" Level: "+gameboard.level+" Grenze: "+gameboard.networkgrenze+" Gegner: "+gameboard.opp);/*Output of the points and the level*/
				game.over(gameboard,true,ugnclient);//the game.over function is called
			}
			if (!gameboard.mod && !gameboard.pointgame && !gameboard.timegame)//The statusbar is refreshed if we are playing gameboard regular game
				gameboard.statusBar.setMessage("Punkte: "+gameboard.punkte+" Level: "+gameboard.level+" Next Level: "+(gameboard.grenze-gameboard.punkte));
			while (del!=0){/*The appropriate entrys in the array are resetted to 0*/
			del--;
			k=i[del];
			while (k>=0){
				j=(gameboard.getWidth()/20);
				while (j>=0){
					if (k!=0)/*The always upper row is copied into the always lower row*/
						gameboard.merke[k][j]=gameboard.merke[k-1][j];
					else/*The first row is filled with 0*/
						gameboard.merke[k][j]=0;
					j--;
				}
				k--;
			}
			}
			return 1;//1 is returned if something has been deleted
		}
		return 0;//0 is returned if nothing has been deleted
	}
	/** Checks if an object is allowed to move down any further
	 * @param i the position of the object in the list
	 * @param a the Board of the game
	 * @return 0 if a check is not necessary, 1 if a check is completed successfully and the object is allowed to fall, 2 if the game ends
	 */
	public int test (int i, Board a, Socket ubgclient){
		int k=i,j=0;
		if (((int)(a.geometricObjects.get(i).position.y)%20)!=0)/*If the objects stands between two Arraypositions we don'timer have to check*/
			return 0;
			while (i<a.geometricObjects.size()){/*All object parts are considered*/
				if (((int)(a.geometricObjects.get(i).position.y)/20)>=(a.merke.length-1))/*Check if the object already is on the lowest position*/
					break;
				if (a.geometricObjects.get(i).position.y==0 && a.merke[((int)(a.geometricObjects.get(i).position.y)/20)][(((int)(a.geometricObjects.get(i).position.x)/20)+1)]!=0){
					if (a.server)
						game.over(a, false, null);
					else
						game.over(a,false,ubgclient);/*Check if the object ends the game*/
					return 2;
				}
				if(a.merke[(((int)(a.geometricObjects.get(i).position.y)/20)+1)][(((int)(a.geometricObjects.get(i).position.x)/20)+1)]!=0)
					i=a.geometricObjects.size();/*Check if there is another object under the current object*/
				else
					j++;
				if (j==(a.geometricObjects.size()-k))/*If there are no other objects under all current objects, they are allowed to fall*/
					return 0;
				i++;
			}
			i=k;
			do {/*If it can'timer fall any further, it is stopped and is registered in the array*/
				a.geometricObjects.get(i).direction.y=0;
				a.merke[((int)(a.geometricObjects.get(i).position.y)/20)][0]++;
				a.merke[((int)(a.geometricObjects.get(i).position.y)/20)][(((int)(a.geometricObjects.get(i).position.x)/20)+1)]=a.geometricObjects.get(i).cstatus;
			i++;
		} while (i<a.geometricObjects.size());
	return 1;
	}
}
