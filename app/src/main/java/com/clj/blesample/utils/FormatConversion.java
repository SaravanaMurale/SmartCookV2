package com.clj.blesample.utils;

import static com.clj.fastble.utils.HexUtil.charToByte;

public class FormatConversion {

    public static void doGetLoopData(byte[] data){

        int pos0,pos1,pos2,pos3,pos4,pos5,pos6,pos7;

        for(int i=data.length-1;i>=0;i--){
            System.out.println("LoopValue"+data[i]);
            //j=data[i];

            if(i==7) {
                pos7=data[i];

                //show_data.setText(String.valueOf(data[i]));
            }
            if(i==6){
                pos6=data[i];
            }

            if(i==5){
                pos5=data[i];
            }
            if(i==4){
                pos4=data[i];
            }
            if(i==3){
                pos3=data[i];
            }
            if(i==2){
                pos2=data[i];
            }
            if(i==1){
                pos1=data[i];
            }

            if(i==0){
                pos0=data[i];
            }

        }

    }

    public static int hexaDecimalToDecimal(String hexadecimal){

        int decimal = Integer.parseInt(hexadecimal, 16);

        return decimal;

    }

    public static String decimalToBinary(int decimal){

        String binary = Integer.toBinaryString(decimal);

        return binary;

    }

    public static byte[] stringToByte(String val){

        byte[] a=new byte[1];
        String s="0x";

        //a={(byte)s+""+val};


        return a;
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

            System.out.println("DataFind"+d[i]);

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


}
