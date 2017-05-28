/**
 * 
 */

/**
 * @author Leo
 *
 */
public class InterfaceUse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InterfaceYes a = new InterfaceYes();
		@SuppressWarnings("unused")
		InterfaceNo b = new InterfaceNo();

		Class<?>[] interfaces = a.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; ++i) {
			System.out.println(interfaces[i]);
		}
		System.out.println("listet all interfaces");

		TestInterface c = a;
		// TestInterface d = (TestInterface) b;
		System.out.println(c.bIsUsingTestInterface());
		// System.out.println(d.bIsUsingTestInterface());

	}

}
