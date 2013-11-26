package Server.GUI;

import Server.Controller.Controller;
import Server.GUI.Game.GameGui;
import Server.GUI.Lobby.LobbyGui;
import Server.GUI.Login.LoginGui;
import Server.ServerUtil.ServerHelper;
import Util.GuiCommand;
import Util.LogType;
import Util.State;
import bsh.util.GUIConsoleInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiManager extends JPanel implements GuiManagerInterface, ActionListener
{

    private Controller controller;

    private GameGui game;
    private LobbyGui lobby;
    private LoginGui login;

    public static State state;

    public GuiManager()
    {
        super();
        setLayout(new GridBagLayout());
        controller = ServerHelper.getController();
        game = new GameGui();
        add(game);
        lobby = new LobbyGui();
        add(lobby);
        login = new LoginGui();
        login.setActionListener(this);
        add(login);

        state = State.Login;
    }

    public void setState(State _state)
    {
        if(getWidth() > 0 && getHeight() > 0)
        {
            getGui().setVisible(false);
            state = _state;
            getGui().setVisible(true);
        }else{
            ServerHelper.log(LogType.GUI, "GuiManager: setState wurde vor setBounds aufgerufen");
        }

    }

    public JPanel getGui()
    {
        if(state == State.Login)
            return (JPanel)login;
        if(state == State.Lobby)
            return (JPanel)lobby;
        if(state == State.Game)
            return (JPanel)game;
        ServerHelper.log(LogType.GUI, "Fehler im GuiManager: state ist " + state);
        return null;
    }

    public void actionPerformed(ActionEvent e)
    {
        GuiCommand command = GuiCommand.getCommand(e.getActionCommand());
        if(state == State.Login)
        {
            if(command == GuiCommand.ServerStart)
            {
                ServerHelper.getNetworkManager().connect(login.getPort());
                if(ServerHelper.getNetworkManager().isConnected())
                {
                    login.switchStartStop();
                }
            }

            if(command == GuiCommand.ServerStop)
            {
                ServerHelper.getNetworkManager().close();
                if(!ServerHelper.getNetworkManager().isConnected())
                {
                    login.switchStartStop();
                }
            }
        }
    }
}
