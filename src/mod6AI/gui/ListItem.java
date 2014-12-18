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

import mod6AI.ai.ClassificationType;
import mod6AI.gui.View.MouseController;

//import mod6AI.gui.View.ListItem;

public class ListItem extends JPanel {
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

	public static ImageIcon icoC1 = new ImageIcon("resources/male.png");
	public static ImageIcon icoC2 = new ImageIcon("resources/female.png");
	public static final ImageIcon icoCorrect = new ImageIcon("resources/ico_correct.png");
	public static final ImageIcon icoCorrectHover = new ImageIcon("resources/ico_correct_hover.png");
	public static final ImageIcon icoWrong = new ImageIcon("resources/ico_wrong.png");
	public static final ImageIcon icoWrongHover = new ImageIcon("resources/ico_wrong_hover.png");
	public static final ImageIcon icoCorrected = new ImageIcon("resources/ico_corrected.png");

	public ListItem(String text, ClassificationType type,
			MouseController controller) {
		setClassificationType(type);
		this.text = text;

		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.drawLayout();
		this.addController(controller);

		// item parameters
		this.setPreferredSize(new Dimension(600, 60));
		this.setMaximumSize(new Dimension(600, 60));
	}

	public ListItem getListItem() {
		return this;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ClassificationType getClassificationType(boolean correct) {
		if (!correct) {
			switch (type) {
			case C2:
				setClassificationType(ClassificationType.C1);
				break;
			case C1:
				setClassificationType(ClassificationType.C2);
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
		if (type.equals(ClassificationType.C1))
			icoPrediction = icoC1;
		else
			icoPrediction = icoC2;
	}
	
	public static void setClassificationName(View.ClassificationName name ){
		switch (name) {
		case MAIL:
			 icoC1 = new ImageIcon("resources/spam.png");
			 icoC2 = new ImageIcon("resources/ham.png");
			break;
		case GENDER:
			icoC1 = new ImageIcon("resources/male.png");
			icoC2 = new ImageIcon("resources/female.png");
			break;
		default:
			break;
		}
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
