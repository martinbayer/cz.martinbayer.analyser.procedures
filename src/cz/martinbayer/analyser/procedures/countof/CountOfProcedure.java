package cz.martinbayer.analyser.procedures.countof;

import java.util.Date;

import cz.martinbayer.analyser.impl.ConcreteXMLog;
import cz.martinbayer.analyser.procedures.EOperator;
import cz.martinbayer.analyser.procedures.IProcedure;
import cz.martinbayer.analyser.procedures.ProcOperand;
import cz.martinbayer.analyser.procedures.ProcOperators;
import cz.martinbayer.analyser.procedures.ProcParams;
import cz.martinbayer.analyser.procedures.TypeProcOperand;
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
public class CountOfProcedure implements IProcedure<ConcreteXMLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -560015766959239053L;
	private ProcParams procParams;
	private Object selectedParam;
	private ProcOperators procOperators;
	private EOperator selectedOperator;
	private XMLogData<ConcreteXMLog> data;
	private boolean result;
	private TypeProcOperand<Integer> operand;

	public CountOfProcedure() {
		initializeParams();
		initializeOperators();
	}

	private void initializeOperators() {
		procOperators = new ProcOperators();
		procOperators.addOperators(EOperator.BTW, EOperator.EQUALS,
				EOperator.GT, EOperator.GTEQUAL, EOperator.LT,
				EOperator.LTEQUAL);
	}

	private void initializeParams() {
		procParams = new ProcParams();
		procParams.addParam(ELogLevel.DEBUG);
		procParams.addParam(ELogLevel.WARN);
		procParams.addParam(ELogLevel.ERROR);
		procParams.addParam(ELogLevel.TRACE);
		procParams.addParam(ELogLevel.INFO);
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
		if (this.selectedOperator == null) {
			throw new UnsupportedOperatorException("NULL", this.procOperators);
		}
		if (this.operand == null
				|| this.operand.getSize() < this.selectedOperator
						.getOperandsCount()) {
			throw new UnsupportedOperandsException(0,
					this.selectedOperator.getOperandsCount());
		}
		int procedureResult = 0;
		for (IXMLog dataRow : this.data.getLogRecords()) {
			if (this.selectedParam.equals(dataRow.getLogLevel())) {
				procedureResult++;
			}
		}
		this.result = CountOfProcedureAlgorithm.resolveProcedure(
				this.selectedOperator, this.operand, procedureResult);
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
	public void setData(XMLogData<ConcreteXMLog> data) {
		this.data = data;
	}

	@Override
	public ProcOperators getOperators() {
		return procOperators;
	}

	@Override
	public void setSelectedOperator(Object operator) {
		if (!procOperators.getOperators().contains(operator))
			this.selectedOperator = (EOperator) operator;
	}

	@Override
	public ProcOperand getSelectableOperands() {
		return TypeProcOperand.getInstance(Date.class);
	}

	@Override
	public void setOperandsValues(ProcOperand operands)
			throws UnsupportedOperandsException {
		if (this.selectedOperator.getOperandsCount() != operands.getSize()) {
			throw new UnsupportedOperandsException(operands.getSize(),
					this.selectedOperator.getOperandsCount());
		}
		this.operand = (TypeProcOperand<Integer>) operands;
	}

	@Override
	public String getName() {
		return "countOf";
	}
}
