import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DeSHA256CPU {
	static byte[] pwd = null;

	static Runnable monitor = new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					if (pwd != null) {
						System.out.println("Last tested password: "
								+ new String(pwd, "US-ASCII"));
					}
					printStat();

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	};

	static long ts1;

	public static void printStat() {
		long tsDiff = System.currentTimeMillis() - ts1;
		System.out.println("Dehash time: " + tsDiff + " milliseconds");
		BigInteger wordIdx = DictGen.getWordIndex(pwd);
		System.out.println("Dict word idx: " + wordIdx.longValue());
		BigInteger rate = wordIdx.multiply(BigInteger.valueOf(1000)).divide(
				BigInteger.valueOf(tsDiff));
		System.out.println("Dehash attempts: " + wordIdx + " words");
		System.out.println("Dehash rate: " + rate + " hashes/sec");
	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789abcdef".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static void example() throws Exception {

		final byte[] text = "bbbb".getBytes();
		System.out.println("Password: " + new String(text, "US-ASCII"));

		final MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text);
		byte[] hash = md.digest();
		System.out.println("SHA-256 Hashed Password: " + bytesToHex(hash));

		System.out.println("Dehash process starts now:");
		ts1 = System.currentTimeMillis();
		pwd = null;
		while (true) {
			pwd = DictGen.nextWord(pwd);
			md.update(pwd);
			byte[] digest = md.digest();
			if (Arrays.equals(digest, hash)) {
				System.out.println("Password is found: "
						+ new String(pwd, "US-ASCII"));
				break;
			}
		}
		System.out.println("Dehash process completed.");
		printStat();
	}
	
	public static void main(String[] args) throws Exception {
		Thread t = new Thread(monitor);
		t.start();

		example();

		System.exit(0);
	}
}
