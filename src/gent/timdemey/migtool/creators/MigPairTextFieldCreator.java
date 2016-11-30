package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairTextField;

public class MigPairTextFieldCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairTextField();
    }

    @Override
    public String getDisplayValue() {
        return "Text Field";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
