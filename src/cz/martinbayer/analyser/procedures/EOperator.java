package cz.martinbayer.analyser.procedures;

public enum EOperator {

	GT(">", 1), LT("<", 1), BTW("BETWEEN", 2), EQUALS("EQUALS", 1), GTEQUAL(
			">=", 1), LTEQUAL("<=", 1), LIKE("LIKE", 1);

	private String sign;
	private int operandsCount;

	EOperator(String sign, int operandsCount) {
		this.sign = sign;
		this.operandsCount = operandsCount;
	}

	public String getSign() {
		return sign;
	}

	/**
	 * Basically there can be one or two operands. E.g. 'something' LIKE
	 * OPERAND1, 'count of something' > OPERAND1, 'count of something'
	 * BETWEEN(OPERAND1, OPERAND2)
	 */
	public int getOperandsCount() {
		return operandsCount;
	}

	@Override
	public String toString() {
		return this.sign;
	}
}
