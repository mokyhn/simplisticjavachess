class Errorhandler {
	public Errorhandler() {
	}

	//$ANALYSIS-IGNORE,codereview.java.rules.declaration.RuleDeclarationMultipleDeclaration
	static final int NOSUCHPIECE=1, TOOMANYPIECES=2;
	

	private void msg(String callplace) {
		System.out.println("ERROR at " + callplace);
	}

	public void fatalError(int err, String callplace) {
		msg(callplace);
		switch (err) {
		case NOSUCHPIECE:
			System.out.println("No piece found");
			break;
		case TOOMANYPIECES:
			System.out.println("Too many pieces");
			break;

		}
		System.exit(0);

	}

}
