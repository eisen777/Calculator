import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String output;
        output = calc(input, container);
        System.out.println(output);
    }

    public static String calc(String input, Container container) {

        int result = 0;
        positionAndOperatorIdentify(input, container);
        operandsValueRecognize(input, container);
        switch (container.getOperator()) {
            case "+" -> result = container.getTheFirstOperand() + container.getTheSecondOperand();
            case "-" -> result = container.getTheFirstOperand() - container.getTheSecondOperand();
            case "*" -> result = container.getTheFirstOperand() * container.getTheSecondOperand();
            case "/" -> result = container.getTheFirstOperand() / container.getTheSecondOperand();
        }
        String stringResult;
        if (container.getIsLatin()) {
            stringResult = arabToRoman(result);
        } else {
            stringResult = String.valueOf(result);
        }
        return stringResult;
    }

    static void positionAndOperatorIdentify(String input, Container container) {
        if (!input.contains("+") & !input.contains("-") &
                !input.contains("/") & !input.contains("*")) {
            throw new ArithmeticException("The string is not a mathematical operation!");
        }

        int isSum = input.indexOf('+');
        int isSub = input.indexOf('-');
        int isMult = input.indexOf('*');
        int isDiv = input.indexOf('/');
        if (isSum != -1) {
            container.setPosition(isSum);
            container.setOperator("+");
        }
        if (isSub != -1) {
            container.setPosition(isSub);
            container.setOperator("-");
        }
        if (isMult != -1) {
            container.setPosition(isMult);
            container.setOperator("*");
        }
        if (isDiv != -1) {
            container.setPosition(isDiv);
            container.setOperator("/");
        }
    }

    static void operandsValueRecognize(String input, Container container) {
        StringBuilder theFirstOperand = new StringBuilder(input.
                substring(0, container.getPosition()).
                replace(" ", ""));
        StringBuilder theSecondOperand = new StringBuilder(input.
                substring(container.getPosition() + 1).
                replace(" ", ""));

        String latinPattern = "IVX";
        try {
            if (latinPattern.indexOf(theFirstOperand.charAt(0)) != -1
                    & latinPattern.indexOf(theSecondOperand.charAt(0)) != -1) {
                container.setLatin(true);
                container.setTheFirstOperand(romanToArabic(theFirstOperand));
                container.setTheSecondOperand(romanToArabic(theSecondOperand));
            } else if (latinPattern.indexOf(theFirstOperand.charAt(0)) == -1
                    & latinPattern.indexOf(theSecondOperand.charAt(0)) == -1) {

                container.setTheFirstOperand(Integer.parseInt(theFirstOperand.toString()));
                container.setTheSecondOperand(Integer.parseInt(theSecondOperand.toString()));

            } else {
                throw new IllegalArgumentException("Both operands must be either Latin or Arabic!");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
            throw new NumberFormatException("Please enter a correct mathematical expression (two operands and one operator (+, -, /, *))");
        }
    }

    static int romanToArabic(StringBuilder str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            int s1 = value(str.charAt(i));
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));
                if (s1 >= s2) {
                    res = res + s1;
                } else {
                    res = res + s2 - s1;
                    i++;
                }
            } else {
                res = res + s1;
                i++;
            }
        }
        return res;
    }

    static int value(char r) {
        int result;
        switch (r) {
            case 'I' -> result = 1;
            case 'V' -> result = 5;
            case 'X' -> result = 10;
            case 'L' -> result = 50;
            case 'C' -> result = 100;
            case 'D' -> result = 500;
            case 'M' -> result = 1000;
            default -> result = -1;
        }
        return result;
    }

    static String arabToRoman(int num) {
        String[] m = {"", "M", "MM", "MMM"};
        String[] c = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] x = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] i = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String thousands = m[num / 1000];
        String hundereds = c[(num % 1000) / 100];
        String tens = x[(num % 100) / 10];
        String ones = i[num % 10];
        return thousands + hundereds + tens + ones;
    }

    static class Container {
        String operator;
        int position;
        int theFirstOperand;
        int theSecondOperand;
        boolean isLatin;

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setTheFirstOperand(int theFirstOperand) {
            this.theFirstOperand = theFirstOperand;
        }

        public void setTheSecondOperand(int theSecondOperand) {
            if (getIsLatin()) {
                if (theSecondOperand >= theFirstOperand) {
                    if (getOperator().equals("-")) {
                        throw new IllegalArgumentException("There are no negative numbers or zero in the Latin system!");
                    }
                    if (theSecondOperand > theFirstOperand & getOperator().equals("/")) {
                        throw new IllegalArgumentException("The first operand must be greater or equal than the second!");
                    }
                }
            }
            if (theFirstOperand < 1 || theFirstOperand > 10 || theSecondOperand < 1 || theSecondOperand > 10) {
                throw new IllegalArgumentException("Operands must be in the range from 1 to 10!");
            }
            this.theSecondOperand = theSecondOperand;
        }

        public void setLatin(boolean latin) {
            isLatin = latin;
        }

        public String getOperator() {
            return operator;
        }

        public int getPosition() {
            return position;
        }

        public int getTheFirstOperand() {
            return theFirstOperand;
        }

        public int getTheSecondOperand() {
            return theSecondOperand;
        }

        public boolean getIsLatin() {
            return isLatin;
        }
    }
}