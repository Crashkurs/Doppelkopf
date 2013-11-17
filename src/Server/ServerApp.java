package Server;

import Server.GUI.GuiManager;
import Server.ServerUtil.ServerHelper;
import Util.State;

import javax.swing.*;

public class ServerApp extends JFrame
{
    public static int width = 1024, height = 768;

    public static void main(String[] args)
    {
        ServerApp sa = new ServerApp();
    }

    public ServerApp()
    {
        setSize(width, height);
        setTitle("Doppelkopfclient");
        setVisible(true);

        ServerHelper.init();

        add(ServerHelper.getGuiManager());
        ServerHelper.getGuiManager().setBounds(0, 0, width, height);
        ServerHelper.getGuiManager().setState(State.Login);

        ServerHelper.getNetworkManager().connect(3000);
    }
}
