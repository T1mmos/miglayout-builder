package gent.timdemey.migtool.pairs;

import javax.swing.JLabel;

public class MigPairLabel extends AbstractMigPair {
    public MigPairLabel () {
        super("JLabel", new JLabel("some label"));
    }

    @Override
    public String getEditMessage() {
        return "Set the label text";
    }

    @Override
    public void setContentValue(String input) {
        ((JLabel) getComponent()).setText(input);
    }

    @Override
    public String getContentValue() {
        return ((JLabel) getComponent()).getText();
    }
}
