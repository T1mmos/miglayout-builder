/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairPanel;

/**
 *
 * @author TDME
 */
public class MigPairPanelCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairPanel ();
    }

    @Override
    public String getDisplayValue() {
        return "Panel";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
