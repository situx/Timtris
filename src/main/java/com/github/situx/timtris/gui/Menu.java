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

import com.github.situx.timtris.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class Menu {
	private static final long serialVersionUID = 1L;
    public static ResourceBundle bundle=ResourceBundle.getBundle("Timtris", Locale.getDefault(),new UTF8Bundle("UTF-8"));
	JLabel Anweisung = new JLabel();
	private Board gameboard;
	private JFrame about = new JFrame();//The "About"-Frame which is also used as Frame for the manual
	private JLabel lab = new JLabel();
	private Game g = new Game();
	/**
	 * Generates the frames needed for the game menu
	 */
	private void build(){//Function to build the several frames
		about.setResizable(false);
		JButton b1 =new JButton(Menu.bundle.getString("ok"));
		b1.setSize(new Dimension(50,50));
		ActionListener act9= e -> about.setVisible(false);
		about.add(lab, java.awt.BorderLayout.NORTH);
		about.add(b1,java.awt.BorderLayout.SOUTH);
		about.setPreferredSize(new Dimension(200,200));
		about.pack();
		b1.addActionListener(act9);
	}
	/**Creates the menubar for the game window
	 * @param gameboard the Board in which the menubar will be integrated
	 * @return the new menubar
	 */
	JMenuBar menubar(Board gameboard){
	this.gameboard =gameboard;
	JMenuBar menuBar = new JMenuBar();//A new menubar is created
	build();
	menuBar.setToolTipText(bundle.getString("gametitle"));
	JMenu menu = new JMenu(bundle.getString("options"));//First menu Optionen:
	JMenuItem ngame = new JMenuItem(bundle.getString("newgame"));//First entry "Neues Spiel"
	ActionListener act1 = e -> {
        g.newgame(this.gameboard,0,null);//Neues Spiel should start gameboard new Tetris game in the class Game
    };
	ngame.addActionListener(act1);//The ActionListener is added to the menu entry
	ngame.setMnemonic(KeyEvent.VK_N);
	menu.add(ngame);//Hinzufügen zum Menü
	JMenuItem nmgame = new JMenuItem(bundle.getString("newmariomod"));//Second entry "Neues Dr. Mario Spiel"
	ActionListener act8 = e -> {
        g.newmariogame(this.gameboard);//Neues Dr. Mario Spiel should start gameboard new Dr. Mario game in the class Game
    };
	nmgame.addActionListener(act8);//The ActionListener is added to the menu entry
	nmgame.setMnemonic(KeyEvent.VK_N);
	menu.add(nmgame);//Hinzufügen zum Menü
	JMenuItem network = new JMenuItem(bundle.getString("newclientlangame"));//Third entry "Neues Spiel LAN-SPiel als Client"
	ActionListener act7 = e -> {
        this.gameboard.netw.build(this.gameboard);
        this.gameboard.netw.clientbuild();
    };
	network.addActionListener(act7);//The ActionListener is added to the menu entry
	network.setMnemonic(KeyEvent.VK_N);
	menu.add(network);//Add it to the menu
	JMenuItem network2 = new JMenuItem(bundle.getString("newserverlangame"));//An option for gameboard netw game in which the localhost is the server
	ActionListener act9 = e -> {
        this.gameboard.netw.build(this.gameboard);
        this.gameboard.netw.serverbuild();
    };
	network2.addActionListener(act9);
	network2.setMnemonic(KeyEvent.VK_P);
	menu.add(network2);
	JMenuItem pause = new JMenuItem(bundle.getString("pause"));
	ActionListener act2 = e -> {

    if (this.gameboard.timer.isRunning()){//The pause button for the game
        this.gameboard.timer.stop();
        this.gameboard.layeredPane.setVisible(true);
    }
    else{
        this.gameboard.layeredPane.setVisible(false);
        this.gameboard.timer.start();
    }
    };
	pause.addActionListener(act2);
	pause.setMnemonic(KeyEvent.VK_P);
	menu.add(pause);
	JMenuItem settings = new JMenuItem(bundle.getString("settings"));//the options of timtris merely to regulate the difficulty of the tetris game and the modgame
	ActionListener act3 = e -> {
        final JFrame abt = new JFrame();
        abt.setResizable(false);
        abt.setPreferredSize(new Dimension(390, 100));
        abt.setVisible(true);
        abt.setTitle(bundle.getString("settings"));
        JButton b1 =new JButton(bundle.getString("ok"));
        b1.setSize(new Dimension(50,50));
        JPanel checkpanel=new JPanel();
        JLabel checkl=new JLabel();
        checkl.setText(Menu.bundle.getString("difficultylevel"));
        final JCheckBox check = new JCheckBox();
        check.setText(bundle.getString("easy"));
        final JCheckBox check1 = new JCheckBox();
        check1.setText(bundle.getString("medium"));
        final JCheckBox check2 = new JCheckBox();
        check2.setText(bundle.getString("hard"));
        checkpanel.add(checkl);
        checkpanel.add(check);
        checkpanel.add(check1);
        checkpanel.add(check2);
        ActionListener act11 = e1 -> {
            check1.setSelected(false);
            check2.setSelected(false);
        };
        ActionListener act21 = e12 -> {
            check.setSelected(false);
            check2.setSelected(false);
        };
        ActionListener act31 = e13 -> {
            check.setSelected(false);
            check1.setSelected(false);
        };
        check.addActionListener(act11);
        check1.addActionListener(act21);
        check2.addActionListener(act31);
        ActionListener act= e14 -> {
            if (check.isSelected()){
                this.gameboard.speed=400;
                this.gameboard.rot=false;
                this.gameboard.verschoben=false;
                this.gameboard.rand=false;
                this.gameboard.hw=false;
                this.gameboard.marioc=10;
            }
            if (check1.isSelected()){
                this.gameboard.speed=200;
                this.gameboard.verschoben=true;
                this.gameboard.rot=true;
                this.gameboard.rand=false;
                this.gameboard.hw=false;
                this.gameboard.marioc=8;
            }
            if (check2.isSelected()){
                this.gameboard.speed=100;
                this.gameboard.rot=true;
                this.gameboard.verschoben=true;
                this.gameboard.rand=true;
                this.gameboard.hw=true;
                this.gameboard.marioc=5;
            }
            abt.setVisible(false);
        };
        JLabel lab1 = new JLabel();
        lab1.setSize(new Dimension (200,16));
        lab1.setText(Menu.bundle.getString("timtrissettings"));
        abt.add(checkpanel,java.awt.BorderLayout.NORTH);
        abt.add(checkpanel,java.awt.BorderLayout.CENTER);
        abt.add(b1,java.awt.BorderLayout.SOUTH);
        abt.pack();
        b1.addActionListener(act);
        };
	settings.addActionListener(act3);
	settings.setMnemonic(KeyEvent.VK_N);
	menu.add(settings);
	menu.addSeparator();
	JMenuItem exit = new JMenuItem(bundle.getString("exitgame"));//the exit option
	exit.setMnemonic(KeyEvent.VK_Q);
	ActionListener act4 = e -> {
        System.exit(0);//menu entray for exiting the game
    };
	exit.addActionListener(act4);
	menu.add(exit);
	menuBar.add(menu);
	JMenuItem about = new JMenuItem(bundle.getString("about"));//The about entry as demanded in the task
	JMenu menu2 = new JMenu(bundle.getString("help"));
	ActionListener act5 = e -> {
        Menu.this.about.setTitle(bundle.getString("about"));
        lab.setSize(new Dimension (200,16));
        lab.setText(Menu.bundle.getString("copyright"));
        Menu.this.about.setPreferredSize(new Dimension(200,200));
        Menu.this.about.pack();
        Menu.this.about.setVisible(true);
};
	about.addActionListener(act5);
	about.setMnemonic(KeyEvent.VK_A);
	JMenuItem help = new JMenuItem(bundle.getString("manual"));//The menu point manual
	ActionListener act6 = e -> {
        Menu.this.about.setPreferredSize(new Dimension(300, 440));
        Menu.this.about.setTitle(Menu.bundle.getString("manual"));
        lab.setText(Menu.bundle.getString("manualtext"));
        Menu.this.about.pack();
        Menu.this.about.setVisible(true);
};
	help.addActionListener(act6);
	menu2.add(help);
	menu2.add(about);
	menuBar.add(menu2);
	return menuBar;//The new menuBar is returned to the function in Board
	}
}
