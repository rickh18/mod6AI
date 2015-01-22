package mod6AI.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import mod6AI.ai.ClassificationType;
import mod6AI.gui.View.MouseController;

/**
 * Class to create a new item in a list. Extends <code>JPanel</code>
 * 
 * @author Rick Harms
 *
 */
public class ListItem extends JPanel {

	private ImageIcon icoPrediction;
	private ClassificationType type;
	private final String text;
	private JLabel lblPrediction;
	private JLabel btnCorrect;
	private JLabel btnWrong;
	private JLabel btnCorrected;
	private final GridBagConstraints d = new GridBagConstraints();

	// Loading all images
	private static ImageIcon icoC1;
	private static ImageIcon icoC2;
	public static final ImageIcon icoCorrect = new ImageIcon(
			"resources/ico_correct.png");
	public static final ImageIcon icoCorrectHover = new ImageIcon(
			"resources/ico_correct_hover.png");
	public static final ImageIcon icoWrong = new ImageIcon(
			"resources/ico_wrong.png");
	public static final ImageIcon icoWrongHover = new ImageIcon(
			"resources/ico_wrong_hover.png");
	private static final ImageIcon icoCorrected = new ImageIcon(
			"resources/ico_corrected.png");

	/**
	 * Constructor to create a new ListItem
	 * 
	 * @param text
	 *            The <code>String</code> text that should be displayed.
	 * @param type
	 *            The <code>ClassificationType</code> type that the text is set
	 *            to.
	 * @param controller
	 *            The <code>MouseController</code> controller that is added to
	 *            the buttons.
	 */
	public ListItem(String text, ClassificationType type,
			MouseController controller) {
		this.text = text;

		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.drawLayout();
		this.addController(controller);

		// item parameters
		this.setPreferredSize(new Dimension(600, 60));
		this.setMaximumSize(new Dimension(600, 60));

		setClassificationType(type);
	}

	/**
	 * Returns the <code>String</code> text of the ListItem.
	 * 
	 * @return the text of this ListItem displays.
	 */
	public String getText() {
		return text;
	}

	public void classify(boolean correct) {
		if (!correct) {
			if (type.equals(ClassificationType.C1))
				setClassificationType(ClassificationType.C2);
			else
				setClassificationType(ClassificationType.C1);
		}

		this.remove(btnCorrect);
		this.remove(btnWrong);
		d.weightx = 2;
		d.gridx = 2;
		this.add(btnCorrected, d);
	}

	/**
	 * Get the <code>ClassifcationType</code> this ListItem.
	 * 
	 * @return <code>ClassificationType</code> type of this ListItem.
	 */
	public ClassificationType getClassificationType() {
		return type;
	}

	/**
	 * Change the <code>ClassificationType</code> type of this object. Also sets
	 * the image.
	 * 
	 * @param type
	 *            the <code>ClassificationType</code> type that has to be set.
	 */
	void setClassificationType(ClassificationType type) {
		this.type = type;
		if (type.equals(ClassificationType.C1))
			icoPrediction = icoC1;
		else
			icoPrediction = icoC2;
		lblPrediction.setIcon(icoPrediction);
	}

	/**
	 * Set the name of the <code>ClassificationType</code>. Changes the icons to
	 * the correct ones.
	 * 
	 * @param name
	 *            the <code>View.ClassificationName</code> name to be set.
	 */
	public static void setClassificationName(ClassificationName name) {
		icoC1 = new ImageIcon(name.getRes1());
		icoC2 = new ImageIcon(name.getRes2());
	}

	/**
	 * Adding the <code>MouseController</code> controller to the correct and
	 * wrong button.
	 * 
	 * @param controller
	 *            the <code>MouseController</code> controller.
	 */
	private void addController(MouseController controller) {
		// adding eventlistener
		btnCorrect.addMouseListener(controller);
		btnWrong.addMouseListener(controller);
	}

	/**
	 * Adds the <code>JTextfield</code> and <code>JButtons</code> to this.
	 */
	private void drawLayout() {
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		// GridBagConstraints
		d.anchor = GridBagConstraints.NORTH;
		d.fill = GridBagConstraints.HORIZONTAL;

		// text
		d.weightx = 8;
		JLabel lblText = new JLabel(text);
		lblText.setBorder(border);
		lblText.setVerticalAlignment(JLabel.TOP);
		lblText.setFont(new Font("Serif", Font.PLAIN, 20));
		lblText.setMinimumSize(new Dimension(540, 60));
		lblText.setPreferredSize(new Dimension(480, 60));
		this.add(lblText, d);

		// prediction
		d.weightx = 1;
		d.gridx = 1;
		lblPrediction = new JLabel();
		lblPrediction.setIcon(icoPrediction);
		lblPrediction.setHorizontalAlignment(JLabel.CENTER);
		lblPrediction.setBorder(border);
		lblPrediction.setMinimumSize(new Dimension(60, 60));
		lblPrediction.setPreferredSize(new Dimension(60, 60));
		this.add(lblPrediction, d);

		// correct button
		d.gridx = 2;
		btnCorrect = new JLabel();
		btnCorrect.setIcon(icoCorrect);
		btnCorrect.setHorizontalAlignment(JLabel.CENTER);
		btnCorrect.setBorder(border);
		btnCorrect.setMinimumSize(new Dimension(60, 60));
		btnCorrect.setPreferredSize(new Dimension(60, 60));
		this.add(btnCorrect, d);

		// wrong button
		d.gridx = 3;
		btnWrong = new JLabel();
		btnWrong.setIcon(icoWrong);
		btnWrong.setHorizontalAlignment(JLabel.CENTER);
		btnWrong.setBorder(border);
		btnWrong.setMinimumSize(new Dimension(60, 60));
		btnWrong.setPreferredSize(new Dimension(60, 60));
		this.add(btnWrong, d);

		// corrected button
		btnCorrected = new JLabel();
		btnCorrected.setIcon(icoCorrected);
		btnCorrected.setHorizontalAlignment(JLabel.CENTER);
		btnCorrected.setBorder(border);
		btnCorrected.setMinimumSize(new Dimension(120, 60));
		btnCorrected.setPreferredSize(new Dimension(120, 60));
	}
}
