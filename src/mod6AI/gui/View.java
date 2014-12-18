package mod6AI.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import mod6AI.ai.AI;
import mod6AI.ai.ClassificationType;

import com.sun.glass.events.KeyEvent;

/**
 * View for the Gui.
 * 
 * @author Rick Harms
 *
 */
public class View extends JFrame implements Observer {

	private static final long serialVersionUID = -1229676347999028520L;
	private String title = "Classifier group 3A";
	private JPanel list;
	private JButton button;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem info;
	private JMenuItem[] menuItems = new JMenuItem[4];
	private String[] menuItemNames = { "new", "Open", "Save", "Exit" };
	private String[] options = { "Gender classifier", "Mail classifier",
			"Open file" };
	private JFileChooser fc;
	private FileNameExtensionFilter gender;
	private FileNameExtensionFilter mail;
	private JStatusBar statusBar;
	private JTextField input;
	private JScrollBar vertical;
	private MouseController controller;

	ImageIcon icon;

	private AI ai;

	public enum ClassificationName {
		GENDER, MAIL
	};

	private String extension;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AI ai = new AI(1);
				new View(ai);
			}
		});
	}

	/**
	 * Contructor of View.
	 * 
	 * @param ai
	 *            The <code>AI</code> ai to start with.
	 */
	public View(AI ai) {
		super();
		this.ai = ai;
		// Nice blue color for background
		Color bgColor = new Color(180, 220, 240);

		// Screen with gridbaglayout
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createLineBorder(bgColor, 20));
		this.add(panel);

		// GridbagConstraints c;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;

		// List of items
		list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		list.setAlignmentY(LEFT_ALIGNMENT);
		list.setBackground(bgColor);

		// Scrollable field;
		c.gridwidth = 2;
		c.weighty = 8;
		JScrollPane scrPane = new JScrollPane(list);
		scrPane.setPreferredSize(new Dimension(scrPane.getHeight(), scrPane
				.getWidth()));
		panel.add(scrPane, c);
		vertical = scrPane.getVerticalScrollBar();

		// Input field
		c.gridwidth = 1;
		c.weightx = 5;
		c.weighty = 1;
		c.gridheight = 1;
		c.gridy = 2;
		input = new JTextField();
		panel.add(input, c);

		// Button
		c.gridx = 1;
		c.weightx = 1;
		button = new JButton("Classify");
		button.setBackground(bgColor);
		button.setFont(new Font("Serif", Font.BOLD, 30));
		panel.add(button, c);

		// StatusBar
		statusBar = new JStatusBar();
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);

		// Menubar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// First menu
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuItems[0] = new JMenuItem("New Classifier", KeyEvent.VK_N);
		menuItems[1] = new JMenuItem("Open File...", KeyEvent.VK_O);
		menuItems[2] = new JMenuItem("Save As...", KeyEvent.VK_S);
		menuItems[3] = new JMenuItem("Exit", KeyEvent.VK_E);
		int i = 0;
		for (JMenuItem menuItem : menuItems) {
			menuItem.setName(menuItemNames[i++]);
			menu.add(menuItem);
		}
		menuBar.add(menu);

		// Info menu
		info = new JMenuItem("?");
		menuBar.add(info);

		// FileChooser
		fc = new JFileChooser();
		gender = new FileNameExtensionFilter(
				"Gender files (*.gender)", "gender");
		mail = new FileNameExtensionFilter(
				"Mail files (*.mail)", "mail");
		fc.addChoosableFileFilter(gender);
		fc.addChoosableFileFilter(mail);
		fc.setFileFilter(gender);
		fc.setFileFilter(mail);

		// Add controllers
		new GuiController(ai);
		controller = new MouseController();

		// We shall not discriminate!
		setIcon(ClassificationName.GENDER);

		// Frame parameters
		this.setTitle(title);
		this.setSize(665, 720);
		this.setResizable(false);
		this.setVisible(true);
		showOptions();

		// Closing window listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				View.this.dispose();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	/**
	 * Updating the window, repaint the contentPane
	 */
	public void update() {
		// Repainting
		this.getContentPane().validate();
		this.getContentPane().repaint();
		vertical.setValue(vertical.getMaximum());
	}

	/**
	 * Controller class
	 * 
	 * @author Rick Harms
	 *
	 */
	public class GuiController implements ActionListener {
		/*
		 * The <code>AI<code> ai that should be controlled.
		 */
		private AI ai;

		/**
		 * GuiController for the send button and the menu items.
		 * 
		 * @param ai
		 *            the <code>AI</code> ai to be controlled
		 */
		public GuiController(AI ai) {
			this.ai = ai;
			button.addActionListener(this);
			info.addActionListener(this);
			input.addActionListener(this);
			for (JMenuItem menuItem : menuItems)
				menuItem.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source.equals(button) || source.equals(input)) {
				list.add(new ListItem(input.getText(), ai.classify(input
						.getText()), controller));
				input.setText("");
				statusBar.setMessage("Item classified");
				update();
			} else if (source.getClass().equals(JMenuItem.class)) {
				if (source.equals(info)) {
					JOptionPane.showMessageDialog(View.this,
							"Classifier. Made by Group 3A \n"
									+ "Frans van dijk\n" + "Rick van Gemert\n"
									+ "Remco Brunsveld and\n" + "Rick Harms",
							"Info", JOptionPane.PLAIN_MESSAGE);
				} else {
					menuAction((JMenuItem) source);
				}
			}
		}
	}

	/**
	 * Mouse controller to get input from the mouse.
	 * 
	 * @author Rick Harms
	 *
	 */
	public class MouseController implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Object source = e.getSource();
			Object parent = ((Component) source).getParent();
			ListItem item = null;

			if (source.getClass().equals(JLabel.class)) {
				Icon icon = ((JLabel) source).getIcon();
				if (parent.getClass().equals(ListItem.class)) {
					item = (ListItem) parent;
					item.changeButton();
					ai.train(item.getText(), item.getClassificationType(icon
							.equals(ListItem.icoCorrectHover)));
					statusBar.setMessage("Corrected");
					update();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource().getClass().equals(JLabel.class)) {
				JLabel b = (JLabel) e.getSource();
				if (b.getIcon().equals(ListItem.icoCorrect)) {
					b.setIcon(ListItem.icoCorrectHover);
				} else if (b.getIcon().equals(ListItem.icoWrong)) {
					b.setIcon(ListItem.icoWrongHover);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource().getClass().equals(JLabel.class)) {
				JLabel b = (JLabel) e.getSource();
				if (b.getIcon().equals(ListItem.icoCorrectHover)) {
					b.setIcon(ListItem.icoCorrect);
				} else if (b.getIcon().equals(ListItem.icoWrongHover)) {
					b.setIcon(ListItem.icoWrong);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	/**
	 * menuAction class. Handles all menu actrions from te different menu Items.
	 * 
	 * @param source
	 *            <code>JMenuItem</code> that called the action.
	 */
	private void menuAction(JMenuItem source) {
		int returnVal;
		File file;
		switch (source.getName()) {
		case "new":
			new View(new AI(ai.getK()));
			break;
		case "Open":
			returnVal = fc.showOpenDialog(View.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				try {
					if (ai.load(file.getAbsolutePath())) {
						statusBar.setMessage("File: " + file.getName()
								+ " opened.");
						if (file.getAbsolutePath().endsWith(".mail")) {
							setIcon(ClassificationName.MAIL);
						} else {
							setIcon(ClassificationName.GENDER);
						}
					} else {
						throw new FileNotFoundException();
					}
				} catch (FileNotFoundException e1) {
					showWarning("Opening file error", "File: " + file.getName()
							+ " could not be opened.");
				}
			}
			break;
		case "Save":
			returnVal = fc.showSaveDialog(View.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				if (!file.getAbsolutePath().endsWith(extension)) {
					file = new File(file.toString() + extension);
				}
				try {
					ai.save(file.getAbsolutePath());
					statusBar.setMessage("File saved as: " + file.getName()
							+ ".");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case "Exit":
			View.this.dispose();
			break;
		default:
			break;
		}
	}

	/**
	 * Shows a MessageDialog with a warning
	 * 
	 * @param title
	 *            The <code>String</code> title of the dialog.
	 * @param warning
	 *            The <code>String</code> warning message to be desplayed.
	 */
	private void showWarning(String title, String warning) {
		statusBar.setMessage(warning);
		JOptionPane.showMessageDialog(View.this, warning, title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * This will show an OptionDialog to determine what should be done. Called
	 * at the start of the View.
	 */
	private void showOptions() {
		int n = JOptionPane.showOptionDialog(View.this,
				"What would you like to do?", "Choose option",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[2]);
		System.out.println(n);
		switch (n) {
		case JOptionPane.YES_OPTION:
			ClassificationType.C1.setName("Male");
			ClassificationType.C2.setName("Female");
			setIcon(ClassificationName.GENDER);
			break;
		case JOptionPane.NO_OPTION:
			ClassificationType.C1.setName("Ham");
			ClassificationType.C2.setName("Spam");
			setIcon(ClassificationName.MAIL);
			break;
		case JOptionPane.CANCEL_OPTION:
			File file;
			int returnVal = fc.showOpenDialog(View.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				try {
					if (ai.load(file.getAbsolutePath())) {
						statusBar.setMessage("File: " + file.getName()
								+ " opened.");
						if (file.getAbsolutePath().endsWith(".mail")) {
							setIcon(ClassificationName.MAIL);
						} else {
							setIcon(ClassificationName.GENDER);
						}
					} else {
						throw new FileNotFoundException();
					}
				} catch (FileNotFoundException e1) {
					showWarning("Opening file error", "File: " + file.getName()
							+ " could not be opened.");
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Sets the icon of the window.
	 * 
	 * @param name
	 *            the <code>ClassificationName</code> name of the
	 *            Classification.
	 */
	private void setIcon(ClassificationName name) {
		String res1 = null;
		String res2 = null;
		switch (name) {
		case MAIL:
			res1 = "resources/spam.png";
			res2 = "resources/ham.png";
			extension = ".mail";
			break;
		case GENDER:
			res1 = "resources/male.png";
			res2 = "resources/female.png";
			extension = ".gender";
			break;
		default:
			break;
		}
		if (Math.random() >= 0.5)
			this.icon = new ImageIcon(res1);
		else
			this.icon = new ImageIcon(res2);
		ListItem.setClassificationName(name);
		this.setIconImage(icon.getImage());
	}
}
