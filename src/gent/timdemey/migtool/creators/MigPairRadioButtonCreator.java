package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;
import gent.timdemey.migtool.pairs.MigPairRadioButton;

public class MigPairRadioButtonCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairRadioButton();
    }

    @Override
    public String getDisplayValue() {
        return "Radio Button";
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
