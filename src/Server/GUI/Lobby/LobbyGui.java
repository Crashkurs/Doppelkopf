package Server.GUI.Lobby;

import Util.GuiCommand;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LobbyGui extends JPanel
{
    private HashMap<String, JLabel> clients;
    private JButton startGame, serverStop;

    public LobbyGui()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        clients = new HashMap<String, JLabel>();

        startGame = new JButton("Spiel starten");
        startGame.setActionCommand(GuiCommand.GameStart.name());
        add(startGame);

        serverStop = new JButton("Stoppe Server");
        serverStop.setActionCommand(GuiCommand.ServerStop.name());
        add(serverStop);
    }

    public void setActionListener(ActionListener al)
    {
        startGame.addActionListener(al);
        serverStop.addActionListener(al);
    }

    public void addClientToLobby(String clientname)
    {
        JLabel tmp = new JLabel(clientname);
        add(tmp);
        clients.put(clientname, tmp);
        revalidate();
    }

    public void removeClientFromLobby(String clientname)
    {
        if(clients.containsKey(clientname))
        {
            JLabel tmp = clients.get(clientname);
            remove(tmp);
            clients.remove(tmp);
        }
        revalidate();
    }

    public void clearClients()
    {
        for(JLabel client: clients.values())
            remove(client);
        clients.clear();
    }
}
