package cz.martinbayer.analyser.procedures;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = -5249070588660483616L;

	private static HashMap<Class<?>, Object> cachedEmptyObjects = new HashMap<>();
	static {
		cachedEmptyObjects.put(Integer.class, new Integer(0));
		cachedEmptyObjects.put(Double.class, new Double(0));
		cachedEmptyObjects.put(String.class, new String());
		cachedEmptyObjects.put(Date.class, new Date());
	}

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
			/* column which is not applicable for actual operator is updated */
			throw new UnsupportedOperandsException(position, 0, getSize() - 1);
		}
		/* init values if there are some set to NULL */
		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				values[i] = cachedEmptyObjects.get(this.operandType);
			}
		}
		return (T) values[position];

	}

	@Override
	public void setValue(Object value, int position)
			throws UnsupportedOperandsException {
		if (position < 0 || position > getSize() - 1) {
			throw new UnsupportedOperandsException(position, 0, getSize() - 1);
		}
		this.values[position] = parse((String) value, position);
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

	public Object parse(String value, int position)
			throws UnsupportedOperandsException {
		if (StringUtils.isEmtpy(value)) {
			return false;
		}
		try {
			Object setValue = null;
			if (this.operandType == Integer.class) {
				return Integer.parseInt(value);
			} else if (this.operandType == Double.class) {
				return Double.parseDouble(value);
			} else if (this.operandType == String.class) {
				return value;
			} else if (this.operandType == Date.class) {
				DateFormat df = new SimpleDateFormat(
						DateUtils.DEFAULT_FORMAT_WITH_TIME);
				return df.parse(value);
			}
			// if (setValue != null) {
			// setValue(setValue, position);
			// return true;
			// }

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// return false;
	}
}
