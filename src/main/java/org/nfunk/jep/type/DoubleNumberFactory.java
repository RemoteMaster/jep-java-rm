/*****************************************************************************

 @header@
 @date@
 @copyright@
 @license@
 *****************************************************************************/

package org.nfunk.jep.type;

import org.nfunk.jep.ParseException;

/**
 * Default class for creating number objects. This class can be replaced by
 * other NumberFactory implementations if other number types are required. This
 * can be done using the
 */
public class DoubleNumberFactory implements NumberFactory
{
	public static Double ZERO = new Double(0.0);
	public static Double ONE = new Double(1.0);
	public static Double TWO = new Double(2.0);
	public static Double MINUSONE = new Double(-1.0);

	/**
	 * Creates a Double object initialized to the value of the parameter.
	 *
	 * @param value The initialization value for the returned object.
	 */
	public Object createNumber(String value) throws ParseException
	{
		char qualifier;
		double res;
		int nextcolon = value.indexOf(':');

		if (nextcolon > 0) {
			int runner = 0;
			int parts = 0;
			res = 0;

			do {
				res = res * 60d + Double.parseDouble(value.substring(runner, nextcolon));
				parts++;
				runner = nextcolon + 1;
				nextcolon = value.indexOf(':', runner);
			}
			while (nextcolon > 0);
			res = res * 60d + Double.parseDouble(value.substring(runner));
			if (parts < 2) {
				res *= 60d;
			}
			return new Double(res);
		}

		qualifier = value.charAt(value.length() - 1);
		if (Character.isLetter(qualifier)) {
			res = Double.parseDouble(value.substring(0, value.length() - 1));
			switch (qualifier) {
				case 'd':
					res *= 24 * 60 * 60;
					break;
				case 'k':
				case 'K':
					res *= 1000;
					break;
				case 'M':
					res *= 1000000;
					break;
				case 'G':
					res *= 1000000000;
					break;
				case 'm':
					res /= 1000;
					break;
				case 'u':
					res /= 1000000;
					break;
				case 'n':
					res /= 1000000000;
					break;
				default:
					throw new ParseException("unknown qualifier '" + qualifier + "'");
			}
			return new Double(res);
		}

		return new Double(value);
	}

	public Object createNumber(double value)
	{
		return new Double(value);
	}

	public Object createNumber(Number value)
	{
		return value;
	}

	public Object createNumber(boolean value)
	{
		return (value ? ONE : ZERO);
	}

	public Object createNumber(float value)
	{
		return new Double(value);
	}

	public Object createNumber(int value)
	{
		return new Double(value);
	}

	public Object createNumber(short value)
	{
		return new Double(value);
	}

	public Object createNumber(Complex value) throws ParseException
	{
		throw new ParseException("Cannot create a number from a Complex value");
	}

	public Object getMinusOne()
	{
		return MINUSONE;
	}

	public Object getOne()
	{
		return ONE;
	}

	public Object getTwo()
	{
		return TWO;
	}

	public Object getZero()
	{
		return ZERO;
	}
}
