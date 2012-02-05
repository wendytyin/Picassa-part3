package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class AbsExpression extends ParenExpression {
    
	private static final String myCommand="abs";
    private static final String commandMatching="abs";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private AbsExpression() {}

	@Override
	public Expression getNewExpression() {
		return new AbsExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new AbsExpression());
	}

	@Override
	public RGBColor evaluate() {

		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.size()<1){
			throw new ParserException(
					"Not enough numbers, what are you trying to do with "
							+ myCommand +"?");
		}
		RGBColor firstOne=mySubExpressions.get(0).evaluate();
		return ColorCombinations.abs(firstOne);
		
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
