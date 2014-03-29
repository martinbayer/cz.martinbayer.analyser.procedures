package cz.martinbayer.analyser.procedures.countof;

import cz.martinbayer.analyser.procedures.EOperator;
import cz.martinbayer.analyser.procedures.ProcOperand;
import cz.martinbayer.analyser.procedures.TypeProcOperand;
import cz.martinbayer.analyser.procedures.exception.ProcedureException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperatorException;

class CountOfProcedureAlgorithm {

	public static boolean resolveProcedure(EOperator selectedOperator,
			TypeProcOperand<Integer> operand, int procedureResult)
			throws ProcedureException {
		switch (selectedOperator) {
		case BTW:
			if (operand.getSize() != 2) {
				throw new UnsupportedOperandsException(operand.getSize(), 2);
			}
			return operand.getValue(ProcOperand.FIRST_OPERAND) <= procedureResult
					&& procedureResult <= operand
							.getValue(ProcOperand.FIRST_OPERAND);
		default:
			// check another options in next switch to check the arguments
			// count;
		}
		if (operand.getSize() != 1) {
			throw new UnsupportedOperandsException(operand.getSize(), 1);
		}
		int finalOperand = operand.getValue(ProcOperand.FIRST_OPERAND);
		switch (selectedOperator) {
		case EQUALS:
			return finalOperand == procedureResult;
		case GT:
			return finalOperand < procedureResult;
		case GTEQUAL:
			return finalOperand <= procedureResult;
		case LT:
			return finalOperand > procedureResult;
		case LTEQUAL:
			return finalOperand >= procedureResult;
		default:
			throw new UnsupportedOperatorException(selectedOperator,
					CountOfProcedureAlgorithm.class);
		}
	}
}
