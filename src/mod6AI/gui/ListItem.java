package mod6AI.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import mod6AI.ai.ClassificationType;
import mod6AI.gui.View.MouseController;

//import mod6AI.gui.View.ListItem;

public class ListItem extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2413060721925199749L;
	private ImageIcon icoPrediction;
	private ClassificationType type;
	private String text;

	private JLabel lblText;
	private JLabel lblPrediction;
	private JLabel btnCorrect;
	private JLabel btnWrong;
	private JLabel btnCorrected;
	private GridBagConstraints d = new GridBagConstraints();

	public static final ImageIcon icoMale = View.scaleImage(
			"resources/male.png", 50, 50);
	public static final ImageIcon icoFemale = View.scaleImage(
			"resources/female.png", 50, 50);
	public static final ImageIcon icoCorrect = View.scaleImage(
			"resources/ico_correct.png", 60, 60);
	public static final ImageIcon icoCorrectHover = View.scaleImage(
			"resources/ico_correct_hover.png", 60, 60);
	public static final ImageIcon icoWrong = View.scaleImage(
			"resources/ico_wrong.png", 60, 60);
	public static final ImageIcon icoWrongHover = View.scaleImage(
			"resources/ico_wrong_hover.png", 60, 60);
	public static final ImageIcon icoCorrected = View.scaleImage(
			"resources/ico_corrected.png", 120, 60);

	public ListItem(String text, ClassificationType type,
			MouseController controller) {
		setClassificationType(type);
		this.text = text;

		this.setLayout(new GridBagLayout());
		this.drawLayout();
		this.addController(controller);

		// item parameters
		this.setPreferredSize(new Dimension(600, 60));
		this.setMaximumSize(new Dimension(600, 60));
	}

	public ListItem getListItem() {
		return this;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	public ClassificationType getClassificationType(boolean correct) {
		if (!correct) {
			switch (type) {
			case FEMALE:
				setClassificationType(ClassificationType.MALE);
				break;
			case MALE:
				setClassificationType(ClassificationType.FEMALE);
				break;
			default:
				return null;
			}
			lblPrediction.setIcon(icoPrediction);
		}
		return type;
	}

	public void setClassificationType(ClassificationType type) {
		this.type = type;
		if (type.equals(ClassificationType.MALE))
			icoPrediction = icoMale;
		else
			icoPrediction = icoFemale;
	}

	public void changeButton() {
		this.remove(btnCorrect);
		this.remove(btnWrong);
		d.weightx = 2;
		d.gridx = 2;
		this.add(btnCorrected, d);
	}

	private void addController(MouseController controller) {
		// adding eventlistener
		btnCorrect.addMouseListener(controller);
		btnWrong.addMouseListener(controller);
	}

	private void drawLayout() {
		// GridBagConstraints
		d.anchor = GridBagConstraints.NORTH;
		d.fill = GridBagConstraints.HORIZONTAL;

		// text
		d.weightx = 8;
		lblText = new JLabel(text);
		lblText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		lblPrediction.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblPrediction.setMinimumSize(new Dimension(60, 60));
		lblPrediction.setPreferredSize(new Dimension(60, 60));
		this.add(lblPrediction, d);

		// correct button
		d.gridx = 2;
		btnCorrect = new JLabel();
		btnCorrect.setIcon(icoCorrect);
		btnCorrect.setHorizontalAlignment(JLabel.CENTER);
		btnCorrect.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnCorrect.setMinimumSize(new Dimension(60, 60));
		btnCorrect.setPreferredSize(new Dimension(60, 60));
		this.add(btnCorrect, d);

		// wrong button
		d.gridx = 3;
		btnWrong = new JLabel();
		btnWrong.setIcon(icoWrong);
		btnWrong.setHorizontalAlignment(JLabel.CENTER);
		btnWrong.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnWrong.setMinimumSize(new Dimension(60, 60));
		btnWrong.setPreferredSize(new Dimension(60, 60));
		this.add(btnWrong, d);

		// corrected button
		btnCorrected = new JLabel();
		btnCorrected.setIcon(icoCorrected);
		btnCorrected.setHorizontalAlignment(JLabel.CENTER);
		btnCorrected.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnCorrected.setMinimumSize(new Dimension(120, 60));
		btnCorrected.setPreferredSize(new Dimension(120, 60));
	}
}
