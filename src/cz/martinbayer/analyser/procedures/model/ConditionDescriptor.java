package cz.martinbayer.analyser.procedures.model;

import cz.martinbayer.analyser.procedures.EOperator;
import cz.martinbayer.analyser.procedures.IProcedure;
import cz.martinbayer.analyser.procedures.ProcOperand;
import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.types.LogProcessor;
import cz.martinbayer.utils.model.ObservableModelObject;

public class ConditionDescriptor extends ObservableModelObject {

	public static final String ENABLED = "enabled";
	public static final String SELECTED_PROCEDURE = "selectedProcedure";
	public static final String SELECTED_PROC_OPERANDS = "selectedProcOperands";
	public static final String SELECTED_PROC_PARAM = "selectedProcParam";
	public static final String SELECTED_PROC_OPERATOR = "selectedProcOperator";
	public static final String SELECTED_PROCESSOR = "selectedProcessor";

	private boolean isEnabled;
	private IProcedure selectedProcedure;
	private ProcOperand selectedProcOperands;
	private Object selectedProcParam;
	private EOperator selectedProcOperator;
	private LogProcessor<IXMLog> selectedProcessor;

	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public final IProcedure getSelectedProcedure() {
		return selectedProcedure;
	}

	public final void setSelectedProcedure(IProcedure procedure) {
		if (this.selectedProcedure != procedure) {
			updateParams(procedure);
			firePropertyChange(SELECTED_PROCEDURE, this.selectedProcedure,
					this.selectedProcedure = procedure);
		}
	}

	private void updateParams(IProcedure procedure) {
		setSelectedProcOperands(null);
		this.selectedProcOperator = null;
		this.selectedProcParam = null;
	}

	public final ProcOperand getSelectedProcOperands() {
		return selectedProcOperands;
	}

	public final void setSelectedProcOperands(ProcOperand selectedProcOperands) {
		firePropertyChange(SELECTED_PROC_OPERANDS, null,
				this.selectedProcOperands = selectedProcOperands);
	}

	public final Object getSelectedProcParam() {
		return selectedProcParam;
	}

	public final void setSelectedProcParam(Object selectedProcParam) {
		this.selectedProcParam = selectedProcParam;
	}

	public final EOperator getSelectedProcOperator() {
		return selectedProcOperator;
	}

	public final void setSelectedProcOperator(EOperator selectedProcOperator) {
		updateOperands(this.selectedProcOperator, selectedProcOperator);
		this.selectedProcOperator = selectedProcOperator;

	}

	private void updateOperands(EOperator oldOperator, EOperator newOperator) {
		if (selectedProcOperands == null) {
			selectedProcOperands = selectedProcedure.getSelectableOperands();
		}
		selectedProcOperands.setValuesSize(newOperator.getOperandsCount());
	}

	public final LogProcessor<IXMLog> getSelectedProcessor() {
		return selectedProcessor;
	}

	public final void setSelectedProcessor(
			LogProcessor<IXMLog> selectedProcessor) {
		firePropertyChange(SELECTED_PROCESSOR, this.selectedProcessor,
				this.selectedProcessor = selectedProcessor);
	}

}
