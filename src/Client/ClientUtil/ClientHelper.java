package Client.ClientUtil;

import Client.ClientApp;
import Client.Controller.Controller;
import Client.Database.DatabaseManager;
import Client.GUI.GuiManager;
import Client.Network.NetworkManager;
import Util.DokoHelper;

public class ClientHelper extends DokoHelper
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

    private static ClientApp window;

    public static ClientApp getWindow()
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
