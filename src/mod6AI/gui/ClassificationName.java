package mod6AI.gui;

/**
 * Enumeration with information from different classifiers.
 * @author Rick Harms
 *
 */
public enum ClassificationName {
	GENDER("resources/male.png", "resources/female.png", ".gender", 5.0), 
	MAIL("resources/ham.png", "resources/spam.png", ".mail", 0.1);

	private String res1;
	private String res2;
	private String extension;
	private double k;

	private ClassificationName(String res1, String res2, String extension,
			double k) {
		this.res1 = res1;
		this.res2 = res2;
		this.extension = extension;
	}

	public String getRes1() {
		return res1;
	}

	public String getRes2() {
		return res2;
	}

	public String getExtension() {
		return extension;
	}

	public double getK() {
		return k;
	}
}