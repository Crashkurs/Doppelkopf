package Util;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 26.11.13
 * Klasse: GuiCommand
 */
public enum GuiCommand
{
    ServerStart,
    ServerStop,
    GameStart;

    public static GuiCommand getCommand(String command)
    {
        for(GuiCommand gc : GuiCommand.values())
        {
            if(gc.name().equals(command))
                return gc;
        }
        return null;
    }
}
