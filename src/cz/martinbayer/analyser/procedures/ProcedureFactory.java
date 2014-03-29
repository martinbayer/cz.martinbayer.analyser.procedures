package cz.martinbayer.analyser.procedures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import cz.martinbayer.analyser.procedures.contains.ContainsProcedure;
import cz.martinbayer.analyser.procedures.countof.CountOfProcedure;

public class ProcedureFactory {

	private static ArrayList<IProcedure> set = new ArrayList<>();
	static {
		set.add(new CountOfProcedure());
		set.add(new ContainsProcedure());

	}

	/**
	 * Method returns <b>UNMODIFIABLE collection of the procedure which are
	 * prepared to be used for the condition functionality</b>
	 * 
	 * @return - unmodifiable collection of IProcedure instances
	 */
	public static final Collection<IProcedure> getProcedures() {
		return Collections.unmodifiableList(set);
	}
}
