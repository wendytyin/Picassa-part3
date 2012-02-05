package expressions;

import java.util.List;
import model.RGBColor;

/**
 * An Expression represents a mathematical expression as a tree.
 * 
 * In this format, the internal nodes represent mathematical functions and the
 * leaves represent constant values.
 * 
 * @author former student solution
 * @author Robert C. Duvall (added comments, some code)
 */

/*
 * Update: each subclass must also include a getFactory method
 */
public abstract class Expression {
	
	private static int advanceTo = 0;

    protected Expression() {}

    abstract Expression getNewExpression();

	/**
	 * Default value is false. Returns true if the expression is a leaf node 
	 * (so far, if it is a number or x/y, overridden in subclasses).
	 * @return
	 */
	public boolean isLeaf(){
		return false;
	}

    public boolean equals (Expression other){ //TODO: check
        return false;
    }

    /**
     * Changes the value of the position of the parser. Can only be changed by 
     * subclasses, preferably after they've parsed.
     */
    protected static void changePosition(int position){
        advanceTo=position;
    }
    /**
     * Gets the current position of the parser.
     */
    public int getPosition() {
        return advanceTo;
    }

    public void updateSubExpressions(List<Expression> subExpressions){
        System.out.println("what are you trying to do? This expression can't take any children");
        //Do nothing, only useful for parenexpression
    }
    public List<Expression> getSubExpressions(){
        System.out.println("no sub expressions here");
        return null; 
        //do nothing, only useful for parenexpression
    }

    /**
     * 
     * @param myInput
     *            from the string
     * @return true/false if this command matches the Expression type
     */
    public abstract boolean isThisCommand(String myInput, int myCurrentPosition);
    
	public abstract void parseCommand(String myInput, int myCurrentPosition);

	/**
	 * 
	 * @return value of expression (a RGBColor object)
	 */
	public abstract RGBColor evaluate();

	/**
	 * Implemented in subclasses
	 * @return string representation of full expression
	 */
	public abstract String toString();

	/**
	 * Used for toString in subclasses (so it can query each expression for its "name")
	 */
    abstract String getMyCommand();
}
