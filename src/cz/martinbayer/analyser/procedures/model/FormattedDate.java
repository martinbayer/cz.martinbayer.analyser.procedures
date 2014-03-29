package cz.martinbayer.analyser.procedures.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.martinbayer.utils.DateUtils;

public class FormattedDate extends Date {

	/**
	 * 
	 */
	private static final long serialVersionUID = -175312679110555532L;
	private Date value;

	public FormattedDate(Date value) {
		super(value.getTime());
		this.value = value;
	}

	@Override
	public String toString() {
		if (value == null) {
			return "";
		}
		return new SimpleDateFormat(DateUtils.DEFAULT_FORMAT_WITH_TIME)
				.format(value);
	}

}
