package com.clj.blesample.utils;

import static com.clj.fastble.utils.HexUtil.charToByte;

public class FormatConversion {

    public static void doGetLoopData(byte[] data) {

        int pos0, pos1, pos2, pos3, pos4, pos5, pos6, pos7;

        for (int i = data.length - 1; i >= 0; i--) {
            System.out.println("LoopValue" + data[i]);
            //j=data[i];

            if (i == 7) {
                pos7 = data[i];

                //show_data.setText(String.valueOf(data[i]));
            }
            if (i == 6) {
                pos6 = data[i];
            }

            if (i == 5) {
                pos5 = data[i];
            }
            if (i == 4) {
                pos4 = data[i];
            }
            if (i == 3) {
                pos3 = data[i];
            }
            if (i == 2) {
                pos2 = data[i];
            }
            if (i == 1) {
                pos1 = data[i];
            }

            if (i == 0) {
                pos0 = data[i];
            }

        }

    }

    public static int hexaDecimalToDecimal(String hexadecimal) {

        int decimal = Integer.parseInt(hexadecimal, 16);

        return decimal;

    }

    public static String decimalToBinary(int decimal) {

        String binary = Integer.toBinaryString(decimal);

        return binary;

    }

    public static byte[] stringToByte(String val) {

        byte[] a = new byte[1];
        String s = "0x";

        //a={(byte)s+""+val};


        return a;
    }

    public static String integerToString(int data) {

        String str_val = String.valueOf(data);

        return str_val;
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.trim();
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

            System.out.println("DataFind" + d[i]);

        }
        return d;
    }


    /*private void doFindKnobAngle() {
        String knobAngel = String.valueOf(pos1) + String.valueOf(pos2) + String.valueOf(pos3) + String.valueOf(pos4)
                + String.valueOf(pos5);

        System.out.println("Received Knob angle in binary value " + knobAngel);
        int decimal = Integer.parseInt(knobAngel, 2);

        System.out.println("Knob Angel " + decimal);

        show_data.setText("" + decimal);

        knobAngleTop.setText("" + decimal);
    }
*/


    //User entered data to hexadecimal conversion
    public static int dataToRanceConversion(int userEnteredHexData) {

        int userEnterBurPos = userEnteredHexData;
        int finalHex = 0;

        if (userEnterBurPos >=0 && userEnterBurPos <= 5) {
            finalHex = 0;

        } else if (userEnterBurPos >5 && userEnterBurPos <= 10) {
            finalHex = 1;

        } else if (userEnterBurPos > 10 && userEnterBurPos <= 20) {
            finalHex = 2;

        } else if (userEnterBurPos > 20 && userEnterBurPos <= 30) {
            finalHex = 3;

        } else if (userEnterBurPos > 30 && userEnterBurPos <= 40) {
            finalHex = 4;

        } else if (userEnterBurPos > 40 && userEnterBurPos <= 50) {
            finalHex = 5;

        } else if (userEnterBurPos > 50 && userEnterBurPos <= 60) {
            finalHex = 6;
        } else if (userEnterBurPos > 60 && userEnterBurPos <= 70) {
            finalHex = 7;
        } else if (userEnterBurPos > 70 && userEnterBurPos <= 80) {
            finalHex = 8;
        } else if (userEnterBurPos > 80 && userEnterBurPos <= 90) {
            finalHex = 9;
        } else if (userEnterBurPos > 90 && userEnterBurPos <= 100) {
            finalHex = 10;
        } else if (userEnterBurPos > 100 && userEnterBurPos <= 110) {
            finalHex = 11;
        } else if (userEnterBurPos > 110 && userEnterBurPos <= 120) {
            finalHex = 12;
        } else if (userEnterBurPos > 120 && userEnterBurPos <= 130) {
            finalHex = 13;
        } else if (userEnterBurPos > 130 && userEnterBurPos <= 140) {
            finalHex = 14;
        } else if (userEnterBurPos > 140 && userEnterBurPos <= 150) {
            finalHex = 15;
        } else if (userEnterBurPos > 150 && userEnterBurPos <= 160) {
            finalHex = 16;
        } else if (userEnterBurPos > 160 && userEnterBurPos <= 170) {
            finalHex = 17;
        } else if (userEnterBurPos > 170 && userEnterBurPos <= 180) {
            finalHex = 18;
        }

        return finalHex;
    }


    public static int stringToInt(String data){

        int whistleCount=Integer.parseInt(data);

        return  whistleCount;
    }

    public static String convertBinaryToHexadecimal(String number) {
        String hexa = "";
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f'};
        if (number != null && !number.isEmpty()) {
            int decimal = convertBinaryToDecimal(number);
            while (decimal > 0) {
                hexa = hex[decimal % 16] + hexa;
                decimal /= 16;
            }
            //System.out.println("The hexa decimal number is: " + hexa);

            //finalHexValue=hexa;
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

    public static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);

            b[i] = (byte) v;
        }
        return b;

    }


}
