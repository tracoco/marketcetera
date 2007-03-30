package org.marketcetera.photon.ui.validation.fix;

import org.eclipse.swt.widgets.Text;

import quickfix.DataDictionary;
import quickfix.Message;

public class FIXTextExtractor extends AbstractFIXExtractor {
	protected final Text field;

	public FIXTextExtractor(Text field, int fieldNum, DataDictionary dictionary) {
		super(field, fieldNum, dictionary);
		this.field = field;
	}

	public void modifyOrder(Message aMessage){
		String text = field.getText();
		if (text != null && text.length() >0)
			insertString(aMessage, text);
	}

	@Override
	public void updateUI(Message aMessage) {
		field.setText(extractString(aMessage));
	}

	@Override
	public void clearUI() {
		field.setText("");
	}
	
	
	
}
