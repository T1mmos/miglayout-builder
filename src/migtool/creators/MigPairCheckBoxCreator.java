package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairCheckBox;

public class MigPairCheckBoxCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairCheckBox();
    }

    @Override
    public String getDisplayValue() {
        return "Check Box";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
