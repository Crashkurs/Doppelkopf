package Client.GUI;

import Client.ClientUtil.ClientHelper;
import Client.Controller.Controller;
import Client.GUI.Game.GameGui;
import Client.GUI.Lobby.LobbyGui;
import Client.GUI.Login.LoginGui;
import Util.State;

import javax.swing.*;

public class GuiManager extends JPanel implements GuiManagerInterface
{

    private Controller controller;

    private GameGui game;
    private LobbyGui lobby;
    private LoginGui login;

    public static State state;

    public GuiManager()
    {
        controller = (Controller) ClientHelper.getController();
        game = new GameGui();
        add(game);
        lobby = new LobbyGui();
        add(lobby);
        login = new LoginGui();
        add(login);
    }

    public void setState(State _state)
    {
        if(getWidth() > 0 && getHeight() > 0)
        {
            getGui().setVisible(false);
            state = _state;
            getGui().setVisible(true);
        }else{
            System.out.println("GuiManager: setState wurde vor setBounds aufgerufen");
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
        System.out.println("Fehler im GuiManager: state ist "+state);
        return null;
    }

    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        game.setBounds(0,0,width,height);
        lobby.setBounds(0,0,width,height);
        login.setBounds(0,0,width,height);
        super.setBounds(x,y,width,height);
    }
}
