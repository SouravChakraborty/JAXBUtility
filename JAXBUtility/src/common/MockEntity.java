package common;

/**
 * The Class MockEntity.
 * 
 * @author Sourav
 */
@JAXBConvertAnnotation(className = "generated.JaxbOuter")
public class MockEntity {

	/** The a. */
	@JAXBConvertAnnotation(fieldName = "a", xmlFieldName = "valueA")
	private String a;

	/** The b. */
	@JAXBConvertAnnotation(fieldName = "b", xmlFieldName = "valueB")
	private String b;

	/** The c. */
	@JAXBConvertAnnotation(fieldName = "c", xmlFieldName = "valueC")
	private Long c;

	/**
	 * Gets the a.
	 * 
	 * @return the a
	 */
	public String getA() {
		return a;
	}

	/**
	 * Sets the a.
	 * 
	 * @param a
	 *            the new a
	 */
	public void setA(String a) {
		this.a = a;
	}

	/**
	 * Gets the b.
	 * 
	 * @return the b
	 */
	public String getB() {
		return b;
	}

	/**
	 * Sets the b.
	 * 
	 * @param b
	 *            the new b
	 */
	public void setB(String b) {
		this.b = b;
	}

	/**
	 * Gets the c.
	 * 
	 * @return the c
	 */
	public Long getC() {
		return c;
	}

	/**
	 * Sets the c.
	 * 
	 * @param c
	 *            the new c
	 */
	public void setC(Long c) {
		this.c = c;
	}

}
