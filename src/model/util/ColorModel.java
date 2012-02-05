package model.util;

import model.RGBColor;


/**
 * Simple utility class that provides functions to convert between color spaces.
 * 
 * Details and constants derived from:
 *   http://www.answers.com/topic/yuv
 * 
 * @author Robert C. Duvall
 */
public class ColorModel
{
    /**
     * Convert color from RGB to YUV color space.
     */
    public static RGBColor rgb2ycrcb (RGBColor c)
    {
        return new RGBColor(
                   c.getRed() *  0.2989 + c.getGreen() *  0.5866 + c.getBlue() *  0.1145,
                   c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() *  0.5,
                   c.getRed() *  0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816);
    }
    
    /**
     * Convert color from YUV to RGB color space.
     */
    public static RGBColor ycrcb2rgb (RGBColor c)
    {
        return new RGBColor(
                    c.getRed() + c.getBlue() *  1.4022,
                    c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145,
                    c.getRed() + c.getGreen() *  1.7710);
    }
}
