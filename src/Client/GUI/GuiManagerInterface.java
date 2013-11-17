package Client.GUI;

import Util.State;

import javax.swing.JPanel;

public interface GuiManagerInterface
{
    public void setState(State _state);
    public JPanel getGui();
}
