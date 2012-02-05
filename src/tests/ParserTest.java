package tests;

import java.awt.Color;
import model.Parser;
import model.ParserException;
import model.RGBColor;
import static org.junit.Assert.*;
import org.junit.*;


public class ParserTest
{
    // useful constants
    private static final RGBColor BLACK = new RGBColor(Color.BLACK);
    private static final RGBColor GRAY = new RGBColor(0.5);
    private static final RGBColor WHITE = new RGBColor(Color.WHITE);

    // collective state
    private Parser myParser;


    @Before
    public void setUp ()
    {
        // initialize stuff here
        myParser = new Parser();
    }


    @Test
    public void testConstant ()
    {
        // extreme color values
        runTest(WHITE, "  1");
        runTest(BLACK, " -1");
        // simple color examples
        runTest(GRAY,  "0.5");
        runTest(GRAY,  " .5");
        runTest(GRAY,  "0.50000");
    }

    @Test
    public void testBinaryOps ()
    {
        // simple binary op
        runTest(WHITE, "(plus .1 .9)");
        // nested binary op
        runTest(WHITE, "(plus (plus 0.01 0.09) (plus 0.4 0.5))");
        // same as above, but with interesting spacing
        runTest(WHITE, "    (plus(plus 0.01 0.09)(plus 0.4 0.5   ))    ");
        // nested binary op with a variety of operations
        runTest(WHITE, "(minus (div 1.8 2) (mul -10 0.01))");
    }


    @Test
    public void testExceptions ()
    {
        runExceptionalTest(ParserException.Type.BAD_SYNTAX, "--1");
        runExceptionalTest(ParserException.Type.EXTRA_CHARACTERS, "0.5 0.5");
        runExceptionalTest(ParserException.Type.UNKNOWN_COMMAND,  "(fooo 0.1 0.9)");
    }


    private void runExceptionalTest (ParserException.Type type, String expression)
    {
        try
        {
            myParser.makeExpression(expression).evaluate();
            fail("Exception should have been thrown");
        }
        catch (ParserException e)
        {
            assertEquals(type, e.getType());
        }
    }

    private void runTest (RGBColor expected, String expression)
    {
        RGBColor actual = myParser.makeExpression(expression).evaluate();
        assertTrue(actual.equals(expected));
    }
}
