package migtool.pairs;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface IMigPair {

    public JComponent getComponent();

    public String getConstraints();

    public void setConstraints(String constraints);

    public JLabel getLabel();

    public JTextField getTextField();

    public JButton getButtonUp();

    public JButton getButtonDown();

    public JButton getButtonEdit();

    public JButton getButtonDelete();

    public boolean isEditable ();
    
    public String getEditMessage();

    public void setContentValue(String input);
    
    public String getContentValue();
}
