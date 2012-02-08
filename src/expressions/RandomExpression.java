package expressions;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.RGBColor;

public class RandomExpression extends ParenExpression {

    private static final String myCommand = "random";
    private static final String commandMatching = "random";
    private static final Pattern COMMAND_REGEX = Pattern
            .compile(commandMatching);
    private static final Random random = new Random();

    private RandomExpression() {
    }

    @Override
    Expression getNewExpression() {
        return new RandomExpression();
    }

    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new RandomExpression());
    }

    @Override
    public RGBColor evaluate() {
        double red = random.nextDouble();
        double green = random.nextDouble();
        double blue = random.nextDouble();
        
        return new RGBColor(switchSignsRandomly(red), switchSignsRandomly(green), switchSignsRandomly(blue));
    }

    private double switchSignsRandomly(double number) {
        if (random.nextBoolean()) {
            return -1 * number;
        }
        return number;
    }

    @Override
    String getMyCommand() {
        return myCommand;
    }

    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

}
