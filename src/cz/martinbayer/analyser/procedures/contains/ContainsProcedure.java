package cz.martinbayer.analyser.procedures.contains;

import cz.martinbayer.analyser.procedures.EOperator;
import cz.martinbayer.analyser.procedures.IProcedure;
import cz.martinbayer.analyser.procedures.ProcOperand;
import cz.martinbayer.analyser.procedures.ProcOperators;
import cz.martinbayer.analyser.procedures.ProcParams;
import cz.martinbayer.analyser.procedures.exception.ProcedureException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperatorException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedParamException;
import cz.martinbayer.analyser.processors.model.ELogLevel;
import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.model.XMLogData;

/**
 * This
 * 
 * @author Martin
 * 
 */
public class ContainsProcedure implements IProcedure {

	private ProcParams procParams;
	private Object selectedParam;
	private XMLogData<IXMLog> data;
	private boolean result;

	public ContainsProcedure() {
		initializeParams();
	}

	private void initializeParams() {
		procParams = new ProcParams();
		procParams.addParam(ELogLevel.WARN);
		procParams.addParam(ELogLevel.ERROR);
	}

	@Override
	public boolean getResult() {
		return this.result;
	}

	@Override
	public void runProcedure() throws ProcedureException {
		if (this.selectedParam == null) {
			throw new UnsupportedParamException("NULL", this.procParams);
		}
		int procedureResult = 0;
		for (IXMLog dataRow : this.data.getLogRecords()) {
			if (this.selectedParam.equals(dataRow.getLogLevel())) {
				procedureResult++;
			}
		}
		// set the result
		this.result = procedureResult > 0;
	}

	@Override
	public ProcParams getPossibleParameters() {
		return procParams;
	}

	@Override
	public void setSelectedParams(Object param)
			throws UnsupportedParamException {
		if (!procParams.getParams().contains(param)) {
			throw new UnsupportedParamException(param, procParams);
		}
		this.selectedParam = param;
	}

	@Override
	public void setData(XMLogData<IXMLog> data) {
		this.data = data;
	}

	@Override
	public ProcOperators getOperators() {
		ProcOperators procOperators = new ProcOperators();
		procOperators.addOperators(EOperator.BTW, EOperator.EQUALS,
				EOperator.GT, EOperator.GTEQUAL, EOperator.LT,
				EOperator.LTEQUAL);
		// no operators are available
		return procOperators;
	}

	@Override
	public void setSelectedOperator(Object operator) {
		try {
			throw new UnsupportedOperatorException(operator,
					new ProcOperators());
		} catch (UnsupportedOperatorException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ProcOperand getSelectableOperands() {
		ProcOperand ops = new ProcOperand();
		ops.addSelectableOperand("AHOJ");
		ops.addSelectableOperand("HI");
		ops.addSelectableOperand("BON JOUR");

		return ops;
	}

	@Override
	public void setOperandsValues(ProcOperand operands)
			throws UnsupportedOperandsException {

	}

	@Override
	public String getName() {
		return "contains";
	}
}
