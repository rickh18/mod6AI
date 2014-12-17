package mod6AI.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private View view;
	private JTextField input;
	ImageIcon imgMale;
	ImageIcon imgFemale;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AI ai = new AI(10);
				new View(ai);
			}
		});

	}

	public View(AI ai) {
		super();
		view = this;

		// Nice blue color for background
		Color bgColor = new Color(180, 220, 240);

		// Screen with gridbaglayout
		/**
		 * -------------------------------
		 * |-----------------------------|
		 * ||scrpPane				|	 |
		 * ||-----------------------|	 |
		 * |||list			|label| |	 |
		 * |-------------------------	 |
		 * |-----------------------------|
		 * ||input				|buttton||
		 * |-----------------------------|
		 * -------------------------------
		 */
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
		button.setFont(new Font("Serif", Font.BOLD, 30));
		panel.add(button, c);

		// Add controller
		new GuiController(ai);

		// Frame parameters
		ImageIcon icon = new ImageIcon("resources/male.png");
		this.setIconImage(icon.getImage());
		this.setTitle(title);
		this.setSize(665, 720);
		this.setResizable(false);
		this.setVisible(true);

		// setting images
		imgMale = scaleImage(new ImageIcon("resources/male.png").getImage(),
				50, 50);
		imgFemale = scaleImage(
				new ImageIcon("resources/female.png").getImage(), 50, 50);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Adding a new item to the list
	 * 
	 * @param text
	 *            text of the item.
	 * @param type
	 *            type of the item.
	 */
	private void addItem(String text, ClassificationType type) {
		ImageIcon image;

		if (type.equals(ClassificationType.C1))
			image = imgMale;
		else
			image = imgFemale;

		JPanel item = new JPanel();
		item.setLayout(new GridBagLayout());

		// GridBagConstraints
		GridBagConstraints d = new GridBagConstraints();
		d.anchor = GridBagConstraints.NORTH;
		d.fill = GridBagConstraints.HORIZONTAL;

		// text
		d.weightx = 9;
		JLabel label = new JLabel(text);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setVerticalAlignment(JLabel.TOP);
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		label.setMinimumSize(new Dimension(540, 60));
		label.setPreferredSize(new Dimension(540, 60));
		item.add(label, d);

		// image
		d.weightx = 1;
		d.gridx = 1;
		JLabel prediction = new JLabel();
		prediction.setIcon(image);
		prediction.setHorizontalAlignment(JLabel.CENTER);
		prediction.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		prediction.setMinimumSize(new Dimension(60, 60));
		prediction.setPreferredSize(new Dimension(60, 60));
		item.add(prediction, d);

		// item parameters
		item.setPreferredSize(new Dimension(600, 60));
		item.setMaximumSize(new Dimension(600, 60));

		// adding to list
		list.add(item);

		// Repainting
		view.getContentPane().validate();
		view.getContentPane().repaint();

	}

	@Override
	public void update(Observable o, Object arg) {
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
			addItem(input.getText(), ai.classify(input.getText()));
		}

	}

	/**
	 * Scale function to scale the imageIcons.
	 * 
	 * @param image
	 *            the <code>image</code> to be scaled.
	 * @param width
	 *            the desired <code>width</code>
	 * @param height
	 *            the desired <code>height</code>
	 * @return Image <code>image</code> of the scaled image.
	 */
	public ImageIcon scaleImage(Image image, int width, int height) {
		if (width == 0 || height == 0) {
			return new ImageIcon(image);
		} else {
			return new ImageIcon(image.getScaledInstance(width, height,
					java.awt.Image.SCALE_SMOOTH));
		}
	}

}
