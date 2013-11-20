package Util;

public class DokoHelper
{
    private static boolean debugController = true;
    private static boolean debugDatabase = true;
    private static boolean debugGui = true;
    private static boolean debugNetwork = false;
    private static boolean debugClientUtil = true;
    private static boolean debugServerUtil = true;
    private static boolean debugUtil = true;

    public static void init()
    {

    }

    public static void log(LogType type, String logMessage)
    {
        switch(type)
        {
            case CONTROLLER:
            {
                if(debugController)
                {
                    Write("[Controller]" + logMessage);
                }
                break;
            }
            case DATABASE:
            {
                if(debugDatabase)
                {
                    Write("[Database]" + logMessage);
                }
                break;
            }
            case GUI:
            {
                if(debugGui)
                {
                    Write("[Gui]" + logMessage);
                }
                break;
            }
            case NETWORK:
            {
                if(debugNetwork)
                {
                    Write("[Network]" + logMessage);
                }
                break;
            }
            case CLIENTUTIL:
            {
                if(debugClientUtil)
                {
                    Write("[ClientUtil]" + logMessage);
                }
                break;
            }
            case SERVERUTIL:
            {
                if(debugServerUtil)
                {
                    Write("[ServerUtil]" + logMessage);
                }
                break;
            }
            case UTIL:
            {
                if(debugUtil)
                {
                    Write("[Util]" + logMessage);
                }
                break;
            }
        }
    }

    private static void Write(String message)
    {
        System.out.println(message);
    }
}
