package migtool.pairs;

import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;



public abstract class AbstractMigPair implements IMigPair {
    protected String constraints = "";
    private final JLabel label;
    private final JTextField field;
    private final JComponent comp;
    private final JButton buttonUp, buttonDown, buttonEdit, buttonDelete;
    
    protected AbstractMigPair (String labelText, JComponent comp) {
        this.label = new JLabel(labelText);
        this.field = new JTextField();      
        this.comp = comp;
        this.buttonUp = createButton();
        this.buttonDown = createButton();
        this.buttonEdit = createButton();
        this.buttonDelete = createButton();
    }
    
    @Override
    public String getConstraints() {
        return constraints;
    }

    @Override
    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }
    
    @Override
    public JLabel getLabel() {
        return label;
    }
    
    @Override
    public JTextField getTextField() {
        return field;
    }
    
    @Override
    public JComponent getComponent() {
        return comp;
    }
    
    private JButton createButton() {
        JButton button = new JButton() {
            {
                setContentAreaFilled(false);
                setBorderPainted(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        };
        return button;
    }
    
    @Override
    public boolean isEditable() {
        return true;
    };
    
    @Override
    public JButton getButtonUp() {
        return buttonUp;
    }
    
    @Override
    public JButton getButtonDown() {
        return buttonDown;
    }
    
    @Override
    public JButton getButtonEdit() {
        return buttonEdit;
    }
    
    @Override
    public JButton getButtonDelete() {
        return buttonDelete;
    }
}
