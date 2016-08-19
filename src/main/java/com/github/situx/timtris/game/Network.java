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
import com.github.situx.timtris.gui.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
/**
 * @author Timo
 *
 */
public class Network {
	private static final long serialVersionUID = 1L;
	private final JRadioButton check = new JRadioButton();
	private final JRadioButton check2 = new JRadioButton();
	private final JRadioButton check3 = new JRadioButton();
	protected ServerSocket server;
	protected Socket client=null;
	InputStream sinput;
	OutputStream soutput;
	OutputStream coutput;
	InputStream cinput;
	JPanel ippanel = new JPanel();
	JPanel w = new JPanel();
	ButtonGroup bg = new ButtonGroup();
	private Board gameboard;
	private Socket realclient=null;
	private Game game = new Game();
	private JFrame netgame=new JFrame();
	private JFrame waiting = new JFrame();
	private boolean sc=false;
	private JButton ok=new JButton(Menu.bundle.getString("ok"));
	private JPanel radiopanel = new JPanel();
	private JLabel ip=new JLabel();
	private JLabel text = new JLabel();
	private JLabel wait = new JLabel();
	private JTextField adresse= new JTextField();
	private JPanel buttonPanel=new JPanel();
	private Timer s = new Timer(200,new ActionListener(){//Timer for the server game start
		public void actionPerformed(ActionEvent e){
			if (check.isSelected()){//Default tetris game start
				game.newgame(gameboard,1,null);//starting the game
				gameboard.timegame=false;
				gameboard.pointgame=false;
				netgame.setVisible(false);
			}
			else if (check2.isSelected()){//tetris pointgame start
				gameboard.pointgame=true;
				gameboard.timegame=false;
			try{
				gameboard.networkgrenze=new Integer(adresse.getText());//reading the pointlimit
				game.newgame(gameboard,1,null);//starting the game
				netgame.setVisible(false);
			}catch(NumberFormatException e1) {
				System.out.println(Menu.bundle.getString("wronginput"));
				netgame.setVisible(true);
			}
			}
			else{
				gameboard.timegame=true;
				gameboard.pointgame=false;
				gameboard.time=0;
			try{
				gameboard.timegrenze=60000*(new Integer(adresse.getText()));//reading the timelimit
				game.newgame(gameboard,1,null);//starting the game
				netgame.setVisible(false);
			}catch(NumberFormatException e1) {
				System.out.println(Menu.bundle.getString("wronginput"));
			}
		}
		waiting.setVisible(false);
		s.stop();//stopping the timer because it should only be executed once	
		}
	});
	private ActionListener act=new ActionListener(){//ActionListener for starting the client game
		public void actionPerformed(ActionEvent e){
			String erg;
			erg=newnetgame(gameboard);//calling the newnetgame function
			if (erg!=null)//If null is not returned an error message is displayed
				ip.setText(erg);
			else{
				game.newgame(gameboard,2,realclient);
				netgame.setVisible(false);
			}
		}
	};
	private ActionListener act10= e -> {
        waiting.setVisible(true);
        s.start();
        };
	/**
	 * Builds the necessary JFrame for the network game
	 */
	public void build(Board a){//builds the network frame used in the menu screens
		this.gameboard =a;
		if (!sc){
		JButton ab =new JButton(Menu.bundle.getString("cancel"));
		ActionListener act2= e -> netgame.setVisible(false);
		ab.addActionListener(act2);
		buttonPanel.setSize(150,20);
		buttonPanel.add(ok);
		buttonPanel.add(ab);
		buttonPanel.setVisible(true);
		check.setEnabled(true);
		check.setText(Menu.bundle.getString("defaultgame"));
		check2.setEnabled(true);
		check2.setText(Menu.bundle.getString("pointgame"));
		check3.setEnabled(true);
		check3.setText(Menu.bundle.getString("timegame"));
		ActionListener act11= e -> {//ActionListener for the server menu selection
            check2.setSelected(false);
            adresse.setVisible(false);
            text.setText("");
            check3.setSelected(false);
        };
		ActionListener act12= e -> {
            check.setSelected(false);
            adresse.setVisible(true);
            text.setText(Menu.bundle.getString("pointlimit"));
            check3.setSelected(false);
        };
		ActionListener act13= e -> {
            check.setSelected(false);
            adresse.setVisible(true);
            text.setText(Menu.bundle.getString("timelimit"));
            check2.setSelected(false);
        };
		check.addActionListener(act11);
		check2.addActionListener(act12);
		check3.addActionListener(act13);
		ok.setSize(50,20);
		ab.setSize(50,20);
		adresse.setPreferredSize(new Dimension(140,22));
		ip.setPreferredSize(new Dimension(200,50));
		ip.setText(Menu.bundle.getString("inputipplayer"));
		ip.setVisible(true);
		JPanel mitte = new JPanel();
		mitte.add(text);
		mitte.add(adresse);
		wait.setText(Menu.bundle.getString("waitforconnection"));
		wait.setSize(new Dimension(200,25));
		wait.setVisible(true);
		waiting.setSize(new Dimension(350,150));
		waiting.setResizable(false);
		waiting.setUndecorated(true);
		waiting.setLocation(450, 250);
		waiting.setVisible(false);
		waiting.add(wait,java.awt.BorderLayout.CENTER);
		netgame.add(mitte,java.awt.BorderLayout.CENTER);
		netgame.add(buttonPanel,java.awt.BorderLayout.SOUTH);
		netgame.add(radiopanel,java.awt.BorderLayout.NORTH);
		netgame.setResizable(false);
		sc=true;
		}
		netgame.pack();
	}
	/**Builds the server frame
	 * 
	 */
	public void serverbuild(){//As the frame is used as server and client window, this is the function to build the server window
		text.setText("");
		adresse.setText("");
		check.setSelected(true);
		check2.setSelected(false);
		check3.setSelected(false);
		netgame.setSize(490,126);
		netgame.setTitle(Menu.bundle.getString("serverlangame"));
		radiopanel.removeAll();
		radiopanel.add(check);
		radiopanel.add(check2);
		radiopanel.add(check3);
		adresse.setVisible(false);
		ok.removeActionListener(act);
		ok.addActionListener(act10);
		radiopanel.setVisible(true);
		netgame.setVisible(true);
	}
	/**Builds the client frame
	 * 
	 */
	public void clientbuild(){//As the frame is used as server and client window, this is the function to build the client window
		netgame.setSize(220,155);
		adresse.setText("");
		ip.setText("<html>Bitte geben sie die IP-Adresse des Mitspielers ein:</html>");
		netgame.setTitle("LAN-Spiel");
		adresse.setVisible(true);
		ok.removeActionListener(act10);
		ok.addActionListener(act);
		radiopanel.removeAll();
		radiopanel.add(ip);
		text.setText("");
		netgame.setVisible(true);
	}
	/** The client function for the network game
	 * 	 * @param gameboard the game board
	 * @return gameboard text string which can be seen in the windows if no connection has been established
	 * 		   null if the connection has been established
	 */
	private String newnetgame(Board gameboard){//is started if gameboard new Client Game is selected from the menu
		int i=0;
		gameboard.add=adresse.getText();//the ip address is read from the textfield and stored
		try {
			realclient = new Socket(gameboard.add,3002);
			sinput=realclient.getInputStream();
			i=sinput.read();//reading the game mode
			if (i==2){//If the mode is 2 the pointlimit must be read in the next 2 bytes
				gameboard.pointgame=true;
				gameboard.timegame=false;
				gameboard.networkgrenze=sinput.read();
				gameboard.networkgrenze*=244;
				gameboard.networkgrenze+=sinput.read();
			}
			else if (i==3){//If the mode is 3 the timelimit must be read in the next 2 bytes
				gameboard.timegame=true;
				gameboard.pointgame=false;
				gameboard.timegrenze=sinput.read();
				gameboard.timegrenze*=244;
				gameboard.timegrenze+=sinput.read();
				gameboard.timegrenze*=60000;
				gameboard.time=0;
			}
			else{//If we have gameboard normal game the other flags have to be set to false
				gameboard.pointgame=false;
				gameboard.timegame=false;
			}
			gameboard.netplay=true;
			gameboard.server=false;
		} catch (UnknownHostException e1) {
			return("<html>Unbekannter Host!<br>Bitte wiederholen Sie ihre Eingabe:</html>");
		} catch (IOException e1) {
			return("<html>Ungültige IP-Adresse!<br>Bitte wiederholen Sie ihre Eingabe:</html>");
		}
		return null;
	}
	/**Function to establish gameboard ServerSocket for the network game
	 * @param gameboard the game board
	 * @return 1 if successful, 0 if not successful
	 */
	public int server(Board gameboard) {//If gameboard server game is selected this function is called
		try {
			server = new ServerSocket(3002);//creating the serversocket
			server.setSoTimeout(25000);//timeout in case nobody is connecting
			client = server.accept();//waiting for someone to connect
			sinput = client.getInputStream();//Inputstream if gameboard connection was successfully established
			if (!gameboard.pointgame && !gameboard.timegame)//If we have gameboard default game gameboard 1 is sent to the client
				client(1,gameboard,client);
			else if (gameboard.pointgame){//If we have gameboard pointgame 2 and the pointlimit is sent to the client
				client(2,gameboard,client);
				client(gameboard.networkgrenze/244,gameboard,client);
				client(gameboard.networkgrenze%244,gameboard,client);
			}
			else if (gameboard.timegame){//If we have gameboard timegame 3 and the timelimit is sent to the client
				client(3,gameboard,client);
				client((gameboard.timegrenze/60000)/244,gameboard,client);
				client((gameboard.timegrenze/60000)%244,gameboard,client);
			}
			gameboard.server=true;
			gameboard.netplay=true;
			} catch (IOException e1) {//Exception Handling if there were errors*/
				System.out.println("IO Fehler");
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 1;
		}
		netgame.setVisible(false);
		return 0;
	}
	/**Gets data from the InputStream and partly deals with it
	 * @param server indicates if the server or the client is started
	 * @param gameboard the Board from the game
	 * @param ubgclient the client socket used from the game
	 */
	public void getinput(boolean server,Board gameboard,Socket ubgclient){
		if (server){
		try {
			sinput = client.getInputStream();//assigns the proper input stream
		} catch (IOException e2) {
			System.out.println("The Inputstream of the server could not be set");
		}
		}
		else{
			try {
				sinput=ubgclient.getInputStream();
			} catch (IOException e) {
				System.out.println("The Inputstream of the client could not be set");
			}
		}
		try {
			while (sinput.available()!=0){/*While something is in the input*/
				if ((gameboard.i=sinput.read())<8 && gameboard.i>3){/*If the input value is smaller than 5 some lines were deleted by the opponent*/
					gameboard.i-=3;
					gameboard.opp+=16*gameboard.i;
					gameboard.opp+=4*(gameboard.i-1);
					gameboard.net+=gameboard.i;/*read it and add it to net*/
				}
				else if (gameboard.i==1){/*If gameboard 1 is received the game is won*/
					game.over(gameboard,true,ubgclient);
					gameboard.net=-2;
					break;
				}
				else if (gameboard.i==2 || (gameboard.server && !gameboard.netw.client.isConnected()) || (!gameboard.server && !ubgclient.isConnected())){/*If gameboard 2 is received someone has disconnected for some reason*/
					gameboard.netplay=false;
					gameboard.net=-2;
					gameboard.timer.stop();
					if (gameboard.level<5)
						gameboard.hop=10;
					gameboard.frame.setTitle(Menu.bundle.getString("singlegametitle"));//The Single Player game is started
					gameboard.statusBar.setMessage(Menu.bundle.getString("pointsdot")+gameboard.punkte+" "+Menu.bundle.getString("leveldot")+gameboard.level+" "+Menu.bundle.getString("nextleveldot")+(gameboard.grenze-gameboard.punkte));//The statusbar is modified for the single player mode
					gameboard.j.setText(Menu.bundle.getString("playerdisconnected"));
					gameboard.layeredPane.setVisible(true);
				}
				else if (gameboard.i==3)/*If gameboard 3 is received the game is lost*/
					game.over(gameboard,false,ubgclient);
				}
		} catch (IOException e) {
			System.out.println(Menu.bundle.getString("connectionnotpossible"));
		}
		}
	/**Function for writing something to the network opponent (e.game. deleted lines)
	 * @param i the Integer to write to the network opponent
	 */
	public void client(int i,Board gameboard,Socket ubgclient){//function for writing something out to the net
		try {
			if (gameboard.server){
				soutput = client.getOutputStream();//OutputStream is opened
				soutput.write(i);//the data is prepared for writing
				soutput.flush();//the data is written through
			}
			else{
				coutput = ubgclient.getOutputStream();//OutputStream is opened
				coutput.write(i);//the data is prepared for writing
				coutput.flush();//the data is written through
			}
			System.out.println(i+" gesendet");
		} catch (UnknownHostException e1) {//Exception Handling
			System.out.println("Wert: "+i+" konnte nicht verschickt werden! Host nicht gefunden!");
		} catch (IOException e1) {
			System.out.println("Wert: "+i+" konnte nicht verschickt werden! Ungültige IP-Adresse!");
		}
	}
	/**Closes the connections that are present during the network games
	 * @param gameboard the board from the game
	 * @param ugnclient the client which is used in the game
	 */
    protected void closeConnections(Board gameboard, Socket ugnclient){
		try {
			  if (gameboard.server){
				  sinput.close();
				  soutput.close();
				  client.close();
				  server.close();
				  client=null;
				  server=null;
				  System.out.println("Server geschlossen");
			   }
			   else{
				   sinput.close();
				   coutput.close();
				   gameboard.client.close();
				   ugnclient.close();
				   gameboard.client=null;
				   realclient=null;
				   System.out.println("Client geschlossen");
			   }
		   } catch (IOException e){
			   e.printStackTrace();
		   		System.out.println("The connection could not be closed");
		   }
	}
	/**Adds Lines to the own game if gameboard network signal or gameboard random event causes this
	 * @param anzahl the amount of lines to create
	 * @param gameboard the game board
	 * @return 1 if the game has ended caused by the lines added, 0 if the game is still continuing
	 */
	public int addLine(int anzahl,Board gameboard,Socket ubgclient){
		GeometricObject geo;
		boolean over=false;
		int k,j;
		for (GeometricObject geometricObject : gameboard.geometricObjects) {
			geo = geometricObject;/*Moves all objects in top direction*/
			if ((geo.position.y -= 20 * anzahl) < 0)//Checks if the object ends the game
				over = true;
		}
		if (over){//When an object finishes the game
			game.over(gameboard,false,ubgclient);//the over function is called
			return 1;
		}
		else{//Otherwise move the arraypositions
			k=anzahl;
			while (k<gameboard.merke.length){
				j=(gameboard.getWidth()/20);
				while (j>=0 && gameboard.merke[k][0]!=0){
					gameboard.merke[k-anzahl][j]=gameboard.merke[k][j];/*The always lower line is copied into the always upper line*/
					j--;
				}
				k++;
			}
			while (anzahl>0){//If we have enough space the new lines are filled in
				Random r= new Random();
				k=(Math.abs(r.nextInt()))%((gameboard.getWidth()/20)-1)+1;
				j=(gameboard.getWidth()/20);
				while (j>0){
					if (j!=k){
						gameboard.merke[((gameboard.getHeight()/20)-anzahl)][j]=1;
						gameboard.geometricObjects.add(new GeometricObject(new Vertex(((j-1)*20),(gameboard.getHeight()-anzahl*20)),19,19,Color.red,new Vertex(0,0)));
					}
					else
						gameboard.merke[((gameboard.getHeight()/20)-anzahl)][j]=0;
					j--;
				}
				gameboard.merke[((gameboard.getHeight()/20)-anzahl)][0]=(gameboard.getWidth()/20)-1;
			anzahl--;
			}
		}
		return 0;
	}
}
