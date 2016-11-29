package migtool.creators;

import migtool.pairs.IMigPair;
import migtool.pairs.MigPairButton;

public class MigPairButtonCreator implements IMigPairCreator {

    @Override
    public IMigPair createComponent() {
        return new MigPairButton();
    }

    @Override
    public String getDisplayValue() {
        return "Button";
    }    

    @Override
    public String toString() {
        return getDisplayValue();
    }
}
