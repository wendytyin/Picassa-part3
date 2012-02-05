import java.awt.Dimension;

import model.Model;
import view.Frame;


/**
 * 
 * @author Robert Duvall (rcd@cs.duke.edu)
 */
public class Main
{
    public static final Dimension SIZE = new Dimension(400, 400);
    public static final String TITLE = "PICASSA!";


    public static void main (String[] args)
    {
        Model model = new Model();
        Frame view = new Frame(TITLE, SIZE);
        view.setModel(model);
        view.setVisible(true);
    }
}
