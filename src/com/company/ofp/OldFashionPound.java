package com.company.ofp;

import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.company.ofp.exception.NegativeResultException;

/**
 * <p>
 * Write a library (package OldFashionPound) implementing the 4 arithmetic
 * operations (sum, subtraction, multiplication and division) for pre-1970 UK
 * prices.
 * </p>
 * 
 * <p>
 * Under the old money system of UK, before 1970, there were 12 pence in a
 * shilling and 20 shillings, or 240 pence, in a pound. Thus, a price in the
 * OldUK Money system was expressed in Pounds, Shillings and Pence. For example
 * "12p 6s 10d" is 12 Pounds, 6 Shillings and 10 Pence. Sum and Subtraction
 * should add or subtract two prices respectively.
 * </p>
 * 
 * <p>
 * Example SUM:                  5p 17s 8d + 3p 4s 10d= 9p 2s 6d Example
 * SUBTRACTION:               5p 17s 8d - 3p 4s 10d= 2p 12s 10d Subtractions
 * giving negative results should be managed.
 * </p>
 * 
 * <p>
 * Multiplication and division will multiply or divide a price by an integer (no
 * decimal is necessary) Example MULTIPLICATION:         5p 17s 8d * 2 = 11p 15
 * s 4d Example DIVISION                          5p 17s 8d / 3 = 1p 19s 2d (2p)
 * (2 pence as remainder - the remainder should be represented between
 * parenthesis) Another Example DIVISION          18p 16s 1d / 15 = 1p 5s 0d
 *   (1s 1d) (1 Shillings and 1 penny as remainder - the remainder should be
 * represented between parenthesis)
 * </p>
 * 
 * <p>
 * The library should be able to receive and produce strings in the format "Xp
 * Ys Zd", as in the examples
 * </p>
 */
public class OldFashionPound {

	/**
	 * A constant holding the value of the amount of pennies in a shilling.
	 */
	private static final int PENCE_IN_SHILLING = 12;

	/**
	 * A constant holding the value of the amount of pennies in a pound.
	 */
	private static final int PENCE_IN_POUND = 240;

	private int pound;
	private int shilling;
	private int penny;
	private int remainder;

	/**
	 * All args constructor.
	 * 
	 * @param pound
	 * @param shilling
	 * @param penny
	 * @param remainder
	 */
	private OldFashionPound(int pound, int shilling, int penny, int remainder) {
		this.pound = pound;
		this.shilling = shilling;
		this.penny = penny;
		this.remainder = remainder;
	}

	private OldFashionPound(int pound, int shilling, int penny) {
		this(pound, shilling, penny, 0);
	}

	/**
	 * Initializes {@link OldFashionPound} to specified string value. Splits the
	 * given string into separate values by spaces, iterates through the entries and
	 * removes characters that are not digits, or '-'.
	 * 
	 * @param value String value to be converted into OldFashionPound.
	 * @return Initialized OldFashionPound object.
	 */
	public static OldFashionPound valueOf(String value) {
		List<String> stringValues = Stream.of(value.split("\\s+")).map(e -> new String(e.replaceAll("[^\\-\\d.]", ""))).collect(Collectors.toList());
		List<Integer> integerValues = stringValues.stream().map(Integer::parseInt).collect(Collectors.toList());

		return new OldFashionPound(integerValues.get(0), integerValues.get(1), integerValues.get(2));
	}

	/**
	 * Adds the addend to the given number.
	 * 
	 * @param addend addend
	 * @return {@link OldFashionPound} representation of the result.
	 * @throws NegativeResultException Thrown when operation result is negative.
	 */
	public OldFashionPound sum(OldFashionPound addend) throws NegativeResultException {
		int penceA = oldFashionPoundToPence(this);
		int penceB = oldFashionPoundToPence(addend);
		int penceSum = sum(penceA, penceB);

		OldFashionPound result = penceToOldFashionPound(penceSum);

		return result;
	}

	/**
	 * Subtracts the subtrahend from the given number.
	 * 
	 * @param subtrahend subtrahend
	 * @return {@link OldFashionPound} representation of the result.
	 * @throws NegativeResultException Thrown when operation result is negative.
	 */
	public OldFashionPound subtract(OldFashionPound subtrahend) throws NegativeResultException {
		int penceA = oldFashionPoundToPence(this);
		int penceB = oldFashionPoundToPence(subtrahend);

		int penceSum = sum(penceA, -penceB);

		OldFashionPound result = penceToOldFashionPound(penceSum);

		return result;
	}

	/**
	 * Multiplies the given number with the multiplier.
	 * 
	 * @param multiplier multiplier
	 * @return {@link OldFashionPound} representation of the result.
	 * @throws NegativeResultException Thrown when operation result is negative.
	 */
	public OldFashionPound multiply(int multiplier) throws NegativeResultException {
		int pence = oldFashionPoundToPence(this);

		int penceProduct = pence * multiplier;
		if (penceProduct < 0) {
			throw new NegativeResultException("Operation result can not be negative.");
		}

		OldFashionPound result = penceToOldFashionPound(penceProduct);

		return result;
	}

	/**
	 * Divides the given number with the divisor and handles the remainder.
	 * 
	 * @param divisor divisor
	 * @return {@link OldFashionPound} representation of the result with remainder.
	 * @throws NegativeResultException Thrown when operation result is negative.
	 */
	public OldFashionPound divide(int divisor) throws NegativeResultException {
		int pence = oldFashionPoundToPence(this);

		int penceQuotient = pence / divisor;
		if (penceQuotient < 0) {
			throw new NegativeResultException("Operation result can not be negative.");
		}

		int penceRemainder = pence % divisor;

		OldFashionPound result = penceToOldFashionPound(penceQuotient, penceRemainder);

		return result;
	}

	/**
	 * Adds two ints together and checks for negative result.
	 * 
	 * @param a addend a
	 * @param b addend b
	 * @return sum of addends a and b
	 * @throws NegativeResultException Thrown when sum result is negative.
	 */
	private static int sum(int a, int b) throws NegativeResultException {
		int sum = a + b;
		if (sum < 0) {
			throw new NegativeResultException("Operation result can not be negative.");
		}

		return sum;
	}

	/**
	 * Converts {@link OldFashionPound} to pence
	 * 
	 * @param oldFashionPound The currency representation to be converted.
	 * @return Pence representation of the currency.
	 */
	private static int oldFashionPoundToPence(OldFashionPound oldFashionPound) {
		return oldFashionPound.penny + oldFashionPound.shilling * 12 + oldFashionPound.pound * 240;
	}

	/**
	 * Converts pence into {@link OldFashionPound}
	 * 
	 * @param pence     value to be converted
	 * @param remainder division operation remainder
	 * @return {@link OldFashionPound} representation of the pence value
	 */
	private static OldFashionPound penceToOldFashionPound(int pence, int remainder) {
		int oldPound = pence / PENCE_IN_POUND;
		int oldShilling = pence % PENCE_IN_POUND / PENCE_IN_SHILLING;
		int oldPence = pence % PENCE_IN_POUND % PENCE_IN_SHILLING;

		return new OldFashionPound(oldPound, oldShilling, oldPence, remainder);
	}

	/**
	 * Converts pence into {@link OldFashionPound} with remainder 0.
	 * 
	 * @param pence value to be converted
	 * @return {@link OldFashionPound} representation of the pence value
	 */
	private static OldFashionPound penceToOldFashionPound(int pence) {
		return penceToOldFashionPound(pence, 0);
	}

	/**
	 * Formats the remainder as a String.
	 * 
	 * @param remainder {@link OldFashionPound} representation of the remainder
	 * @return The formatted remainder String.
	 */
	private static String formatRemainder(OldFashionPound remainder) {
		StringBuilder stringBuilder = new StringBuilder();

		String pound = remainder.pound == 0 ? null : String.valueOf(remainder.pound) + "p ";
		String shilling = remainder.shilling == 0 ? null : String.valueOf(remainder.shilling) + "s ";
		String pence = remainder.penny == 0 ? null : String.valueOf(remainder.penny) + "d ";

		stringBuilder.append(String.format("%s", ofNullable(pound).orElse("")));
		stringBuilder.append(String.format("%s", ofNullable(shilling).orElse("")));
		stringBuilder.append(String.format("%s", ofNullable(pence).orElse("")));

		return stringBuilder.toString().trim();
	}

	/**
	 * Returns a string representation of {@link OldFashionPound} along with the
	 * remainder (if any).
	 * 
	 * @return The formatted string.
	 */
	@Override
	public String toString() {
		String result = String.format("%dp %ds %dd", this.pound, this.shilling, this.penny);
		String remainder = formatRemainder(penceToOldFashionPound(this.remainder));

		return remainder.isEmpty() ? result : String.format("%s (%s)", result, remainder);
	}

	public int getPound() {
		return pound;
	}

	public void setPound(int pound) {
		this.pound = pound;
	}

	public int getShilling() {
		return shilling;
	}

	public void setShilling(int shilling) {
		this.shilling = shilling;
	}

	public int getPenny() {
		return penny;
	}

	public void setPenny(int penny) {
		this.penny = penny;
	}

	public int getRemainder() {
		return remainder;
	}

	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}
}
