package mod6AI.gui;

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
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import mod6AI.ai.AI;
import mod6AI.ai.ClassificationType;

public class View extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1229676347999028520L;
	private String title = "Gender Guesser";
	private JPanel list;
	private JButton button;
	private JTextField input;
	private JScrollBar vertical;
	private MouseController controller;

	private AI ai;

	public static void main(String[] args) {
		ClassificationType.C1.setName("Male");
		ClassificationType.C2.setName("Female");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ListItem(null, ClassificationType.C1, null);
				AI ai = new AI(1);
				new View(ai);
			}
		});
	}

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
		button = new JButton("Send");
		button.setBackground(bgColor);
		button.setFont(new Font("Serif", Font.BOLD, 30));
		panel.add(button, c);

		// Add controllers
		new GuiController(ai);
		controller = new MouseController();

		// We shall not discriminate!
		ImageIcon icon;
		if (Math.random() >= 0.5)
			icon = new ImageIcon("resources/male.png");
		else
			icon = new ImageIcon("resources/female.png");

		// Frame parameters
		this.setIconImage(icon.getImage());
		this.setTitle(title);
		this.setSize(665, 720);
		this.setResizable(false);
		this.setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {

	}

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
		private AI ai;

		/**
		 * GuiController
		 * 
		 * @param ai
		 */
		public GuiController(AI ai) {
			button.addActionListener(this);
			this.ai = ai;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(button)) {
				list.add(new ListItem(input.getText(), ai.classify(input
						.getText()), controller));
				update();
			}
		}

	}

	public class MouseController implements MouseListener {

		public MouseController() {
			button.addMouseListener(this);
		}

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
	 * Scale function to scale the imageIcons.
	 * 
	 * @param image
	 *            ...... the <code>String</code> location of the image to be
	 *            scaled.
	 * @param width
	 *            the desired <code>width</code>
	 * @param height
	 *            the desired <code>height</code>
	 * @return Image <code>image</code> of the scaled image.
	 */
	public static ImageIcon scaleImage(String resource, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(resource);
		ImageIcon img;
		long begin = System.currentTimeMillis();
		
		if (width == 0 || height == 0) {
			img = imageIcon;
		} else {
			
			img = new ImageIcon(imageIcon.getImage().getScaledInstance(width,
					height, java.awt.Image.SCALE_SMOOTH));
		}
		long time = begin - System.currentTimeMillis();
		System.out.println("timing: " + resource + time);
		
		return img;
	}

}
