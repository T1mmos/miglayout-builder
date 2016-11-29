package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairTextField;

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
