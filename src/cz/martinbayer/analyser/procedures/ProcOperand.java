package cz.martinbayer.analyser.procedures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;

/**
 * Used to specify collections of operands if raw types like String, Integer
 * etc. are not applicable. This collection should be rendered as combobox
 * 
 * @author Martin
 * 
 */
public class ProcOperand implements Serializable {

	/** first operand's index in array */
	public static final int FIRST_OPERAND = 0;

	/** second operand's index in array */
	public static final int SECOND_OPERAND = 1;

	/** all selectable operands */
	private ArrayList<Object> selectableOperands = new ArrayList<>();

	protected Object[] values = new Object[] {};

	/**
	 * Set should contain only the operands which cannot be some of the raw Java
	 * type. Other types can be gained via static properties like
	 * {@link TypeProcOperand#STRING}
	 */
	public final ArrayList<Object> getSelectableOperands() {
		return this.selectableOperands;
	};

	public void addSelectableOperand(Object operand) {
		if (!selectableOperands.contains(operand)) {
			selectableOperands.add(operand);
		}
	}

	/**
	 * Count of operands set to the ProcOperand instance can be controlled via
	 * operator's max operands count. If size is decreased then array is cut so
	 * some items can be lost
	 */
	public void setValuesSize(int size) {
		this.values = Arrays.copyOf(this.values, size);
	}

	public void setValue(Object value, int position)
			throws UnsupportedOperandsException {
		if (!this.selectableOperands.contains(value)) {
			throw new UnsupportedOperandsException(value,
					this.selectableOperands);
		}
		if (position < 0 || position > getSize() - 1) {
			throw new UnsupportedOperandsException(position, 0, getSize() - 1);
		}
		values[position] = value;
	}

	/**
	 * Never returns NULL. It returns empty String instead of NULL
	 * 
	 * @param position
	 * @return
	 * @throws UnsupportedOperandsException
	 */
	public Object getValue(int position) throws UnsupportedOperandsException {
		if (position < 0 || position > getSize() - 1) {
			throw new UnsupportedOperandsException(position, 0, getSize() - 1);
		}
		return values[position] == null ? "" : values[position];
	}

	public int getSize() {
		return this.values.length;
	}
}
