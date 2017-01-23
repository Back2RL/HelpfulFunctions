/**
 * Created by leonard on 05.01.17.
 */
public class IP {
	public static void main(String args[]) {

		int ipAddress = 100 << 24 | 200 << 16 | 66 << 8 | 10;

		BitShift.writeBinary(ipAddress);

		int netMask = 255 << 24 | 255 << 16 | 192 << 8 | 0;

		BitShift.writeBinary(netMask);


		int subnet = ipAddress & netMask;

		BitShift.writeBinary(subnet);

		int pcAddress = ipAddress & ~subnet;

		BitShift.writeBinary(pcAddress);
		bsn1_ueb07_6();
	}

	public static int generateNetMask(final int n) {
		int mask = 0;

		for (int i = 0; i < n; ++i) {
			mask |= 1 << (31 - i);
		}

		return mask;
	}

	public static void addressToString(final int address) {
		int a = ((255 << 24) & address) >> 24;
		System.out.print(256 + a + ".");
		a = ((255 << 16) & address) >> 16;
		System.out.print(a + ".");
		a = ((255 << 8) & address) >> 8;
		System.out.print(a + ".");
		a = 255 & address;
		System.out.println(a);
	}

	private static void bsn1_ueb07_6() {
		System.out.println("a)");
		int mask1 = generateNetMask(22);
		BitShift.writeBinary(mask1);
		int targetIPAddress = 135 << 24 | 46 << 16 | 63 << 8 | 10;
		int netAddress = targetIPAddress & mask1;
		BitShift.writeBinary(netAddress);
		addressToString(netAddress);

		System.out.println("b)");
		mask1 = generateNetMask(22);
		BitShift.writeBinary(mask1);
		targetIPAddress = 135 << 24 | 46 << 16 | 57 << 8 | 14;
		netAddress = targetIPAddress & mask1;
		BitShift.writeBinary(netAddress);
		addressToString(netAddress);

		System.out.println("c)");
		mask1 = generateNetMask(23);
		BitShift.writeBinary(mask1);
		targetIPAddress = 135 << 24 | 46 << 16 | 52 << 8 | 2;
		netAddress = targetIPAddress & mask1;
		BitShift.writeBinary(netAddress);
		addressToString(netAddress);

		System.out.println("d)");
		mask1 = generateNetMask(23);
		BitShift.writeBinary(mask1);
		targetIPAddress = 192 << 24 | 53 << 16 | 40 << 8 | 7;
		netAddress = targetIPAddress & mask1;
		BitShift.writeBinary(netAddress);
		addressToString(netAddress);

		System.out.println("e)");
		mask1 = generateNetMask(23);
		BitShift.writeBinary(mask1);
		targetIPAddress = 192 << 24 | 53 << 16 | 56 << 8 | 7;
		netAddress = targetIPAddress & mask1;
		BitShift.writeBinary(netAddress);
		addressToString(netAddress);

	}
}
