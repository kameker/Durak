package ivan;

import ivan.Graphics.DurakSettings;
import ivan.Graphics.Frame.MainFrame;
import java.util.ArrayList;


public class Durak {
    public void startDurak() {
        DurakSettings settings = new DurakSettings();
        MainFrame gameFrame = new MainFrame(settings);
    }
}
