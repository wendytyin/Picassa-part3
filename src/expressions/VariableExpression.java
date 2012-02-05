package expressions;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Model;
import model.ParserException;
import model.RGBColor;

/**
 * This is for parsing any arbitrary variable name. 
 * Now also contains instructions for x and y (the axes)
 * 
 * @author Wendy
 */
public class VariableExpression extends TerminalExpression {

    private String myCommand;

    // a simple variable identification corresponding with axes
    private static final Pattern VAR_REGEX = Pattern.compile("([a-zA-Z]+)");

    private VariableExpression() {
    }

    private VariableExpression(String command) {
        myCommand = command;
    }
    @Override
    public boolean equals(Expression other){
        return getMyCommand().equals(other.getMyCommand());
    }

    @Override
    Expression getNewExpression() {
        return new VariableExpression(myCommand);
    }

    public static ExpressionFactory getFactory() {
        return new ExpressionFactory(new VariableExpression());
    }
    
    /**
//     * compares the myCommands. Useful in LetExpression parsing
//     */
//    public boolean hasSameVarAs(Expression other) {
//        return myCommand.equals(other.getMyCommand());
//    }

    @Override
    public boolean isThisCommand(String command, int currentPosition) {
        Matcher varMatcher = VAR_REGEX.matcher(command
                .substring(currentPosition));
        return varMatcher.lookingAt();
    }

    @Override
    public void parseCommand(String myInput, int myCurrentPosition) {
        Matcher varMatcher = VAR_REGEX.matcher(myInput);
            varMatcher.find(myCurrentPosition);
            try{
            myCommand = myInput.substring(varMatcher.start(), varMatcher.end());
            changePosition(varMatcher.end());
            }
            catch (IllegalStateException e){
                throw new ParserException("No variable");
            }
    }
    
    /**
     * Will only be called in an improperly formatted string or axes
     */
    @Override
    public RGBColor evaluate() {
        if (myCommand.equals("x")){
            return new RGBColor(Model.getXCoordinate());
        }
        if (myCommand.equals("y")){
            return new RGBColor(Model.getYCoordinate());
        }
        throw new ParserException("No value has been assigned to "
                + myCommand);
    }

    @Override
    String getMyCommand() {
        return myCommand;
    }

}
