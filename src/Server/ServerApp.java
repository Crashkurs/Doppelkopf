package Server;

import Server.GUI.GuiManager;
import Server.ServerUtil.ServerHelper;
import Util.State;

import javax.swing.*;

public class ServerApp extends JFrame
{
    public static int width = 400, height = 400;

    public static void main(String[] args)
    {
        ServerApp sa = new ServerApp();
    }

    public ServerApp()
    {
        setSize(width, height);
        setTitle("Doppelkopfserver");
        setVisible(true);
        setResizable(false);

        ServerHelper.init();

        ServerHelper.getGuiManager().setBounds(0, 0, width, height);
        ServerHelper.getGuiManager().setState(State.Login);
        add(ServerHelper.getGuiManager());
    }
}
