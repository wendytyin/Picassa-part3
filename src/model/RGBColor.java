package model;

import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;


/**
 * Represents an immutable RGB color, whose values range from -1 to 1
 * 
 * For more information about RGB colors, see:
 *   http://en.wikipedia.org/wiki/RGB_color_model
 * 
 * @author Robert C. Duvall
 */
public class RGBColor implements Comparable<RGBColor>, Iterable<Double>
{
    public static final double COLOR_MIN = -1;
    public static final double COLOR_MAX = 1;
    public static final int JAVA_COLOR_MAX = 255; // should be defined in java.util.Color

    private double myRed;
    private double myGreen;
    private double myBlue;


    /**
     * Create a color that represents BLACK.
     */
    public RGBColor ()
    {
        this(COLOR_MIN);
    }


    /**
     * Create a grey-scale color from the given value.
     */
    public RGBColor (double grey)
    {
        this(grey, grey, grey);
    }


    /**
     * Create a color from the given component values.
     */
    public RGBColor (double red, double green, double blue)
    {
        myRed = red;
        myGreen = green;
        myBlue = blue;
    }


    /**
     * Create a color from another RGBColor.
     */
    public RGBColor (RGBColor other)
    {
        this(other.getRed(), other.getGreen(), other.getBlue());
    }


    /**
     * Create a color from its corresponding Java Color.
     */
    public RGBColor (Color javaColor)
    {
        this(fromJava(javaColor.getRed()),
             fromJava(javaColor.getGreen()),
             fromJava(javaColor.getBlue()));
    }


    /**
     * @return red component of this color
     */
    public double getRed ()
    {
        return myRed;
    }


    /**
     * @return green component of this color
     */
    public double getGreen ()
    {
        return myGreen;
    }


    /**
     * @return blue component of this color
     */
    public double getBlue ()
    {
        return myBlue;
    }


    /**
     * Clamps this colors values to its max and min values.
     */
    public void clamp ()
    {
        myRed = clamp(getRed());
        myGreen = clamp(getGreen());
        myBlue = clamp(getBlue());
    }


    /**
     * Wraps this color's component values around its max and min values.
     */
    public void wrap ()
    {
        myRed = wrap(getRed());
        myGreen = wrap(getGreen());
        myBlue = wrap(getBlue());
    }


    /**
     * Converts this color's component values to grey-scale.
     * 
     * Computes weighted average of color components based on:
     *   http://www.csulb.edu/~percept/kyotocolor.html
     */
    public RGBColor toGreyScale ()
    {
        return new RGBColor(0.30 * getRed() + 0.59 * getGreen() + 0.11 * getBlue());
    }


    /**
     * Converts this colors to its corresponding Java Color.
     */
    public Color toJavaColor ()
    {
        clamp();
        return new Color(toJava(getRed()),
                         toJava(getGreen()),
                         toJava(getBlue()));
    }


    /**
     * @return iterator over this color's component values.
     */
    @Override
    public Iterator<Double> iterator ()
    {
        return Arrays.asList(getRed(), getGreen(), getBlue()).iterator();
    }


    /**
     * Compares this color with the given color for order.
     * 
     * @return negative integer, zero, or a positive integer as this object is 
     * less than, equal to, or greater than given object.
     */
    @Override
    public int compareTo (RGBColor other)
    {
        return Double.compare(toGreyScale().getRed(), other.toGreyScale().getRed());
    }


    /**
     * @return true iff this Color's component values match the given color's.
     */
    public boolean equals (Object o)
    {
        if (o instanceof RGBColor)
        {
            return compareTo((RGBColor)o) == 0;
        }
        return false;
    }


    /**
     * @return component values of this color as a string
     */
    public String toString ()
    {
        return "[ " + getRed() + " , " + getGreen() + " , " + getBlue() + " ]";
    }


    /**
     * Clamps the given value to the Color's max and min values.
     */
    private double clamp (double value)
    {
        return Math.max(COLOR_MIN, Math.min(COLOR_MAX, value));
    }


    /**
     * Wraps the given value around the RGBColor's max and min values.
     */
    private double wrap (double value)
    {
        double range = COLOR_MAX - COLOR_MIN;
        value %= range;
        if (value > COLOR_MAX)      return value - range;
        else if (value < COLOR_MIN) return value + range;
        else                        return value;
    }


    /**
     * Converts the given value to the RGB Color's range.
     */
    // note, must be static to be called from constructor
    private static double fromJava (int value)
    {
        double range = COLOR_MAX - COLOR_MIN;
        return (double)value / JAVA_COLOR_MAX * range + COLOR_MIN;
    }


    /**
     * Converts the given value to the Java Color's range.
     */
    // note, must be static to be called from constructor
    private static int toJava (double value)
    {
        double range = COLOR_MAX - COLOR_MIN;
        return (int)((value - COLOR_MIN) / range * JAVA_COLOR_MAX);
    }
}
