package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class PlusExpression extends ParenExpression {

    private static final String myCommand = "plus";
    private static final String commandMatching="plus|sum|\\+";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

    // for the factory to look at
    private PlusExpression() {
    }

    @Override
    public Expression getNewExpression() {
        return new PlusExpression();
    }

    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new PlusExpression());
    }

    @Override
    public RGBColor evaluate() {
        List<Expression> mySubExpressions = getSubExpressions();
        if (mySubExpressions.isEmpty()) {
            throw new ParserException(
                    "No numbers, what are you trying to do with " + myCommand
                            + "?");
        }

        List<RGBColor>myRGBColors=new ArrayList<RGBColor>();
        for (Expression sub:mySubExpressions){
            myRGBColors.add(sub.evaluate());
        }
        return ColorCombinations.add(myRGBColors);
    }

    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

    /**
     * still a redundancy
     */
    @Override
    String getMyCommand() {
        return myCommand;
    }
}
