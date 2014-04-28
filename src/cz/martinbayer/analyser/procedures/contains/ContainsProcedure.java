package cz.martinbayer.analyser.procedures.contains;

import cz.martinbayer.analyser.impl.ConcreteE4LogsisLog;
import cz.martinbayer.analyser.procedures.IProcedure;
import cz.martinbayer.analyser.procedures.ProcOperand;
import cz.martinbayer.analyser.procedures.ProcOperators;
import cz.martinbayer.analyser.procedures.ProcParams;
import cz.martinbayer.analyser.procedures.exception.ProcedureException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperatorException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedParamException;
import cz.martinbayer.analyser.processors.model.E4LogsisLogData;
import cz.martinbayer.analyser.processors.model.ELogLevel;
import cz.martinbayer.analyser.processors.model.IE4LogsisLog;

/**
 * This
 * 
 * @author Martin
 * 
 */
public class ContainsProcedure implements IProcedure<ConcreteE4LogsisLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1533234314299073408L;
	private ProcParams procParams;
	private Object selectedParam;
	private transient E4LogsisLogData<ConcreteE4LogsisLog> data;
	private boolean result;

	public ContainsProcedure() {
		initializeParams();
	}

	private void initializeParams() {
		procParams = new ProcParams();
		for (ELogLevel level : ELogLevel.values()) {
			procParams.addParam(level);
		}
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
		for (IE4LogsisLog dataRow : this.data.getLogRecords()) {
			if (this.selectedParam.equals(ELogLevel.ALL)
					|| this.selectedParam.equals(dataRow.getLogLevel())) {
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
	public void setData(E4LogsisLogData<ConcreteE4LogsisLog> data) {
		this.data = data;
	}

	@Override
	public ProcOperators getOperators() {
		ProcOperators procOperators = new ProcOperators();
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
		return null;
	}

	@Override
	public void setOperandsValues(ProcOperand operands)
			throws UnsupportedOperandsException {

	}

	@Override
	public String getName() {
		return "contains";
	}

	@Override
	public IProcedure<ConcreteE4LogsisLog> getNewInstance() {
		return new ContainsProcedure();
	}
}
