package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ParserException;
import model.RGBColor;
import model.util.ColorCombinations;

public class DivideExpression extends ParenExpression {
	
	private static final String myCommand="div";
    private static final String commandMatching="div";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private DivideExpression() {}

	@Override
	public Expression getNewExpression() {
		return new DivideExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new DivideExpression());
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
//		newRed=firstOne.getRed(); 
//		newGreen=firstOne.getGreen();
//		newBlue=firstOne.getBlue();

        RGBColor secondOne=mySubExpressions.get(1).evaluate();
        
		return ColorCombinations.divide(firstOne, secondOne);
		
//		int z=1;
//		// System.out.println(mySubExpressions.size());
//		while (z<mySubExpressions.size()) {
//			RGBColor leaf = mySubExpressions.get(z).evaluate();
//			newRed /= leaf.getRed();
//			newGreen /= leaf.getGreen();
//			newBlue /= leaf.getBlue();
//			z+=1;
//		}
//		return new RGBColor(newRed, newGreen, newBlue);
	}

	@Override
	public String getMyCommand() {
		return myCommand;
	}
    @Override
    boolean commandIsThisExpression(String commandName) {
        Matcher expMatcher = COMMAND_REGEX.matcher(commandName);
        return expMatcher.lookingAt();
    }
}
