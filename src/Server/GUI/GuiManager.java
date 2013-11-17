package Server.GUI;

import Server.Controller.Controller;
import Server.GUI.Game.GameGui;
import Server.GUI.Lobby.LobbyGui;
import Server.GUI.Login.LoginGui;
import Server.ServerUtil.ServerHelper;

import javax.swing.*;

public class GuiManager extends JPanel implements GuiManagerInterface
{

    private Controller controller;
    private int state = 0;

    private GameGui game;
    private LobbyGui lobby;
    private LoginGui login;

    public static int loginState = 0, lobbyState = 1, gameState = 2;

    public GuiManager()
    {
        controller = (Controller) ServerHelper.getController();
        game = new GameGui();
        add(game);
        lobby = new LobbyGui();
        add(lobby);
        login = new LoginGui();
        add(login);
    }

    public void setState(int _state)
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
        if(state == loginState)
            return (JPanel)login;
        if(state == lobbyState)
            return (JPanel)lobby;
        if(state == gameState)
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
