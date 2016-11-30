package gent.timdemey.migtool.creators;

import gent.timdemey.migtool.pairs.IMigPair;


public interface IMigPairCreator {
    public IMigPair createComponent();

    public String getDisplayValue();
}
