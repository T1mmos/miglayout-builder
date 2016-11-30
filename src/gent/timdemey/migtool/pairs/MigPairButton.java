package gent.timdemey.migtool.pairs;

import javax.swing.JButton;

public class MigPairButton extends AbstractMigPair {

    public MigPairButton() {
        super("Button", new JButton("Test"));
    }

    @Override
    public String getEditMessage() {
        return "Set the button text.";
    }

    @Override
    public void setContentValue(String input) {
        ((JButton) getComponent()).setText(input);
    }

    @Override
    public String getContentValue() {
        return ((JButton) getComponent()).getText();
    }
}
