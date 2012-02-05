package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.TimerTask;
import java.util.Timer;
import model.Model;
import model.ParserException;
import model.Pixmap;


/**
 * A simple frame to organize the view.
 * 
 * @author Robert C Duvall
 */
public class Frame extends JFrame
{
    // default version number
    private static final long serialVersionUID = 1L;
    private static final int ANIMATION_DELAY = 100;    // in milli-seconds

    // state
    private Model myModel;
    private JLabel myDisplay;
    private JTextField myInput;
    private Timer myTimer;



    /**
     * Create frame with the given title and size for the interior canvas.
     */
    public Frame (String title, Dimension size)
    {
        // create GUI components
        myDisplay = makeDisplay(size);
        myInput = makeInput();
        // add containers to Frame and show it
        getContentPane().add(myDisplay, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(myInput), BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(title);
        pack();
        // support animation, initially stopped
        myTimer = null;
    }


    /**
     * Updates current model for this view.
     */
    public void setModel (Model model)
    {
        if (model != null)
        {
            myModel = model;
        }
    }


    // Return input area where ENTER evaluates expression.
    protected JTextField makeInput ()
    {
        JTextField result = new JTextField();
        result.setBorder(BorderFactory.createLoweredBevelBorder());
        result.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed (ActionEvent evt)
                {
                    animateExpression(myInput.getText());
                }
            });
        return result;
    }

    // Return display area for results of expression
    private JLabel makeDisplay (Dimension size)
    {
        JLabel result = new JLabel(new Pixmap(size).toIcon());
        result.setBorder(BorderFactory.createLoweredBevelBorder());
        return result;
    }

    // Evaluate the given input repeatedly to produce an animation
    private void animateExpression (final String text)
    {
        // generate new pictures to animate
        TimerTask task = new TimerTask()
            {
                private int index = 0;
                @Override
                public void run ()
                {
                    try
                    {
                        if (index <= Model.NUM_FRAMES)
                        {
                            myDisplay.setIcon(myModel.evaluate(text, myDisplay.getSize()).toIcon());
                            myModel.nextFrame();
                            index++;
                        }
                        else
                        {
                            endAnimation();
                        }
                    }
                    catch (ParserException e)
                    {
                        endAnimation();
                        JOptionPane.showMessageDialog(Frame.this,
                                                      e.getMessage(),
                                                      "Input Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
        // end previous animation if still running
        endAnimation();
        // start new animation
        myTimer = new Timer();
        myTimer.schedule(task, 0, ANIMATION_DELAY);
        myModel.reset();
    }


    // Silly Java anachronism that you cannot cancel the same Timer twice
    private void endAnimation ()
    {
        if (myTimer != null)
        {
            myTimer.cancel();
            myTimer = null;
        }
    }
}
