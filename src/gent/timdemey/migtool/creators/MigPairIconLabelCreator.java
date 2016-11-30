package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairIconLabel;

public class MigPairIconLabelCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairIconLabel();
    }

    @Override
    public String getDisplayValue() {
        return "Icon Label";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
