package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairLabel;

public class MigPairLabelCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairLabel();
    }

    @Override
    public String getDisplayValue() {
        return "Label";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
