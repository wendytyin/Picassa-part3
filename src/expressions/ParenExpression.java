package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ParenExpression extends Expression {

    private static boolean isOperator = false;
    // expression begins with a left paren followed by the command name,
    // which is a sequence of alphabetic characters
    private static final Pattern EXPRESSION_BEGIN_REGEX = Pattern
            .compile("\\(([a-zA-Z]+)");
    private static final Pattern OVERLOADED_OPERATOR_REGEX = Pattern
            .compile("\\((\\p{Punct}+?)");

    private List<Expression> mySubExpressions;

    /**
     * Pretty much self-explanatory. True if string contains parenthetical
     * expression, false if it doesn't.
     * 
     * @param myInput
     * @param myCurrentPosition
     */
    private static boolean isParenExpression(String myInput,
            int myCurrentPosition) {
        Matcher expMatcher = EXPRESSION_BEGIN_REGEX.matcher(myInput
                .substring(myCurrentPosition));
        Matcher overloadMatcher = OVERLOADED_OPERATOR_REGEX.matcher(myInput
                .substring(myCurrentPosition));
        if (overloadMatcher.lookingAt()) {
            isOperator = true;
        }
        return expMatcher.lookingAt() || overloadMatcher.lookingAt();
    }

    /**
     * Finds the next parenthetical expression, given the string and current
     * location within the string.
     * 
     * @param myInput
     * @param myCurrentPosition
     * @return the first word of the parenthetical expression
     */
    private static String findCommand(String myInput, int myCurrentPosition) {
        Matcher expMatcher;
        if (isOperator) {
            expMatcher = OVERLOADED_OPERATOR_REGEX.matcher(myInput);
        } else {
            expMatcher = EXPRESSION_BEGIN_REGEX.matcher(myInput);
        }
        expMatcher.find(myCurrentPosition);
        return expMatcher.group(1);
    }

    /**
     * Called by the subclasses. Perhaps not the most efficient way to do
     * things, but it was the next logical step from what I had before.
     * 
     * @return true if the String input matches the Expression type, false if it
     *         doesn't
     */
    @Override
    public boolean isThisCommand(String myInput, int myCurrentPosition) {
        if (isParenExpression(myInput, myCurrentPosition)) {
            String commandName = findCommand(myInput, myCurrentPosition);
            return commandIsThisExpression(commandName);
        }
        return false;
    }

    /**
     * Parses the string, updates location within the string
     * 
     * @param myInput
     * @param myCurrentPosition
     */
    @Override
    public void parseCommand(String myInput, int myCurrentPosition) {
        Matcher expMatcher;
        if (isOperator) {
            expMatcher = OVERLOADED_OPERATOR_REGEX.matcher(myInput);
        } else {
            expMatcher = EXPRESSION_BEGIN_REGEX.matcher(myInput);
        }

        expMatcher.find(myCurrentPosition);
        changePosition(expMatcher.end());
    }

    /**
     * Update the expression tree so it knows who its children are.
     * Perhaps not the safest thing to have public, but I don't know how else to do it
     */
    @Override
    public void updateSubExpressions(List<Expression> subExpressions) {
        mySubExpressions = subExpressions;
    }

    @Override
    public List<Expression> getSubExpressions() {
        return mySubExpressions;
    }

    /**
     * Provides the basic framework of printing out the Expression tree.
     * 
     * @return the Expression tree as string
     */
    @Override
    public final String toString() {
        StringBuffer result = new StringBuffer();
        result.append("(" + getMyCommand() + " ");
        // myCommand is implemented in subclasses
        List<Expression> mySubExpressions = getSubExpressions();
        if (mySubExpressions != null) {
            for (Expression subExp : mySubExpressions) {
                result.append(subExp.toString() + " ");
            }
        }
        result.append(")");
        return result.toString();
    }

    abstract boolean commandIsThisExpression(String commandName);

}
