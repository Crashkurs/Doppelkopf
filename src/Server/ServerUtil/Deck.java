package Server.ServerUtil;

import Util.Farbe;
import Util.Karte;
import Util.Typ;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 17.11.13
 * Klasse: Deck
 */
public class Deck
{
    private ArrayList<Karte> deck, karten;

    private boolean enableNine = true;

    public Deck()
    {
        karten = new ArrayList<Karte>();
        for(Farbe farbe : Farbe.values())
        {
            for(Typ typ : Typ.values())
            {
                karten.add(new Karte(farbe,typ));
                karten.add(new Karte(farbe,typ));
            }
        }
        Collections.shuffle(karten);
        deck = karten;
    }

    public Karte ziehKarte()
    {
        Karte k = deck.remove(0);
        if(!enableNine && k.getTyp() == Typ.Neun)
        {
            return ziehKarte();
        }
        return k;
    }

    public void resetDeck()
    {
        Collections.shuffle(karten);
        deck = karten;
    }

    public void setNineEnabled(boolean enabled)
    {
        enableNine = enabled;
    }

}
