package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class MinExpression extends ParenExpression {
	
	private static final String myCommand="min";
    private static final String commandMatching="min";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private MinExpression() {}

	@Override
	Expression getNewExpression() {return new MinExpression();
	}

	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new MinExpression());
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
		return ColorCombinations.minimum(mySubExp2);
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
