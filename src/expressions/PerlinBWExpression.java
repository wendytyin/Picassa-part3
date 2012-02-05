package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.ParserException;
import model.RGBColor;
import model.util.PerlinNoise;

public class PerlinBWExpression extends ParenExpression {
    
	private static final String myCommand="perlinBW";
    private static final String commandMatching="perlinBW";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private PerlinBWExpression() {}

	@Override
	public Expression getNewExpression() {
		return new PerlinBWExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new PerlinBWExpression());
	}

	@Override
	public RGBColor evaluate() {

		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.size()<2){
			throw new ParserException(
					"Not enough subexpressions, what are you trying to do with "
							+ myCommand +"?");
		}
		RGBColor firstOne=mySubExpressions.get(0).evaluate();
        RGBColor secondOne=mySubExpressions.get(1).evaluate();
        return PerlinNoise.greyNoise(firstOne, secondOne);
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
