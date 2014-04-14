package cz.martinbayer.analyser.procedures;

import java.io.Serializable;
import java.util.HashSet;

/** Collection of possible parameters of the function */
public class ProcParams implements Serializable {

	private HashSet<Object> params = new HashSet<>();

	public HashSet<Object> getParams() {
		return this.params;
	}

	/** Added parameters must be correctly handled by used function */
	public void addParam(Object param) {
		this.params.add(param);
	}
}
