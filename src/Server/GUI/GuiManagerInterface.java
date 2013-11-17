package Server.GUI;

import Util.State;

import javax.swing.*;

public interface GuiManagerInterface
{
    public void setState(State _state);
    public JPanel getGui();
}
