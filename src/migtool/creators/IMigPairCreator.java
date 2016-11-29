package migtool.creators;

import migtool.pairs.IMigPair;


public interface IMigPairCreator {
    public IMigPair createComponent();

    public String getDisplayValue();
}
