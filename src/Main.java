import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static private Scanner scan;
    static private Map<Character, Integer> romanNum;
    static private Map<Integer, String> arabicNum;

    public static void main(String[] args) throws Exception {
        romanNum = new HashMap<>() {
            {
                put('I', 1);
                put('V', 5);
                put('X', 10);
                put('L', 50);
                put('C', 100);
                put('D', 500);
                put('M', 1000);
            }
        };
        arabicNum = new LinkedHashMap<>() {
            {
                put(1000, "M");
                put(900, "CM");
                put(500, "D");
                put(400, "CD");
                put(100, "C");
                put(90, "XC");
                put(50, "L");
                put(40, "XL");
                put(10, "x");
                put(9, "IX");
                put(5, "V");
                put(4, "IV");
                put(1, "I");

            }
        };
        scan = new Scanner(System.in);
        String inText = scan.nextLine();

        if (inText.matches("^[1-90]+\s*[-+/*]\s*[1-90]+$")) {
            arabicCalc(inText);
        } else if (inText.matches("^([IVX])+\s*[-+/*]\s*([IVX])+$")) {
            romanCalc(inText);
        } else {
            throw new Exception();
        }
    }

    static void arabicCalc(String inText) throws Exception {
        if (inText.contains("+")) {
            int[] num = arabicParse(inText.split("\\+"));
            System.out.println(num[0] + num[1]);
        } else if (inText.contains("-")) {
            int[] num = arabicParse(inText.split("\\-"));
            System.out.println(num[0] - num[1]);
        } else if (inText.contains("*")) {
            int[] num = arabicParse(inText.split("\\*"));
            System.out.println(num[0] * num[1]);
        } else if (inText.contains("/")) {
            int[] num = arabicParse(inText.split("\\/"));
            System.out.println(num[0] / num[1]);
        }
    }

    static void romanCalc(String inText) throws Exception {
        if (inText.contains("+")) {
            int[] num = romanParse(inText.split("\\+"));
            System.out.println(arabicToRoman(num[0] + num[1]));
        } else if (inText.contains("-")) {
            int[] num = romanParse(inText.split("\\-"));
            System.out.println(arabicToRoman(num[0] - num[1]));
        } else if (inText.contains("*")) {
            int[] num = romanParse(inText.split("\\*"));
            System.out.println(arabicToRoman(num[0] * num[1]));
        } else if (inText.contains("/")) {
            int[] num = romanParse(inText.split("\\/"));
            System.out.println(arabicToRoman(num[0] / num[1]));
        }
    }

    static int[] arabicParse(String[] inText) throws Exception {
        int[] num = new int[2];

        num[0] = Integer.parseInt(inText[0].trim());
        num[1] = Integer.parseInt(inText[1].trim());

        if (num[0] > 10 || num[1] > 10 || num[0] < 1 || num[1] < 1) {
            throw new Exception();
        }

        return num;
    }

    static int[] romanParse(String[] inText) throws Exception {
        int[] num = new int[2];

        num[0] = romanToArabic(inText[0].trim());
        num[1] = romanToArabic(inText[1].trim());

        if (num[0] > 10 || num[1] > 10 || num[0] < 1 || num[1] < 1) {
            throw new Exception();
        }

        return num;
    }

    public static int romanToArabic(String roman) {
        if (roman.length() == 0)
            return 0;
        int integerValue = 0;
        int prevNumber = romanNum.get(roman.charAt(0));
        for (int i = 1; i < roman.length(); i++) {
            char ch = roman.charAt(i);
            int number = romanNum.get(ch);
            if (number <= prevNumber)
                integerValue += prevNumber;
            else
                integerValue -= prevNumber;
            prevNumber = number;
        }
        integerValue += prevNumber;
        return integerValue;
    }

    public static String arabicToRoman(int arabic) throws Exception {
        if (arabic < 1) {
            throw new Exception();
        }
        StringBuilder rom = new StringBuilder();

        for (Map.Entry<Integer, String> entry: arabicNum.entrySet()) {
            while (arabic >= entry.getKey()){
                double max = arabic / entry.getKey();
                arabic = arabic % entry.getKey();
                for (int i = 0; i < max; i++)
                    rom.append(entry.getValue());
            }
        }

        return rom.toString();

    }
}