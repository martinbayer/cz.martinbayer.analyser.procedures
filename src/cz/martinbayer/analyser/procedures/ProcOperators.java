package cz.martinbayer.analyser.procedures;

import java.io.Serializable;
import java.util.HashSet;

public class ProcOperators implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5171335479511214575L;
	private HashSet<EOperator> operators = new HashSet<>();

	public HashSet<EOperator> getOperators() {
		return this.operators;
	}

	public void addOperator(EOperator param) {
		this.operators.add(param);
	}

	public void addOperators(EOperator... params) {
		for (EOperator param : params) {
			this.operators.add(param);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProcOperators [\n");
		for (EOperator operator : this.operators) {
			sb.append(operator).append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}
}
