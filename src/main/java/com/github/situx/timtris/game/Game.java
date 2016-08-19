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
import com.github.situx.timtris.gui.Board;
import com.github.situx.timtris.gui.Menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Game{
	private static final long serialVersionUID = 1L;
	private MarioMod m = new MarioMod();
	/**Starts gameboard MarioMod Game
	 * @param gameboard the Board which should be used
	 */
	public void newmariogame(Board gameboard){/*Starts gameboard new MarioMod Game*/
		int i=(gameboard.getHeight()/20-1),j;
		int level=1;
		gameboard.netplay=false;//Netplay is not possible here
		gameboard.speed=200;//Speed is resetted
		gameboard.mod=true;//The mod is now active
		gameboard.hop=10;//The objects should move by 10 pixel now
		gameboard.mariostate=gameboard.marioc;//The counter is set to its default value
		gameboard.frame.setTitle(Menu.bundle.getString("mariomodgametitle"));//The window title is set
		for (Iterator <GeometricObject> it = gameboard.geometricObjects.iterator(); it.hasNext();){
			it.next();/*Deleting of all objects that may be present*/
			it.remove();
		}
		do {/*The array is overwritten with 0*/
			j=(gameboard.getWidth()/20);
			while (j>=0){
				gameboard.merke[i][j]=0;
				j--;
			}
			i--;
		} while (i>=0);
		gameboard.viren=6;//The first number of circles is set*/
		gameboard.vold=6;
		m.generateBlocks(gameboard.viren,gameboard);//The circles are generated*/
		gameboard.statusBar.setMessage(Menu.bundle.getString("leveldot")+level+" "+Menu.bundle.getString("remainingcircles")+gameboard.viren);/*The statusBar is set*/
		gameboard.layeredPane.setVisible(false);//The pause screen is set invisible
		gameboard.j.setText("<html><body bgcolor=#00008F><font size=16 color=red>"+Menu.bundle.getString("pause")+"</font></body></html>");
		gameboard.started=true;//The started flag is set*/
		gameboard.timer.start();/*The timer is started*/
	}
	/**Starts a new Tetris game (also in network mode if chosen)
	 * @param a the Board which should be used
	 * @param status indicates if a network game should be started
	 * @throws SocketException on network error
	 */
	public void newgame(Board a, int status, Socket client){
		int i=(a.getHeight()/20-1),j;
		try {
			if (a.netplay && a.server && a.started){//If a network connection is still opened we have to close it
				a.netplay=false;
				a.netw.client(2,a,null);
				a.netw.sinput.close();
				a.netw.soutput.close();
				a.netw.client.close();
				a.netw.server.close();
			}
			else if (a.netplay && status==0 && a.started){
				a.netplay=false;
				a.netw.client(2,a,null);
				a.netw.sinput.close();
				a.netw.coutput.close();
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		a.punkte=0;/*Resetting the points to 0 */
		a.hop=10;/*Resetting the space to move to 10*/
		a.net=0;//Setting the net value to 0
		a.speed=200;//Resetting the speed
		a.level=1;/*Resetting the level to 1*/
		a.grenze=100;/*Setting the new limit for the next level*/
		a.mod=false;/*Setting the flag for Tetris*/
		for (Iterator <GeometricObject> it = a.geometricObjects.iterator(); it.hasNext();){
			it.next();/*Deleting of the next object in the list*/
			it.remove();
		}
		do {/*Every position in the array should get the value 0 so that the game notices they are free*/
			j=(a.getWidth()/20);
			while (j>=0){
				a.merke[i][j]=0;
				j--;
			}
			i--;
		} while (i>=0);
		if (status==1)//If a network game as a server is started, the parameter 1 is given and the function server is called to establish a connection
			j=a.netw.server(a);
		if (client!=null){//The current client is given to the Board class for future use
			a.client=client;
		}
		a.layeredPane.setVisible(false);//Some settings for the Main Frame concerning the Pause screen
		a.j.setVisible(true);
		if (status!=1 || j==0){//If the status is not 1 or j==0 we can check if we have a network game or a single player game and set the score appropriate
			a.timer.start();/*the timer is started*/
			if (a.netplay){
				a.frame.setTitle(Menu.bundle.getString("langametitle"));//The title is set
				if (a.pointgame)//The statusBar is set
					a.statusBar.setMessage(Menu.bundle.getString("pointsdot")+a.punkte+" "+Menu.bundle.getString("leveldot")+a.level+" "+Menu.bundle.getString("limit")+a.networkgrenze+" "+Menu.bundle.getString("opponentdot")+a.opp);/*Ausgabe der Punkte und des Levels*/
				else if (a.timegame)
					a.statusBar.setMessage(Menu.bundle.getString("pointsdot")+a.punkte+" "+Menu.bundle.getString("leveldot")+a.level+" "+Menu.bundle.getString("opponentdot")+a.opp+" "+Menu.bundle.getString("timedots")+(a.timegrenze-a.time)/1000);
				else
					a.statusBar.setMessage(Menu.bundle.getString("pointsdot")+a.punkte+" "+Menu.bundle.getString("leveldot")+a.level+" "+Menu.bundle.getString("nextleveldot")+(a.grenze-a.punkte)+" "+Menu.bundle.getString("opponentdot")+a.opp);/*Ausgabe der Punkte und des Levels*/
				a.hop=20;//The amount of pixels is set
			}
			else{
				a.frame.setTitle(Menu.bundle.getString("singlegametitle"));
				a.statusBar.setMessage(Menu.bundle.getString("pointsdot")+a.punkte+" "+Menu.bundle.getString("leveldot")+a.level+" "+Menu.bundle.getString("nextleveldot")+(a.grenze-a.punkte));/*Ausgabe der Punkte und des Levels*/
			}
			a.j.setText("<html><body bgcolor=#00008F><font size=16 color=red>"+Menu.bundle.getString("pause")+"</font></body></html>");
			a.opp=0;//The opponent points are set to 0
			a.started=true;//The started flag is set
		}
		else{//If we don'timer have a connection but should play a network game, an error message is given out in the StatusBar
			a.timer.stop();//The timer is stopped
			a.statusBar.setMessage(Menu.bundle.getString("connectionnotpossible"));
			a.netplay=false;
		}
	}
	/**Function in which the end of the game is defined
	 * @param gameboard the Board which is used for the game
	 * @param win indicates if the game is won if it is gameboard network game
	 */
	public int over(Board gameboard,boolean win,Socket ugnclient){
		String s;
		if (gameboard.netplay && win && !gameboard.server)//If the opponent in the network game lost it, we have to send the token 3 to him
			gameboard.netw.client(3,gameboard,ugnclient);
		else if (gameboard.netplay && !win && !gameboard.server)//If gameboard network game is running we have to send the token "1" to the opponent so that he knows that he won the game
			gameboard.netw.client(1,gameboard,ugnclient);
		else if (gameboard.netplay && win && gameboard.server){
			if ((gameboard.opp!=gameboard.punkte && (gameboard.timegame || gameboard.pointgame)) || (!gameboard.timegame && !gameboard.pointgame))
				gameboard.netw.client(3,gameboard,null);
			else 
				gameboard.netw.client(1,gameboard,null);
		}
		else if (gameboard.netplay && !win && gameboard.server)
			gameboard.netw.client(1,gameboard,null);
		if (gameboard.netplay)//If we are playing gameboard network game we should now close all connections
			gameboard.netw.closeConnections(gameboard, ugnclient);
		gameboard.netplay=false;//The netplay flag is set to false because the network game is over
		if (gameboard.pointgame)//Refreshing the statusBar for the final screen
			gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+gameboard.punkte+" "+Menu.bundle.getString("leveldot")+gameboard.level+" "+Menu.bundle.getString("limit")+gameboard.networkgrenze+" "+Menu.bundle.getString("opponentdot")+gameboard.opp);/*Ausgabe der Punkte und des Levels*/
		else if (gameboard.timegame){
			if (gameboard.timegrenze-gameboard.time<0)
				gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+gameboard.punkte+" "+Menu.bundle.getString("leveldot")+gameboard.level+" "+Menu.bundle.getString("opponentdot")+gameboard.opp+" "+Menu.bundle.getString("timedots")+"0");
			else
				gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+gameboard.punkte+" "+Menu.bundle.getString("leveldot")+gameboard.level+" "+Menu.bundle.getString("opponentdot")+gameboard.opp+" "+Menu.bundle.getString("timedots")+(gameboard.timegrenze-gameboard.time)/1000);
		}
		else
			gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+gameboard.punkte+" "+Menu.bundle.getString("leveldot")+gameboard.level+" "+Menu.bundle.getString("nextleveldot")+(gameboard.grenze-gameboard.punkte)+" "+Menu.bundle.getString("opponentdot")+gameboard.opp);
		if (win && (gameboard.punkte>gameboard.opp || (!gameboard.pointgame && !gameboard.timegame) || (gameboard.timegrenze-gameboard.time>0 && gameboard.timegame) || gameboard.pointgame))//If we got the message that we won the network game gameboard message is given out on the label of the layeredpane in the main frame
			gameboard.j.setText("<html><body bgcolor=#00008F><font size=5 color=red>"+Menu.bundle.getString("youwonlan")+"</font></body></html>");
		else if (win && gameboard.punkte==gameboard.opp && gameboard.timegame)//In the timegame gameboard draw is also possible
			gameboard.j.setText("<html><body bgcolor=#00008F><font size=5 color=red>"+Menu.bundle.getString("draw")+"</font></body></html>");
		else{//Otherwise gameboard Game Over message is given out on the label of the layeredpane
			gameboard.j.setText("<html><body bgcolor=#00008F><font size=7 color=red>"+Menu.bundle.getString("gameover")+"</font></body></html>");
		}
		gameboard.layeredPane.setVisible(true);//the layeredPane is set visible
		gameboard.started=false;//the started flag is set to false
		gameboard.pointgame=false;//the game modes are set to false
		gameboard.timegame=false;
		gameboard.timer.stop();//the timer is stopped
		gameboard.frame.setTitle(Menu.bundle.getString("gametitle"));//the standard title is set
		if (gameboard.punkte>0 || gameboard.level>0){
			File f = new File("score.txt");//the current score is written to the scorefile if the necessary conditions are present
			try {
				FileWriter w = new FileWriter(f,true);
				SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy SS:mm");
				s=sd.format(new Date());
				if (gameboard.mod)//Depending on the mod or the tetris game the log is written to the file
					w.write(s+": MarioMod: Level:"+gameboard.level+"\n");
				else
					w.write(s+": "+gameboard.punkte+" Punkte Level:"+gameboard.level+"\n");
				w.close();
			} catch (Exception e) {//Otherwise an exception occurs an an error message is printed out on the console
				System.out.println("The Highscore could not be written properly to the file");
			}
		}
		return 1;
	}
}
