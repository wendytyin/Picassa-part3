package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;

/**
 * Assigning values to arbitrary variables
 * 
 * @author Wendy
 * 
 */
public class IfExpression extends ParenExpression {

    private static final String myCommand = "if";
    private static final String commandMatching = "if";
    private static final Pattern COMMAND_REGEX = Pattern
            .compile(commandMatching);

    // for the factory to look at
    private IfExpression() {
    }

    @Override
    public Expression getNewExpression() {
        return new IfExpression();
    }
    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new IfExpression());
    }
    
    @Override
    public RGBColor evaluate() {
        List<Expression> mySubExpressions = getSubExpressions();
        if (mySubExpressions.size()<3) {
            throw new ParserException(
                    "Not enough expressions, what are you trying to do with "
                            + myCommand + "?");
        }
        RGBColor value=mySubExpressions.get(0).evaluate();
        if ((value.getRed()+value.getGreen()+value.getBlue())>0){
            return mySubExpressions.get(1).evaluate();
        }
        return mySubExpressions.get(2).evaluate();
    }

    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

    @Override
    String getMyCommand() {
        return myCommand;
    }

}
