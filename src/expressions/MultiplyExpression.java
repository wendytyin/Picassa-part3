package expressions;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class MultiplyExpression extends ParenExpression {
	
	private static final String myCommand="mul";
    private static final String commandMatching="mul|product|\\*";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private MultiplyExpression() {}


	@Override
	Expression getNewExpression() {return new MultiplyExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new MultiplyExpression());
	}
	
	@Override
	public RGBColor evaluate() {
	    
		List<Expression>mySubExpressions=getSubExpressions();
		if (mySubExpressions.isEmpty()){
			throw new ParserException(
					"No numbers, what are you trying to do with "
							+ myCommand +"?");
		}
		
		List<RGBColor>myRGBColors=new ArrayList<RGBColor>();
		for (Expression sub:mySubExpressions){
		    myRGBColors.add(sub.evaluate());
		}
        return ColorCombinations.multiply(myRGBColors);
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
