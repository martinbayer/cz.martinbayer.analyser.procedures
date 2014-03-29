package cz.martinbayer.analyser.procedures.exception;

import cz.martinbayer.analyser.procedures.ProcParams;

public class UnsupportedParamException extends ProcedureException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653221572843658734L;

	public UnsupportedParamException(Object param, ProcParams possibleParameters) {
		super(
				String.format(
						"Parameter %s cannot be used. The only parameters available are: %s",
						param, possibleParameters.toString()));
	}
}
