package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairSeparator;

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
