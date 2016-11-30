/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gent.timdemey.migtool.pairs;

import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author TDME
 */
public class MigPairPanel extends AbstractMigPair {

    private static final Random rg = new Random ();
    
    public MigPairPanel() {
        super("Panel", MigPairPanel.newColorPanel());
    }
    
    private static JPanel newColorPanel (){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(rg.nextInt(1 << 24)));
        return panel;
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
