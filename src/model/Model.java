package model;

import java.awt.Dimension;

import expressions.Expression;

/**
 * Evaluate an expression for each pixel in a image.
 * 
 * @author Robert C Duvall
 */
public class Model {
    public static final double DOMAIN_MIN = -1;
    public static final double DOMAIN_MAX = 1;
    public static final int NUM_FRAMES = 50;

    private static double myCurrentTime = 0;
    private static double evalY;
    private static double evalX;

    /**
     * Advance to the next frame in the animation.
     */
    public void reset() {
        myCurrentTime = 0;
    }

    /**
     * Advance to the next frame in the animation.
     */
    public void nextFrame() {
        myCurrentTime += 1.0 / NUM_FRAMES;
    }

    /**
     * Evaluate an expression for each point in the image.
     */
    public Pixmap evaluate(String input, Dimension size) {
        Pixmap result = new Pixmap(size);
        // create expression to evaluate just once
        Expression toEval = new Parser().makeExpression(input);
        // evaluate at each pixel
        for (int imageY = 0; imageY < size.height; imageY++) {
            evalY = imageToDomainScale(imageY, size.height);
            for (int imageX = 0; imageX < size.width; imageX++) {
                evalX = imageToDomainScale(imageX, size.width);
                result.setColor(imageX, imageY, toEval.evaluate().toJavaColor());

                // result.setColor(imageX, imageY, new RGBColor(myCurrentTime*2
                // - 1).toJavaColor()); //tester
            }
        }
        return result;
    }

    /**
     * Convert from image space to domain space.
     */
    protected double imageToDomainScale(int value, int bounds) {
        double range = DOMAIN_MAX - DOMAIN_MIN;
        return ((double) value / bounds) * range + DOMAIN_MIN;
    }

    public static double getXCoordinate() {
        return evalX;
    }

    public static double getYCoordinate() {
        return evalY;
    }
    public static double getTime(){
        return myCurrentTime;
    }
}
