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
        farbe = _farbe;
        typ = _typ;
    }

    public Karte(String incomingKarte)
    {
        String[] teile = incomingKarte.split("\\|");
        if(teile.length >= 2)
        {
            farbe = Farbe.valueOf(teile[0]);
            typ = Typ.valueOf(teile[1]);
        }else{
            DokoHelper.log(LogType.ERROR, "Karte wurde nicht ordnungsgemäß vom Client gesendet");
        }
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
        return farbe.toString() + "|" + typ.toString();
    }
}
