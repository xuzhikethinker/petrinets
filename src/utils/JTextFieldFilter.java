package utils;

/**
 *
 * @author Aloren
 */
import javax.swing.text.*;
import javax.swing.text.BadLocationException;

public class JTextFieldFilter extends PlainDocument {

    public static final String NUMERIC =
            "0123456789";
    public static final String LastDays = "0123456789";
    public static final String BINARY = "01";
    protected String acceptedChars = null;

    public JTextFieldFilter() {
        this(NUMERIC);
    }

    public JTextFieldFilter(String acceptedchars) {
        acceptedChars = acceptedchars;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1) {
                return;
            }
        }

        super.insertString(offset, str, attr);
    }
}