package expressions;


/**
 * Creates objects of supertype Expression and queries them. 
 * Each subtype includes its own 
 * ways of evaluating and parsing.
 * @author Wendy
 *
 */
public class ExpressionFactory {

	private Expression myExpression;
	
	public ExpressionFactory(Expression expression){
		myExpression=expression;
	}
	
	public boolean isThisCommand(String myInput, int myCurrentPosition){
		return myExpression.isThisCommand(myInput, myCurrentPosition);
	}
	/**
	 * Parses string, updates position, returns a new Expression
	 */
	public Expression getExpression(String myInput, int myCurrentPosition){
		myExpression.parseCommand(myInput,myCurrentPosition);
		return myExpression.getNewExpression();
	}

//	public void updateOperands(List<Expression>subExpressions){
//		myExpression.updateSubExpressions(subExpressions);
//	}
	
	public int getPosition() {
		return myExpression.getPosition();
	}
	
	
}
