package org.marketcetera.photon.ui;

import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPartSite;
import org.marketcetera.photon.marketdata.OptionMessageHolder;
import org.marketcetera.photon.views.OptionMarketDataView;

import quickfix.DataDictionary;
import quickfix.FieldMap;



public class OptionMessageListTableFormat extends MessageListTableFormatBase<OptionMessageHolder> {

	public OptionMessageListTableFormat(Table table, Enum[] columns, IWorkbenchPartSite site, DataDictionary dataDictionary) {
		super(table, columns, site, dataDictionary);
	}

	public OptionMessageListTableFormat(Table table, Enum[] columns, IWorkbenchPartSite site) {
		super(table, columns, site);
	}

	@Override
	public FieldMap getFieldMap(OptionMessageHolder element, int columnIndex) {
		FieldMap fieldMap = null;
		if (isCallOption(columnIndex)) {
			fieldMap = element.getCallMessage();
		} else {
			fieldMap = element.getPutMessage();
		}
		return fieldMap;
	}

	@Override
	public Object getColumnValue(OptionMessageHolder element, int columnIndex) {
		if (isStrikeColumn(columnIndex)) {
			return element.getKey().getStrikePrice();
		}
		if (isExpirationColumn(columnIndex)) {
			return element.getKey().getExpirationYear() + "-"
					+ element.getKey().getExpirationMonth();
		}
		return super.getColumnValue(element, columnIndex);
	}

	private boolean isStrikeColumn(int columnIndex) {
		return columnIndex == OptionMarketDataView.STRIKE_INDEX;
	}

	private boolean isExpirationColumn(int columnIndex) {
		return columnIndex == OptionMarketDataView.EXP_DATE_INDEX;
	}

	private boolean isCallOption(int columnIndex) {
		return (columnIndex < OptionMarketDataView.FIRST_PUT_DATA_COLUMN_INDEX);
	}


}
