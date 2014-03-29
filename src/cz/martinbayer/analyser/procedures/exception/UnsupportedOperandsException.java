package cz.martinbayer.analyser.procedures.exception;

import java.util.ArrayList;

public class UnsupportedOperandsException extends ProcedureException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2842032051953993810L;

	/** Use this constructor when count of operands is not correct */
	public UnsupportedOperandsException(int operandsCount,
			int neededOperandsCount) {
		super(
				String.format(
						"Unsupported operands count used. %d operands needed, but %d operands used",
						neededOperandsCount, operandsCount));
	}

	public UnsupportedOperandsException(Object value,
			ArrayList<Object> selectableOperands) {
		super(
				String.format(
						"Value [%s] is not valid for collection of available operands %s",
						value.toString(), formatOperands(selectableOperands)
								.toString()));
	}

	public UnsupportedOperandsException(int position, int minIndex, int maxIndex) {
		super(String.format(
				"Value index must be between %d and %d. Index %d is not valid",
				minIndex, maxIndex, position));
	}

	private static StringBuffer formatOperands(
			ArrayList<Object> selectableOperands) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (Object o : selectableOperands) {
			sb.append(o).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb;
	}
}
