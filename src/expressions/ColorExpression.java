package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class ColorExpression extends ParenExpression {

    private static final String myCommand = "color";
    private static final String commandMatching="color";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

    // for the factory to look at
    private ColorExpression() {
    }

    @Override
    public Expression getNewExpression() {
        return new ColorExpression();
    }

    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new ColorExpression());
    }

    @Override
    public RGBColor evaluate() {

        List<Expression> mySubExpressions = getSubExpressions();
        if (mySubExpressions.size() < 3) {
            throw new ParserException(
                    "Not enough numbers, what are you trying to do with "
                            + myCommand + "?");
        }
        // Yes, this is really messy and bad, I know.
        RGBColor newRed = mySubExpressions.get(0).evaluate();
        RGBColor newGreen = mySubExpressions.get(1).evaluate();
        RGBColor newBlue = mySubExpressions.get(2).evaluate();

        return ColorCombinations.color(newRed, newGreen, newBlue);
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
