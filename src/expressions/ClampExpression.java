package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;

public class ClampExpression extends ParenExpression {
    
	private static final String myCommand="clamp";
    private static final String commandMatching="clamp";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private ClampExpression() {}

	@Override
	public Expression getNewExpression() {
		return new ClampExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new ClampExpression());
	}

	@Override
	public RGBColor evaluate() {

		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.isEmpty()){
			throw new ParserException(
					"Not enough numbers, what are you trying to do with "
							+ myCommand +"?");
		}
		RGBColor firstOne=mySubExpressions.get(0).evaluate();
		firstOne.clamp();
        return firstOne;
	}

	@Override
	String getMyCommand() {return myCommand;
	}

    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

}
