package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class NegExpression extends ParenExpression {
	
	private static final String myCommand="neg";
    private static final String commandMatching="neg|\\!";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private NegExpression() {}

	@Override
	Expression getNewExpression() {return new NegExpression();
	}

	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new NegExpression());
	}
	
	@Override
	public RGBColor evaluate() {
		// System.out.println(mySubExpressions.size());
		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.isEmpty()){
			throw new ParserException(
					"No numbers, what are you trying to do with "
							+ myCommand + "?");
		}
		//Yes, this is really messy and bad, I know.

		RGBColor firstOne=mySubExpressions.get(0).evaluate();
		return ColorCombinations.negate(firstOne);
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
