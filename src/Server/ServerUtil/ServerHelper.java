package Server.ServerUtil;

import Server.Controller.Controller;
import Server.Database.DatabaseManager;
import Server.GUI.GuiManager;
import Server.Network.NetworkManager;
import Server.ServerApp;
import Util.DokoHelper;

public class ServerHelper extends DokoHelper
{

    public static void init()
    {
        DokoHelper.init();
        controller = new Controller();
        networkManager = new NetworkManager();
        guiManager = new GuiManager();
        databaseManager = new DatabaseManager();
        System.gc();
    }

    private static ServerApp window;

    public static ServerApp getWindow()
    {
        return window;
    }

    private static Controller controller;

    public static Controller getController()
    {
        if(controller == null)
            init();
        return controller;
    }

    private static NetworkManager networkManager;

    public static NetworkManager getNetworkManager()
    {
        if(networkManager == null)
            init();
        return networkManager;
    }

    private static GuiManager guiManager;

    public static GuiManager getGuiManager()
    {
        if(guiManager == null)
            init();
        return guiManager;
    }

    private static DatabaseManager databaseManager;

    public static DatabaseManager getDatabaseManager()
    {
        if(databaseManager == null)
            init();
        return databaseManager;
    }
}
