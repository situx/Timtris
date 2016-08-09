package com.github.situx.timtris.gui;

import java.awt.*;

public class StatusBar extends javax.swing.JLabel {//StatusBar for showing the points and other status messages
	private static final long serialVersionUID = 4193236748746053697L;
    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));//The Dimension of the StatusBar
        setMessage(Menu.bundle.getString("welcome"));//Setting the default text
        setOpaque(true);//Setting the opaque value to be able to change the background color
        setBackground(Color.black);//Setting the background color
        setForeground(Color.yellow);//Setting the font
    }
    
    /**Sets the text of the status bar
     * @param message the message to be set
     */
    public void setMessage(String message) {//Function for setting the text message
        setText(" "+message);        
    }        
}
