package migtool.pairs;

import javax.swing.JSeparator;

public class MigPairSeparator extends AbstractMigPair {

    public MigPairSeparator() {
        super("Separator", new JSeparator());
    }
    @Override
    public boolean isEditable() {
        return false;
    }
    @Override
    public String getEditMessage() {
        return null;
    }

    @Override
    public void setContentValue(String input) {
    }

    @Override
    public String getContentValue() {
        return null;
    }

}
