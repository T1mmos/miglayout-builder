package gent.timdemey.migtool.pairs;

import javax.swing.JTextField;

public class MigPairTextField extends AbstractMigPair {

    public MigPairTextField() {
        super("Text Field", new JTextField());
    }

    @Override
    public String getEditMessage() {
        return "Set the Text Field's text.";
    }

    @Override
    public void setContentValue(String input) {
        ((JTextField) getComponent()).setText(input);
    }

    @Override
    public String getContentValue() {
        return ((JTextField) getComponent()).getText();
    }

}
