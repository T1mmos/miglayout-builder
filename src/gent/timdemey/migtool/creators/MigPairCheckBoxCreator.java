package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairCheckBox;

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
