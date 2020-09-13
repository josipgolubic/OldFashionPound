# OldFashionPound
A library implementing the 4 arithmetic operations (sum, subtraction, multiplication and division) for pre-1970 UK prices.

Build with **mvn clean install**.

Example usage:
```
	public static void main(String[] args) {

		OldFashionPound a = OldFashionPound.valueOf("18p 16s 1d");
		OldFashionPound b = OldFashionPound.valueOf("3p 4s 10d");

		OldFashionPound r;
		try {
			r = a.divide(2258);
			System.out.println(r);
		} catch (NegativeResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
```
