package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class ExponentExpression extends ParenExpression {
	
	private static final String myCommand="exp";
    private static final String commandMatching="exp";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private ExponentExpression() {}

	@Override
	public Expression getNewExpression() {
		return new ExponentExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new ExponentExpression());
	}

	@Override
	public RGBColor evaluate() {

		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.size()<2){
			throw new ParserException(
					"Not enough numbers, what are you trying to do with "
							+ myCommand +"?");
		}
		RGBColor firstOne=mySubExpressions.get(0).evaluate();
		RGBColor secondOne=mySubExpressions.get(1).evaluate();
		return ColorCombinations.exponent(firstOne, secondOne);
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
