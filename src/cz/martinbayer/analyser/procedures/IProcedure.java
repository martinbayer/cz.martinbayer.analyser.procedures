package cz.martinbayer.analyser.procedures;

import java.io.Serializable;

import cz.martinbayer.analyser.procedures.exception.ProcedureException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedParamException;
import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.model.XMLogData;

public interface IProcedure<T extends IXMLog> extends Serializable {

	String getName();

	/** Result of the procedure returned. It can pass the condition or not */
	boolean getResult();

	/**
	 * parameters must be configured before if any of them are used
	 * 
	 * @throws ProcedureException
	 */
	void runProcedure() throws ProcedureException;

	/** Attributes of any type can be used if the implementation allows it */
	ProcParams getPossibleParameters();

	/** Any parameter(s) can be used but data must be always configured */
	void setData(XMLogData<T> data);

	/** Contains collection of applicable operators like <, >, EQUAL, LIKE... */
	ProcOperators getOperators();

	/**  */
	ProcOperand getSelectableOperands();

	/** Set the selected parameter */
	void setSelectedParams(Object param) throws UnsupportedParamException;

	void setSelectedOperator(Object operator);

	void setOperandsValues(ProcOperand operands)
			throws UnsupportedOperandsException;
}
