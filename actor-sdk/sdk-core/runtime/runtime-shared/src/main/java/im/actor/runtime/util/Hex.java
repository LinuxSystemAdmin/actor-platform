package im.actor.runtime.util;

public class Hex {

    public static byte[] fromHex(String hex) {
        byte[] res = new byte[hex.length() / 2];
        for (int j = 0; j < hex.length() / 2; j++) {
            String dg = hex.charAt(j * 2) + "" + hex.charAt(j * 2 + 1);
            res[j] = (byte) Integer.parseInt(dg, 16);
        }
        return res;
    }

    public static byte[] fromHexReverse(String hex) {
        byte[] res = new byte[hex.length() / 2];
        for (int j = 0; j < hex.length() / 2; j++) {
            String dg = hex.charAt(j * 2) + "" + hex.charAt(j * 2 + 1);
            res[res.length - j - 1] = (byte) Integer.parseInt(dg, 16);
        }
        return res;
    }

    private static final String HEXES = "0123456789ABCDEF";

    public static String toHex(byte[] raw) {
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}
