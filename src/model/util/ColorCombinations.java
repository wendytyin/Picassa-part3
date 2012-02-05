package model.util;

import java.util.Collections;
import java.util.List;

import model.RGBColor;

/**
 * Combine two colors by combining their components.
 * 
 * This is a separate class from color since it is just one set of ways to
 * combine colors, many may exist and we do not want to keep modifying the
 * RGBColor class.
 * 
 * @author Robert C. Duvall
 * 
 *         added more functions
 * @author Wendy Yin
 */
public class ColorCombinations {

    public static RGBColor abs(RGBColor left) {
        return new RGBColor(Math.abs(left.getRed()), Math.abs(left.getGreen()),
                Math.abs(left.getBlue()));
    }

    /**
     * Combine two colors by adding their components. is ready for >2 operands
     */
    public static RGBColor add(List<RGBColor> RGBColors) {
        double newRed = 0;
        double newGreen = 0;
        double newBlue = 0;
        int z = 0;
        while (z < RGBColors.size()) {
            RGBColor leaf = RGBColors.get(z);
            newRed += leaf.getRed();
            newGreen += leaf.getGreen();
            newBlue += leaf.getBlue();
            z += 1;
        }
        return new RGBColor(newRed, newGreen, newBlue);
    }

    /**
     * Arc-tangent of each individual component
     */
    public static RGBColor atan(RGBColor left) {
        double newRed = 0;
        double newGreen = 0;
        double newBlue = 0;
        newRed += Math.atan(left.getRed());
        newGreen += Math.atan(left.getGreen());
        newBlue += Math.atan(left.getBlue());
        return new RGBColor(newRed, newGreen, newBlue);
    }

    public static RGBColor avg(List<RGBColor> RGBColors) {
        double size = RGBColors.size();
        RGBColor total = add(RGBColors);
        return new RGBColor(total.getRed() / size, total.getGreen() / size,
                total.getBlue() / size);
    }

    public static RGBColor ceil(RGBColor firstOne) {
        return new RGBColor(Math.ceil(firstOne.getRed()), Math.ceil(firstOne
                .getGreen()), Math.ceil(firstOne.getBlue()));
    }

    /**
     * 
     */
    public static RGBColor color(RGBColor left, RGBColor middle, RGBColor right) {
        return new RGBColor(left.getRed(), middle.getGreen(), right.getBlue());
    }

    public static RGBColor cos(RGBColor firstOne) {
        return new RGBColor(Math.cos(firstOne.getRed()), Math.cos(firstOne
                .getGreen()), Math.cos(firstOne.getBlue()));
    }

    /**
     * Combine two colors by dividing their components.
     */
    public static RGBColor divide(RGBColor left, RGBColor right) {
        return new RGBColor(left.getRed() / right.getRed(), left.getGreen()
                / right.getGreen(), left.getBlue() / right.getBlue());
    }

    /**
     * Combine two colors by finding exponent
     */
    public static RGBColor exponent(RGBColor left, RGBColor right) {
        return new RGBColor(Math.pow(left.getRed(), right.getRed()), Math.pow(
                left.getGreen(), right.getGreen()), Math.pow(left.getBlue(),
                right.getBlue()));
    }

    public static RGBColor floor(RGBColor firstOne) {
        return new RGBColor(Math.floor(firstOne.getRed()), Math.floor(firstOne
                .getGreen()), Math.floor(firstOne.getBlue()));
    }

    public static RGBColor log(RGBColor firstOne) {
        double newRed = 0;
        double newGreen = 0;
        double newBlue = 0;
        newRed += Math.log(firstOne.getRed());
        newGreen += Math.log(firstOne.getGreen());
        newBlue += Math.log(firstOne.getBlue());
        return new RGBColor(newRed, newGreen, newBlue);
    }

    public static RGBColor maximum(List<RGBColor> RGBColors) {
        return Collections.max(RGBColors);
    }

    public static RGBColor minimum(List<RGBColor> RGBColors) {
        return Collections.min(RGBColors);
    }

    /**
     * Combine two colors by multiplying their components. is now ready for >2
     * operands
     */
    public static RGBColor multiply(List<RGBColor> RGBColors) {
        double newRed = 1;
        double newGreen = 1;
        double newBlue = 1;
        int z = 0;
        while (z < RGBColors.size()) {
            RGBColor leaf = RGBColors.get(z);
            newRed *= leaf.getRed();
            newGreen *= leaf.getGreen();
            newBlue *= leaf.getBlue();
            z += 1;
        }
        return new RGBColor(newRed, newGreen, newBlue);
    }

    /**
     * Combine two colors by finding remainder of their components.
     */
    public static RGBColor modulus(RGBColor left, RGBColor right) {
        return new RGBColor(left.getRed() % right.getRed(), left.getGreen()
                % right.getGreen(), left.getBlue() % right.getBlue());
    }

    /**
     * Invert colors. Only takes one argument.
     */
    public static RGBColor negate(RGBColor left) {
        return new RGBColor(-1 * left.getRed(), -1 * left.getGreen(), -1
                * left.getBlue());
    }

    public static RGBColor sin(RGBColor firstOne) {
        return new RGBColor(Math.sin(firstOne.getRed()), Math.sin(firstOne
                .getGreen()), Math.sin(firstOne.getBlue()));
    }

    /**
     * Combine two colors by subtracting their components.
     */
    public static RGBColor subtract(RGBColor left, RGBColor right) {
        return new RGBColor(left.getRed() - right.getRed(), left.getGreen()
                - right.getGreen(), left.getBlue() - right.getBlue());
    }

    public static RGBColor tan(RGBColor firstOne) {
        double newRed = 0;
        double newGreen = 0;
        double newBlue = 0;
        newRed += Math.tan(firstOne.getRed());
        newGreen += Math.tan(firstOne.getGreen());
        newBlue += Math.tan(firstOne.getBlue());
        return new RGBColor(newRed, newGreen, newBlue);
    }
}
