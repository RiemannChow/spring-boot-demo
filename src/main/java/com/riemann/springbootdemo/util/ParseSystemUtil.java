package com.riemann.springbootdemo.util;

/**
 * @author riemann
 * @date 2019/06/12 0:29
 */
public class ParseSystemUtil {

    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
    //    String str = null;
        System.out.println("hexStr" + hexStr);
        byte[] result = null;
        if (hexStr.length() < 1)
            return null;
        result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            Integer high = Integer.valueOf(hexStr.substring(i*2, i*2+1));
            Integer low = Integer.valueOf(hexStr.substring(i*2+1, i*2+2));
            result[i] = (byte) (high * 16 + low);
        }
        //    str = new String(result, "UTF-8");
        System.out.println("sout: " + result);
        return result;
    }

}
