package Util;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 20.11.13
 * Klasse: MessageType
 */
public enum MessageType
{
    GENERAL,        //Dieser Typ muss erhalten bleiben, wird als Standardwert gesetzt
    CLOSE,
    JOINLOBBY,
    JOINGAME,
    INVALIDNAME,
    LEAVELOBBY,
    LEAVEGAME,
    PLAYCARD,
    SENDNAME,
    UPDATEPLAYERNAMES,
}
