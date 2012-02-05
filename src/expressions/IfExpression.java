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

    private Expression myVariable;

    // for the factory to look at
    private IfExpression() {
    }

    private IfExpression(Expression variable) {
        myVariable = (VariableExpression) variable;
    }

    @Override
    public Expression getNewExpression() {
        return new IfExpression(myVariable);
    }
    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new IfExpression());
    }
    
    @Override
    public void updateSubExpressions(List<Expression>subExpressions){
        super.updateSubExpressions(subExpressions);
        //do the last bit of parsing
        List<Expression> mySubExpressions = getSubExpressions();
        if (mySubExpressions.isEmpty()) {
            throw new ParserException(
                    "No expressions, what are you trying to do with "
                            + myCommand + "?");
        }
        getVariable(mySubExpressions);

    }
    
    @Override
    public RGBColor evaluate() {
        List<Expression> mySubExpressions = getSubExpressions();
        if (mySubExpressions.size()<2) {
            throw new ParserException(
                    "No expressions, what are you trying to do with "
                            + myCommand + "?");
        }
        
        RGBColor value=myVariable.evaluate();
        if ((value.getRed()+value.getGreen()+value.getBlue())>0){
            return mySubExpressions.get(1).evaluate();
        }
        return mySubExpressions.get(0).evaluate();
    }

    private void getVariable(List<Expression> mySubExpressions) {
        if (mySubExpressions.isEmpty()) {
            throw new ParserException("No value has been assigned to "
                    + myVariable.toString());
        }
        myVariable = mySubExpressions.remove(0);
    }

    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

    @Override
    String getMyCommand() {
        return myCommand+" "+myVariable+" ";
    }

}
