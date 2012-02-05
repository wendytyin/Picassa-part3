package model;

import java.util.ArrayList;
import java.util.List;

import model.ParserException.Type;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import expressions.*;


/**
 * Parses a string into an expression tree based on rules for arithmetic.
 * 
 * Due to the nature of the language being parsed, a recursive descent parser is
 * used http://en.wikipedia.org/wiki/Recursive_descent_parser
 * 
 * @author former student solution
 * @author Robert C. Duvall (added comments, exceptions, some functions)
 */
public class Parser {

    // Contains addresses to all the different Expression factories
    // I considered a map here. Decided against that because it places a little
    // too much power into this class (it would know the commands of every
    // Expression subclass).
    //

    private static final List<ExpressionFactory> Operations = new ArrayList<ExpressionFactory>();
    static {
        // the terminal expressions
        Operations.add(NumberExpression.getFactory());
        Operations.add(VariableExpression.getFactory()); // also covers the axes
                                                         // if no value assigned
                                                         // to x/y

        // the parenthetical expressions
        // part one
        Operations.add(PlusExpression.getFactory());
        Operations.add(NegExpression.getFactory());
        Operations.add(MinusExpression.getFactory());
        Operations.add(MultiplyExpression.getFactory());
        Operations.add(DivideExpression.getFactory());
        Operations.add(ModExpression.getFactory());
        Operations.add(ExponentExpression.getFactory());
        Operations.add(ColorExpression.getFactory());

        // part two
        Operations.add(AbsExpression.getFactory());
        Operations.add(CeilingExpression.getFactory());
        Operations.add(FloorExpression.getFactory());
        Operations.add(LogExpression.getFactory());
        Operations.add(LetExpression.getFactory());

        Operations.add(RandomExpression.getFactory());
        Operations.add(PerlinBWExpression.getFactory());
        Operations.add(PerlinColorExpression.getFactory());
        Operations.add(RGBtoyCrCbExpression.getFactory());
        Operations.add(yCrCbtoRGBExpression.getFactory());
        Operations.add(ClampExpression.getFactory());
        Operations.add(WrapExpression.getFactory());

        Operations.add(SinExpression.getFactory());
        Operations.add(CosExpression.getFactory());
        Operations.add(TanExpression.getFactory());
        Operations.add(ATanExpression.getFactory());

    }

    // private static final List<Expression> MajorExpressionTypes=new
    // ArrayList<Expression>();
    // static{
    // MajorExpressionTypes.add(new ParenExpression());
    // MajorExpressionTypes.add(new NumberExpression());
    // MajorExpressionTypes.add(new XAxisExpression());
    // MajorExpressionTypes.add(new YAxisExpression());
    // }

    // state of the parser
    private int myCurrentPosition;
    private String myInput;

    /**
     * Converts given string into expression tree.
     * 
     * @param input
     *            expression given in the language to be parsed
     * @return expression tree representing the given formula
     */
    public Expression makeExpression(String input) {
        myInput = input;
        myCurrentPosition = 0;
        Expression result = parseExpression();
        skipWhiteSpace();
        if (notAtEndOfString()) {
            throw new ParserException(
                    "Unexpected characters at end of the string: "
                            + myInput.substring(myCurrentPosition),
                    ParserException.Type.EXTRA_CHARACTERS);
        }
        return result;
    }

    private Expression parseExpression() {
        skipWhiteSpace();

        for (ExpressionFactory ExpressionCreator : Operations) {
            if (ExpressionCreator.isThisCommand(myInput, myCurrentPosition)) {

                Expression tree = ExpressionCreator.getExpression(myInput,
                        myCurrentPosition); // contains parsing, returns a new
                                            // empty expression
                myCurrentPosition = ExpressionCreator.getPosition();
                
                if (tree.isLeaf()) {
                    return tree;
                }
                return parseSubExpressions(tree);
            }
        }
        throw new ParserException("Unknown Command " + myInput,
                Type.UNKNOWN_COMMAND);
    }

    private Expression parseSubExpressions(Expression tree) {
        List<Expression> subExpressions = new ArrayList<Expression>();
        while (notAtEndOfString() && currentCharacter() != ')') {
            subExpressions.add(parseExpression());
            skipWhiteSpace();
        }
        tree.updateSubExpressions(subExpressions);
        
        if (!notAtEndOfString()){
            throw new ParserException("Expected close paren, instead found "
                    + myInput.substring(myCurrentPosition));
        }
        if (currentCharacter() == ')') {
            myCurrentPosition++;
            return tree;
        }
        throw new ParserException("I don't know what happened, your input is really messed up");
    }

    private void skipWhiteSpace() {
        while (notAtEndOfString() && Character.isWhitespace(currentCharacter())) {
            myCurrentPosition++;
        }
    }

    private char currentCharacter() {
        return myInput.charAt(myCurrentPosition);
    }

    private boolean notAtEndOfString() {
        return myCurrentPosition < myInput.length();
    }
}
