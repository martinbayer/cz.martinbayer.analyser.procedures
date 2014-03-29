package cz.martinbayer.analyser.procedures.exception;

import cz.martinbayer.analyser.procedures.EOperator;
import cz.martinbayer.analyser.procedures.ProcOperators;

public class UnsupportedOperatorException extends ProcedureException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653221572843658734L;

	public UnsupportedOperatorException(Object operator,
			ProcOperators possibleOperators) {
		super(
				String.format(
						"Operator %s cannot be used. The only operators available are: %s",
						operator, possibleOperators.toString()));
	}

	public UnsupportedOperatorException(EOperator selectedOperator,
			Class<?> clazz) {
		super(String.format("%s operator is not allowed for %s",
				selectedOperator.toString(), clazz.toString()));
	}
}
