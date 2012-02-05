package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class MaxExpression extends ParenExpression {
	
	private static final String myCommand="max";
    private static final String commandMatching="max";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private MaxExpression() {}

	@Override
	Expression getNewExpression() {return new MaxExpression();
	}

	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new MaxExpression());
	}
	
	@Override
	public RGBColor evaluate() {
		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.isEmpty()){
			throw new ParserException(
					"No numbers, what are you trying to do with "
							+ myCommand + "?");
		}
		List<RGBColor>mySubExp2=new ArrayList<RGBColor>();
		for (Expression e:mySubExpressions){
		    mySubExp2.add(e.evaluate());
		}
		return ColorCombinations.maximum(mySubExp2);
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
