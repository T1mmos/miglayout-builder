package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairComboBox;

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
