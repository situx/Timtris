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
