package migtool.pairs;

import javax.swing.JLabel;

import migtool.MigBuildingTool;

public class MigPairIconLabel extends AbstractMigPair {

    public MigPairIconLabel() {
        super("Icon Label", new JLabel(MigBuildingTool.getIcon("random")));
    }

    @Override
    public String getEditMessage() {
        return "Set the Icon Label's text";
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
