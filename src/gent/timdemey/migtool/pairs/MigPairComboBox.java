package gent.timdemey.migtool.pairs;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class MigPairComboBox extends AbstractMigPair {

    public MigPairComboBox() {
        super("Combo Box", new JComboBox<String>());        
    }

    @Override
    public String getEditMessage() {
        return "Separate the values by a comma.";
    }

    @Override
    public void setContentValue(String input) {
        String[] values = input.split(",");
        ((JComboBox<String>) getComponent()).setModel(new DefaultComboBoxModel<String>(values));      
    }

    @Override
    public String getContentValue() {
        ComboBoxModel<String> model = ((JComboBox<String>) getComponent()).getModel();
        String content = "";
        for (int i = 0; i < model.getSize(); i++) {
            content += model.getElementAt(i) + (i < model.getSize() - 1 ? "," : "");
        }
        return content;
    }
}
