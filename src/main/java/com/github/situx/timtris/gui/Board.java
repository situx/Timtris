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

package com.github.situx.timtris.gui;

import com.github.situx.timtris.game.*;
import com.github.situx.timtris.geometry.Circle;
import com.github.situx.timtris.geometry.GeometricObject;
import com.github.situx.timtris.geometry.Makeo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Timo Homburg FH Wiesbaden University of Applied Sciences
 *
 */
public class Board extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	public List<GeometricObject> geometricObjects =new LinkedList<>();
	public int[][]merke;/*Array for remembering the positions of the objects*/
	public String add;/*String for remembering the IP-Address of gameboard possible netw player*/
	public int max;
	public int punkte;
	public int level;
	public int hop=10;
	public int speed=200;
	public int grenze;
	public int i=0;
	public int time=0;
	public  int net=0;
	public int viren=8;
	public int opp=0;
	public  int networkgrenze=0;
	public  int vold=0;
	public  int mariostate;/*Several ints to count Points*/
	public boolean mod=false;
	public boolean started=false;
	public boolean server=false;
	public boolean verschoben=true;
	public boolean timegame = false;
    public boolean pointgame = false;
	public boolean rot=true;
	public boolean rand=false;
    public boolean netplay = false;
	public boolean hw=false;
	public int marioc = 8;
    public int timegrenze = 0;
	public boolean juststarted=true;/*Booleans to indicate several states of the game*/
	/**
	 * JFrame for the game window
	 */
	public JFrame frame = new JFrame();/*JFrame for the game*/
	public JLabel j = new JLabel();//JLabel needed for informations*/
	public Socket client=null;/*Socket for netw games*/
	public JLayeredPane layeredPane;/*Layered Pane for the pause screen*/
	public StatusBar statusBar = new StatusBar();/*The statusbar that can be seen in the game*/
	public MarioMod ma = new MarioMod();
	public KeyMove key=new KeyMove();
	public Network netw = new Network();
	private int obj;
	private int ret=0;
	private boolean dstatus=false;
	private Board gameboard;/*Board for the game*/
	private Menu m = new Menu();/*Objects of the self-written classes*/
	private Delete delete = new Delete();
	private Game game = new Game();
	private Makeo make = new Makeo();
	/**
	 * The timer for synchronizing the events of the game
	 */
	public Timer timer = new Timer(speed,new ActionListener(){/*Timer zur Steuerung der Spielgeschwindigkeit*/
		public void actionPerformed(ActionEvent e){
			if (!mod){/*If the mod is not played*/
				if (moveObjects()!=0){/*Check if gameboard new object should be created*/
					if (netplay && started){/*If gameboard networkgame is run*/
							netw.getinput(server, gameboard,client);//We have to check if sth. is in the input
							System.out.println(net);
							if (net>0){/*If net>0 it is time to create new Lines in the game*/
								netw.addLine(net, gameboard,client);
								net=0;
							}
						if (pointgame && gameboard.opp>=networkgrenze)//If the opponent won, the game over function is called
								ret= game.over(gameboard, false, client);
							else if (netplay && pointgame)//Else we should refresh the statusBar
								gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+ gameboard.punkte+" "+Menu.bundle.getString("leveldot")+ gameboard.level+" "+Menu.bundle.getString("limit")+ gameboard.networkgrenze+" "+Menu.bundle.getString("opponentdot")+ gameboard.opp);/*Ausgabe der Punkte und des Levels*/
							else if (netplay)
							    gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+ gameboard.punkte+" "+Menu.bundle.getString("leveldot")+ gameboard.level+" "+Menu.bundle.getString("nextleveldot")+(gameboard.grenze- gameboard.punkte)+" "+Menu.bundle.getString("opponentdot")+ gameboard.opp);
					}
				dstatus=false;/*The status of the button "down" is set to enabled*/
				obj=make.generateObject(gameboard);//A new object is generated with the function generateObject
			    if (ret==0)
			    	moveObjects();//The object should be moved if appropriate
				}
			}
			else{//In the other case the MarioMod is active an we have to generate gameboard block
				if (moveObjects()!=0){
					ma.generatePills(max, gameboard);
					mariostate--;//After gameboard block is generated gameboard counter is decreased
					if (mariostate==0){//If it reaches 0 the speed of the timer is increased
						mariostate= marioc;
						if (speed>=20)
							speed-=20;
					}
					dstatus=false;//The status of the down key is set to false
					moveObjects();
				}
			}

			if (timegame && netplay){//If we are playing gameboard timegame
				time+=speed;//we have to update the time

				if (time>= timegrenze && opp>punkte && gameboard.server)//and check if the timelimit is reached and who won the game
					game.over(gameboard, false, client);
				else if (time>= timegrenze && gameboard.server)
					game.over(gameboard, true, client);
				if ((timegrenze -time)>=0)//If the time should fall lower than 0, gameboard zero should be drawn
					gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+ gameboard.punkte+" "+Menu.bundle.getString("leveldot")+ gameboard.level+" "+Menu.bundle.getString("opponentdot")+ gameboard.opp+" "+Menu.bundle.getString("timedot")+(timegrenze -time)/1000);
			}
		}
	});
	private Rotate rotate = new Rotate();

	public Board() {
		layeredPane = new JLayeredPane();
	}

	/**
	 * The Main function of the game
	 */
	public static void main(String[] args) {
		Board b = new Board();//A board is created
		b.build();//The board is filled and customized
		}

	/**
	 * Function for moving GeometricObjects and requesting checks
	 * @return 0 if the Object has not yet reached the bottom of the Board or if it has not touched another object
	 * 		   1 if the object cannot be moved any further and gameboard new object should be created
	 */
	private int moveObjects() {/*Function for the movement of the objects*/
		int i= geometricObjects.size()-4,ende;
		if (geometricObjects.size()>0 && !(geometricObjects.get(geometricObjects.size()-1) instanceof Circle)) {/*If the list is empty,1 is returned to create gameboard new figure*/
			if (mod)/*If the mod is active, there are only two objects to check*/
				i+=2;
			ende= delete.test(i, gameboard,client);/*The test function is called to check if the object can still be moved*/
			if (ende==0){/*if test returns 0 the object is allowed to move*/
				geometricObjects.get(i).move();
				geometricObjects.get(i+1).move();
				if (!mod){/*If the mod is not active there are 4 objects to move*/
					geometricObjects.get(i+2).move();
					geometricObjects.get(i+3).move();
				}
			}
			else{/*If another value is returned the delete function is called to explore if gameboard line can be deleted*/
				if (mod)
					ma.Mariodelete(gameboard);
				else
					delete.delete(i, gameboard,client);
			}
			repaint();//The board is repainted
			return ende;/*ende is returned and decides whether gameboard new object is created in the timer*/
		}
		return 1;//If nothing is yet created 1 is returned to indicate that gameboard new object should be created
	}

	/**
	 * Function for building the components of the JFrame
	 */
	public void build (){
		gameboard =this;
		setFocusable(true);
		setVisible(true);
		addKeyListener(this);
		setBackground(Color.black);
		j.setSize(200, 300);
		j.setLocation(100,120);
		j.setVisible(true);
		JMenuBar menuBar=m.menubar(this);
		frame.setPreferredSize(new Dimension(320,600));
		layeredPane.setPreferredSize(frame.getPreferredSize());
		layeredPane.setVisible(false);
		layeredPane.add(this.j);
		add(gameboard.layeredPane);
		frame.setResizable(false);
		frame.setTitle(Menu.bundle.getString("gametitle"));
		frame.setJMenuBar(menuBar);
		frame.add(statusBar, java.awt.BorderLayout.SOUTH);
		frame.add(this);
		frame.pack();
		merke=new int[((this.getHeight()/20))][((this.getWidth()/20)+1)];
		max=20*((this.getWidth()/20)/2)-40;
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g){/*Function for drawing the objects*/
		GeometricObject geo;
		super.paintComponent(g);
		if (timer.isRunning()){//The objects should only be drawn when the timer is active
			for (GeometricObject aListe : geometricObjects) {//Every object is painted in this for-each loop
				geo = aListe;
				geo.paintMeTo(g);
			}
		}
	}

	public void keyTyped(KeyEvent k) {
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent k) {//The keypressed function in which the different keys are connected to the specific functions
		if (started){
		switch (k.getKeyCode()){
		case KeyEvent.VK_UP: if (!mod)
								rotate.rotation(obj, geometricObjects, gameboard.getWidth());
							 else
								 ma.MarioRotate(geometricObjects,obj, gameboard);break;//Rotating of the current object
		case KeyEvent.VK_RIGHT:if (!mod)
								key.move(obj,false, geometricObjects,this.getWidth(),merke);
							   else
								 ma.MarioMove(geometricObjects,0, gameboard);break;//Moves the object right
		case KeyEvent.VK_DOWN: if (!dstatus)
									dstatus=key.down(gameboard); break;// the down button for moving the object to the nearest position at the bottom
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_P:if (net==-2) 
							 j.setText("<html><body bgcolor=#00008F><font size=16 color=red>"+Menu.bundle.getString("pause")+"</font></body></html>");
							if (this.timer.isRunning()){
								this.timer.stop();
								layeredPane.setVisible(true);
							} 
							else {
								layeredPane.setVisible(false);
								this.timer.start();
							} break;//Pauses the game
		case KeyEvent.VK_LEFT:if(!mod)//Moves the objects left
								key.move(obj,true, geometricObjects,this.getWidth(),merke);
							  else
								  ma.MarioMove(geometricObjects, 1, gameboard);
		}
	}
	}
		
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		
	}
}
