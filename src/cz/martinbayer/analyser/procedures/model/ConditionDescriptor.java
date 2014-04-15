package cz.martinbayer.analyser.procedures.model;

import java.io.Serializable;

import org.eclipse.e4.core.services.log.Logger;

import cz.martinbayer.analyser.procedures.EOperator;
import cz.martinbayer.analyser.procedures.IProcedure;
import cz.martinbayer.analyser.procedures.ProcOperand;
import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;
import cz.martinbayer.analyser.procedures.exception.UnsupportedParamException;
import cz.martinbayer.analyser.processors.model.IXMLog;
import cz.martinbayer.analyser.processors.types.LogProcessor;
import cz.martinbayer.e4.analyser.LoggerFactory;
import cz.martinbayer.utils.model.ObservableModelObject;

public class ConditionDescriptor<T extends IXMLog> extends
		ObservableModelObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982735198573570481L;

	private static Logger logger = LoggerFactory
			.getInstance(ConditionDescriptor.class);

	public static final String ENABLED = "enabled";
	public static final String SELECTED_PROCEDURE = "selectedProcedure";
	public static final String SELECTED_PROC_OPERANDS = "selectedProcOperands";
	public static final String SELECTED_PROC_PARAM = "selectedProcParam";
	public static final String SELECTED_PROC_OPERATOR = "selectedProcOperator";
	public static final String SELECTED_PROCESSOR = "selectedProcessor";

	private boolean isEnabled;
	private IProcedure<T> selectedProcedure;
	private ProcOperand selectedProcOperands;
	private Object selectedProcParam;
	private EOperator selectedProcOperator;
	private LogProcessor<T> selectedProcessor;

	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public final IProcedure<T> getSelectedProcedure() {
		return selectedProcedure;
	}

	public final void setSelectedProcedure(IProcedure<T> procedure) {
		if (this.selectedProcedure != procedure) {
			firePropertyChange(SELECTED_PROCEDURE, this.selectedProcedure,
					this.selectedProcedure = procedure);
			updateParams(procedure);
		}
	}

	private void updateParams(IProcedure<T> procedure) {
		try {
			setSelectedProcOperands(null);
		} catch (UnsupportedOperandsException e) {
			logger.error("Wrong operand set", e);
		}
		this.selectedProcOperator = null;
		this.selectedProcParam = null;
	}

	public final ProcOperand getSelectedProcOperands() {
		return selectedProcOperands;
	}

	public final void setSelectedProcOperands(ProcOperand selectedProcOperands)
			throws UnsupportedOperandsException {
		firePropertyChange(SELECTED_PROC_OPERANDS, null,
				this.selectedProcOperands = selectedProcOperands);
		if (selectedProcedure != null) {
			selectedProcedure.setOperandsValues(selectedProcOperands);
		}
	}

	public final Object getSelectedProcParam() {
		return selectedProcParam;
	}

	public final void setSelectedProcParam(Object selectedProcParam)
			throws UnsupportedParamException {
		this.selectedProcParam = selectedProcParam;
		if (selectedProcedure != null) {
			selectedProcedure.setSelectedParams(selectedProcParam);
		}
	}

	public final EOperator getSelectedProcOperator() {
		return selectedProcOperator;
	}

	public final void setSelectedProcOperator(EOperator selectedProcOperator) {
		updateOperands(this.selectedProcOperator, selectedProcOperator);
		this.selectedProcOperator = selectedProcOperator;
		if (selectedProcedure != null) {
			selectedProcedure.setSelectedOperator(this.selectedProcOperator);
		}

	}

	private void updateOperands(EOperator oldOperator, EOperator newOperator) {
		if (selectedProcOperands == null) {
			selectedProcOperands = selectedProcedure.getSelectableOperands();
		}
		selectedProcOperands.setValuesSize(newOperator.getOperandsCount());
	}

	public final LogProcessor<T> getSelectedProcessor() {
		return selectedProcessor;
	}

	public final void setSelectedProcessor(LogProcessor<T> selectedProcessor) {
		firePropertyChange(SELECTED_PROCESSOR, this.selectedProcessor,
				this.selectedProcessor = selectedProcessor);
	}

	public boolean isRunnable() {
		boolean result = true;
		/* processor must be enabled */
		result &= this.isEnabled();

		/* procedure must be configured */
		result &= this.getSelectedProcedure() != null;

		/* output processor must be set for descriptor */
		result &= this.getSelectedProcessor() != null;
		return result;
	}
}
