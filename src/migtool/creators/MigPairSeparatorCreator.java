package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairSeparator;

public class MigPairSeparatorCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairSeparator();
    }

    @Override
    public String getDisplayValue() {
        return "Separator";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
