package com.clj.blesample.utils;

public class TotalConversion {

  /*package collections;

public class hexStringToByteArray {

	static byte[] b;

	static String finalHexValue="";

	public static void main(String[] args) {




		int angle=20;
		String burner="00";
		String vessel="1";


		String binary=vessel+Integer.toBinaryString(angle)+""+burner;

		System.out.println("Converted Binary Value "+binary);

		String binar=binary;

		finalHexValue=convertBinaryToHexadecimal(binar);

		System.out.println("Converting Binary Value To Hexadecimal"+" "+finalHexValue);

	//To put hexadecimal value in byte array. Need to convert hexadecimal value into byte

		byte[] a=hexStringToByteArray(finalHexValue);


		//String s="50";
		//byte a[]={(byte)0xf8};

		System.out.println("Converting Hex value into byte "+a);


		String hex=byteToHexaDecimal(a);
		System.out.println("Converting byte into Hex "+hex);

		//byte[] get=hexStringToByteArray(s);

		//String hexFormat=byteToHexaDecimal(get);

		//System.out.println("ReceivedHexValue "+hexFormat);

		//System.out.println("ReceivedByteValue "+get);
	}



	private static String convertBinaryToHexadecimal(String number) {
		 String hexa = "";
	        char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
	                'b', 'c', 'd', 'e', 'f' };
	        if (number != null && !number.isEmpty()) {
	            int decimal = convertBinaryToDecimal(number);
	            while (decimal > 0) {
	                hexa = hex[decimal % 16] + hexa;
	                decimal /= 16;
	            }
	            //System.out.println("The hexa decimal number is: " + hexa);

	            finalHexValue=hexa;
	        }
	        return hexa;


	}

	 public static int convertBinaryToDecimal(String number) {
	        int length = number.length() - 1;
	        int decimal = 0;
	        if (isBinary(number)) {
	            char[] digits = number.toCharArray();
	            for (char digit : digits) {
	                if (String.valueOf(digit).equals("1")) {
	                    decimal += Math.pow(2, length);
	                }
	                --length;
	            }
	           // System.out.println("The decimal number is : " + decimal);
	        }
	        return decimal;
	    }


	 public static boolean isBinary(String number) {
	        boolean isBinary = false;
	        if (number != null && !number.isEmpty()) {
	            long num = Long.parseLong(number);
	            while (num > 0) {
	                if (num % 10 <= 1) {
	                    isBinary = true;
	                } else {
	                    isBinary = false;
	                    break;
	                }
	                num /= 10;
	            }
	        }
	        return isBinary;
	    }




	private static String byteToHexaDecimal(byte[] hashInBytes) {
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashInBytes.length; i++) {
            sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

	}

	private static byte[] hexStringToByteArray(String s) {
	 b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);

	      b[i] = (byte) v;
	    }
	    return b;

	}

}
*/
}
