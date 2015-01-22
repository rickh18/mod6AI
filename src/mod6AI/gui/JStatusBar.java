package mod6AI.gui;

import java.awt.Dimension;
import javax.swing.JLabel;

/**
 * StatusBar to show messages.
 * 
 * @author Rick
 *
 */
class JStatusBar extends JLabel {

	/** Creates a new instance of StatusBar */
	public JStatusBar() {
		super();
		super.setPreferredSize(new Dimension(100, 16));
		setMessage("Ready");
	}

	/**
	 * Set a message to the StatusBar
	 * 
	 * @param message
	 *            the <code>String</code> message to be set.
	 */
	public void setMessage(String message) {
		setText(" " + message);
	}
}
