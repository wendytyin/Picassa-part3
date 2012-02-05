package expressions;

public abstract class TerminalExpression extends Expression{

	protected TerminalExpression() {}

	@Override
	public boolean isLeaf() { //aka isTerminalExpression. Same thing.
		return true;
	}
	
	//There is no findCommand or parseExpression here, 
	//because neither numbers nor variables need my help. Aww. Maybe in future refactoring. 

	/**
	 * Since these are terminal expressions, there really is nothing much to do. 
	 * This only exists to reflect the methods in ParenExpression.
	 */
	public final String toString(){
		return getMyCommand();
	}
}
