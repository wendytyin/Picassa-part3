package expressions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.ParserException;
import model.RGBColor;
import model.util.ColorModel;
/**
 * convert color to luminance / chrominance space
 * @author Wendy
 *
 */
public class RGBtoyCrCbExpression extends ParenExpression {
    
	private static final String myCommand="rgbToYCrCb";
    private static final String commandMatching="rgbToYCrCb";
    private static final Pattern COMMAND_REGEX = Pattern.compile(commandMatching);

	// for the factory to look at
	private RGBtoyCrCbExpression() {}

	@Override
	public Expression getNewExpression() {
		return new RGBtoyCrCbExpression();
	}
	
	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new RGBtoyCrCbExpression());
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
        return ColorModel.rgb2ycrcb(firstOne);
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
