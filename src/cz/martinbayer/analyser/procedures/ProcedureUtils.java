package cz.martinbayer.analyser.procedures;

import java.util.Collection;

public class ProcedureUtils {

	public static final int getMaxOperands(Collection<IProcedure> procedures) {
		int max = 0;
		for (IProcedure proc : procedures) {
			for (EOperator operator : proc.getOperators().getOperators()) {
				if (max < operator.getOperandsCount()) {
					max = operator.getOperandsCount();
				}
			}
		}
		return max;
	}
}
