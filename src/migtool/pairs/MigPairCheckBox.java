package migtool.pairs;

import javax.swing.JCheckBox;

public class MigPairCheckBox extends AbstractMigPair {

    public MigPairCheckBox() {
        super("Check Box", new JCheckBox());
    }

    @Override
    public String getEditMessage() {
        return "Set the Check Box's text";
    }

    @Override
    public void setContentValue(String input) {
        ((JCheckBox) getComponent()).setText(input);
    }

    @Override
    public String getContentValue() {
        return ((JCheckBox) getComponent()).getText();
    }

}
