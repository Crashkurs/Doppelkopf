package Util;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 17.11.13
 * Klasse: Karte
 */
public class Karte
{
    private Farbe farbe;
    private Typ typ;

    public Karte(Farbe _farbe, Typ _typ)
    {
        farbe = farbe;
        typ = _typ;
    }

    public Farbe getFarbe()
    {
        return farbe;
    }

    public Typ getTyp()
    {
        return typ;
    }

    public String toString()
    {
        return "(" + farbe.toString() + "|" + typ.toString() + ")";
    }
}
