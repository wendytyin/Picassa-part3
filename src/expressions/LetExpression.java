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
public class LetExpression extends ParenExpression {

    private static final String myCommand = "let";
    private static final String commandMatching = "let";
    private static final Pattern COMMAND_REGEX = Pattern
            .compile(commandMatching);

    private Expression myVariable;
    private Expression myVariableValue;

    // for the factory to look at
    private LetExpression() {
    }

    private LetExpression(Expression variable) {
        myVariable = (VariableExpression) variable;
    }

    @Override
    public Expression getNewExpression() {
        return new LetExpression(myVariable);
    }
    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new LetExpression());
    }
    
    @Override
    public void updateSubExpressions(List<Expression>subExpressions){
        super.updateSubExpressions(subExpressions);
        //do the last bit of parsing
        List<Expression> mySubExpressions = getSubExpressions();
        getVariable(mySubExpressions);
        getVariableValue(mySubExpressions);

        if (mySubExpressions.isEmpty()) {
            throw new ParserException(
                    "No expressions, what are you trying to do with "
                            + myCommand + "?");
        }
        replaceVariableWithValue(mySubExpressions);
    }
    
    @Override
    public RGBColor evaluate() {
        List<Expression> mySubExpressions = getSubExpressions();
        
        return mySubExpressions.get(0).evaluate();
    }

    private void getVariable(List<Expression> mySubExpressions) {
        if (mySubExpressions.isEmpty()) {
            throw new ParserException("No value has been assigned to "
                    + myVariable.toString());
        }
        myVariable = mySubExpressions.remove(0);
    }
    
    private void getVariableValue(List<Expression> mySubExpressions) {
        if (mySubExpressions.isEmpty()) {
            throw new ParserException("No value has been assigned to "
                    + myVariable.toString());
        }
        myVariableValue = mySubExpressions.remove(0);
    }

    // traverses the expression tree, replaces any expressions that match
    // myVariable with myVariableValue
    private void replaceVariableWithValue(List<Expression> mySubExpressions) {
        for (int i = 0; i < mySubExpressions.size(); i++) {
            Expression subExps = mySubExpressions.get(i);

            if (!subExps.isLeaf()) {
                replaceVariableWithValue(subExps.getSubExpressions()); // recurse
            }
            if (subExps.isLeaf() && (myVariable.equals(subExps))) { // replace
                mySubExpressions.remove(i);
                mySubExpressions.add(i, myVariableValue);
            }
        } // or else do nothing
    }

    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

    @Override
    String getMyCommand() {
        return myCommand+" "+myVariable+" "+myVariableValue;
    }

}
