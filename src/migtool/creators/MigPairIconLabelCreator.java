package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairIconLabel;

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
