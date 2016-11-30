package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairComboBox;

public class MigPairComboBoxCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairComboBox();
    }

    @Override
    public String getDisplayValue() {
        return "Combo Box";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
