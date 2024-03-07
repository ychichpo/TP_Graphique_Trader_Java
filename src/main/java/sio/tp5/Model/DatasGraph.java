package sio.tp5.Model;

public class DatasGraph
{
    private String nomTrader;
    private int nbActions;

    public DatasGraph(String unNom, int unNb)
    {
        nomTrader = unNom;
        nbActions = unNb;
    }

    public String getNomTrader() {
        return nomTrader;
    }

    public int getNbActions() {
        return nbActions;
    }
}
