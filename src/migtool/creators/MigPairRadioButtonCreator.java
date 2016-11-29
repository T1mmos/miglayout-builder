package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairRadioButton;

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
