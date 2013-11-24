package Util;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 24.11.13
 * Klasse: ParamNotDefined
 */
public class ParamNotDefinedException extends Exception
{

    public ParamNotDefinedException(String error)
    {
        super(error);
    }
}
