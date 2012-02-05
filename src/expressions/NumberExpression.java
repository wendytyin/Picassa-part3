package expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.RGBColor;

public class NumberExpression extends TerminalExpression {

	private RGBColor myValue;
	// double is made up of an optional negative sign, followed by a sequence
	// of one or more digits, optionally followed by a period, then possibly
	// another sequence of digits
	private static final Pattern DOUBLE_REGEX = Pattern
			.compile("(\\-?[0-9]+(\\.[0-9]+)?)|(\\-?\\.[0-9]+)");

	private NumberExpression(RGBColor gray) {
		myValue = gray;
	}

	public NumberExpression() {	}

	//some safety issues...
	@Override
	protected Expression getNewExpression() {
		return new NumberExpression(myValue);
	}

	public static ExpressionFactory getFactory() {
		return new ExpressionFactory(new NumberExpression());
	}

	@Override
	public boolean isThisCommand(String command, int currentPosition) {
		Matcher doubleMatcher = DOUBLE_REGEX.matcher(command
				.substring(currentPosition));
		return doubleMatcher.lookingAt();
	}

	@Override
	public void parseCommand(String myInput, int myCurrentPosition) {
		Matcher doubleMatcher = DOUBLE_REGEX.matcher(myInput);
		doubleMatcher.find(myCurrentPosition);
		String numberMatch = myInput.substring(doubleMatcher.start(),
				doubleMatcher.end());
		changePosition(doubleMatcher.end());
		// this represents the color gray of the given intensity
		double value = Double.parseDouble(numberMatch);
		myValue = new RGBColor(value);
	}

	@Override
	public RGBColor evaluate() {
		return myValue;
	}

    @Override
    String getMyCommand() {
        return myValue.toString();
    }

}
