package Client;

import Client.ClientUtil.ClientHelper;
import Client.GUI.GuiManager;
import Util.Message;
import Util.MessageType;
import Util.State;

import javax.swing.*;

public class ClientApp extends JFrame
{

    public static int width = 300, height = 300;

    public static void main(String[] args)
    {
        ClientApp app = new ClientApp();
    }

    public ClientApp()
    {
        setSize(width, height);
        setTitle("Doppelkopfclient");
        setVisible(true);

        ClientHelper.init();

        add(ClientHelper.getGuiManager());
        ClientHelper.getGuiManager().setBounds(200, 200, width, height);
        ClientHelper.getGuiManager().setState(State.Login);

        ClientHelper.getNetworkManager().connect("127.0.0.1", 3000);
        ClientHelper.getNetworkManager().sendMessageToAll(new Message(MessageType.SENDNAME, "Testname"));
    }
}
