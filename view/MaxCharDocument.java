package view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxCharDocument extends PlainDocument {
	private int limite;

	public MaxCharDocument(int limite) {
		this.limite = limite;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str != null) {
			if ((getLength() + str.length()) <= limite) {
				super.insertString(offs, str, a);
			} else {
				String substr = str.substring(0, limite - getLength());
				super.insertString(offs, substr, a);
			}
		}
	}
} // fim da classe MaxCharDocument