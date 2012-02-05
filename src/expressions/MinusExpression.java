package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class MinusExpression extends ParenExpression {
	
	private static final String myCommand="minus";
    private static final String commandMatching="minus";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private MinusExpression() {}

	@Override
	Expression getNewExpression() {
return new MinusExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new MinusExpression());
	}
	
	@Override
	public RGBColor evaluate() {
		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.isEmpty()){
			throw new ParserException(
					"No numbers, what are you trying to do with "
							+ myCommand+"?");
		}
		RGBColor firstOne=mySubExpressions.get(0).evaluate();
		RGBColor secondOne=mySubExpressions.get(1).evaluate();
		return ColorCombinations.subtract(firstOne, secondOne);
	}

	@Override
	String getMyCommand() {
		return myCommand;
	}

    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }

}
