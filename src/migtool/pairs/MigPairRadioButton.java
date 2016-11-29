package migtool.pairs;

import javax.swing.JRadioButton;

public class MigPairRadioButton extends AbstractMigPair {

    public MigPairRadioButton() {
        super("Radio Button", new JRadioButton());
    }

    @Override
    public String getEditMessage() {
        return "Set the Radio Button's text.";
    }

    @Override
    public void setContentValue(String input) {
        ((JRadioButton) getComponent()).setText(input);
    }

    @Override
    public String getContentValue() {
        return ((JRadioButton) getComponent()).getText();
    }

}
