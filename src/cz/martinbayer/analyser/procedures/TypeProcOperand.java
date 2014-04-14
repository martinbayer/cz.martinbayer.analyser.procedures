package cz.martinbayer.analyser.procedures;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.e4.core.services.log.Logger;

import cz.martinbayer.analyser.procedures.exception.UnsupportedOperandsException;
import cz.martinbayer.analyser.procedures.model.FormattedDate;
import cz.martinbayer.e4.analyser.LoggerFactory;
import cz.martinbayer.utils.DateUtils;
import cz.martinbayer.utils.StringUtils;

/**
 * Type operand is used to know which constraint used and how to parse the value
 * which is given by the user. These are only Java raw types and their Class<?>
 * representation
 */
public class TypeProcOperand<T extends Object> extends ProcOperand {
	private static Logger logger = LoggerFactory
			.getInstance(TypeProcOperand.class);

	private Class<T> operandType;

	public static TypeProcOperand<?> getInstance(Class<?> clazz) {
		return new TypeProcOperand<>(clazz);
	}

	private TypeProcOperand(Class<T> operandType) {
		this.operandType = operandType;
		final T[] values = (T[]) Array.newInstance(operandType, 0);
		this.values = values;
	}

	@Override
	public T getValue(int position) throws UnsupportedOperandsException {
		if (position < 0 || position > getSize() - 1) {
			throw new UnsupportedOperandsException(position, 0, getSize() - 1);
		}
		try {
			return values[position] == null ? this.operandType.newInstance()
					: (T) values[position];
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e, "Unable to create new instance");
		}
		return null;
	}

	@Override
	public void setValue(Object value, int position)
			throws UnsupportedOperandsException {
		if (position < 0 || position > getSize() - 1) {
			throw new UnsupportedOperandsException(position, 0, getSize() - 1);
		}
		this.values[position] = getFormattedValue(value);
	}

	private Object getFormattedValue(Object value) {
		if (this.operandType == Date.class) {
			value = new FormattedDate((Date) value);
		}
		return value;
	}

	public Class<T> getType() {
		return operandType;
	}

	public boolean parse(String value, int position)
			throws UnsupportedOperandsException {
		if (StringUtils.isEmtpy(value)) {
			return false;
		}
		try {
			Object setValue = null;
			if (this.operandType == Integer.class) {
				setValue = Integer.parseInt(value);
			} else if (this.operandType == Double.class) {
				setValue = Double.parseDouble(value);
			} else if (this.operandType == String.class) {
				setValue = value;
			} else if (this.operandType == Date.class) {
				DateFormat df = new SimpleDateFormat(
						DateUtils.DEFAULT_FORMAT_WITH_TIME);
				setValue = df.parse(value);
			}
			if (setValue != null) {
				setValue(setValue, position);
				return true;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getSize() {
		return values.length;
	}
}
