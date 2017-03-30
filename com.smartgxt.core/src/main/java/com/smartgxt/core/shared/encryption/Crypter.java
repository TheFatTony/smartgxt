package com.smartgxt.core.shared.encryption;


/**
 * @author Anton Alexeyev
 * 
 */
public class Crypter {

	// private TripleDesCipher cipher;

	private byte key[] = { 0x01, 0x05, 0x04, 0x03, 0x00, 0x06, 0x03, 0x01,
			0x01, 0x03, 0x06, 0x02, 0x02, 0x05, 0x03, 0x00, 0x05, 0x04, 0x00,
			0x00, 0x04, 0x03, 0x01, 0x02 };
	// private byte key[];

	private static Crypter instance = new Crypter();

	public Crypter() {
		// cipher = new TripleDesCipher();
		// key = new byte[24];
		// for (int i = 0; i < key.length; ++i) {
		// Integer randomInt = Random.nextInt(9);
		// key[i] = randomInt.byteValue();
		// }
		// cipher.setKey(key);
	}

	public Crypter(byte[] key) {
		this();
		// cipher.setKey(key);
	}

	public String decrypt(String what) {
		// try {
		// return cipher.decrypt(what);
		// } catch (DataLengthException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalStateException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InvalidCipherTextException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return what;
	}

	public String encrypt(String what) {
		// try {
		// return cipher.encrypt(what);
		// } catch (DataLengthException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalStateException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InvalidCipherTextException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return what;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public static Crypter get() {
		return instance;
	}

}
