package model;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * Represents an image that supports changing individual pixel colors.
 * 
 * Only the following formats are supported: png, jpg, gif.
 * 
 * @author Robert C. Duvall
 */
public class Pixmap
{
    public static final Dimension DEFAULT_SIZE = new Dimension(300, 300);
    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final String DEFAULT_NAME = "Default";

    private String myFileName;
    private BufferedImage myImage;
    private Dimension mySize;


    /**
     * Create a default pixmap (300x300, completely black)
     */
    public Pixmap ()
    {
        this(DEFAULT_SIZE.width, DEFAULT_SIZE.height, DEFAULT_COLOR);
    }

    /**
     * Create a default pixmap with given size
     */
    public Pixmap (Dimension size)
    {
        this(size.width, size.height, DEFAULT_COLOR);
    }

    /**
     * Create a default pixmap with given width and height
     */
    public Pixmap (int width, int height)
    {
        this(width, height, DEFAULT_COLOR);
    }

    /**
     * Create a pixmap with given width and height and filled with given initial color
     */
    public Pixmap (int width, int height, Color color)
    {
        createImage(width, height, color);
    }

    /**
     * Create a pixmap as a copy of the given pixmap
     */
    public Pixmap (Pixmap other)
    {
        myFileName = other.myFileName;
        mySize = other.getSize();
        myImage = copyImage(mySize, mySize, other.myImage);
    }

    /**
     * Create a pixmap from the given local file
     * @throws IOException 
     */
    public Pixmap (String fileName)
        throws IOException
    {
        if (fileName == null)
        {
            createImage(DEFAULT_SIZE.width, DEFAULT_SIZE.height, DEFAULT_COLOR);
        }
        else
        {
            read(fileName);
        }
    }


    /**
     * @return name of this pixmap (without file system information)
     */
    public String getName ()
    {
        int index = myFileName.lastIndexOf(File.separator);
        if (index >= 0) return myFileName.substring(index + 1);
        else            return myFileName;
    }

    /**
     * @return true iff the given coordinate is within the bounds of this pixmap
     */
    public boolean isInBounds (int x, int y)
    {
        return (0 <= x && x < mySize.width) && (0 <= y && y < mySize.height);
    }

    /**
     * @return bounds of this pixmap
     */
    public Dimension getSize ()
    {
        return new Dimension(mySize);
    }

    /**
     * @return color of the pixel at the given coordinate in this pixmap
     */
    public Color getColor (int x, int y)
    {
        if (isInBounds(x, y)) return new Color(myImage.getRGB(x, y));
        else                  return DEFAULT_COLOR;
    }


    /**
     * Changes the color of the pixel at the given coordinate in this pixmap
     */
    public void setColor (int x, int y, Color value)
    {
        if (isInBounds(x, y))
        {
            myImage.setRGB(x, y, value.getRGB());
        }
    }

    /**
     * Changes the size of this pixmap
     * 
     * Adds default color to fill in blank space if necessary
     */
    public void setSize (Dimension size)
    {
        setSize(size.width, size.height);
    }

    /**
     * Changes the size of this pixmap
     * 
     * Adds default color to fill in blank space if necessary
     */
    public void setSize (int width, int height)
    {
        if (width != mySize.width || height != mySize.height)
        {
            Dimension newSize = new Dimension(width, height);
            if (width > mySize.width || height > mySize.height)
            {
                myImage = copyImage(mySize, newSize, myImage);
            }
            else
            {
                // BUGBUG: scale image down instead?
                myImage = myImage.getSubimage(0, 0, width, height);
            }
            mySize = newSize;
        }
    }


    /**
     * Updates this pixmap to reflect colors from the given local file
     * 
     * Note, changes this pixmap's size if necessary
     * 
     * @throws IOException 
     */
    public void read (String fileName) 
        throws IOException
    {
    	myFileName = fileName;
    	myImage = ImageIO.read(new File(myFileName));
    	mySize = new Dimension(myImage.getWidth(), myImage.getHeight());
    }


    /**
     * Saves this pixmap to the given local file as a JPEG image
     * 
     * @throws IOException 
     */
    public void write (String fileName)
        throws IOException
    {
    	ImageIO.write(myImage, "jpg", new File(fileName));
    }


    /**
     * Draws this pixmap on the screen.
     */
    public void paint (Graphics pen)
    {
        pen.drawImage(myImage, 0, 0, mySize.width, mySize.height, null);
    }


    /**
     * Returns this pixmap as as an Icon to be used in swing.
     */
    public Icon toIcon ()
    {
        return new ImageIcon(myImage);
    }

    private void createImage (int width, int height, Color color)
    {
        myFileName = DEFAULT_NAME;
        myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        mySize = new Dimension(width, height);
    }

    
    private BufferedImage copyImage (Dimension from, Dimension to, BufferedImage original)
    {
        int[] data = new int[from.width * from.height];
        BufferedImage result = new BufferedImage(to.width, to.height, BufferedImage.TYPE_INT_RGB);
        original.getRGB(0, 0, from.width, from.height, data, 0, from.width);
        result.setRGB(0, 0, Math.min(from.width, to.width), Math.min(from.height, to.height), data, 0, from.width);
        return result;
    }
}
