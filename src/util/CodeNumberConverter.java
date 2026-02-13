package util;

public class CodeNumberConverter {
    private CodeNumberConverter(){} // 인스턴스 생성 방지

    /**
     *
     * @param colCode 가로 코드
     * @return 코드를 숫자로 변환함 (A -> 1, B -> 2 등)
     */
    public static int convertColCodeToNumber(String colCode) {
        int result = 0;

        for (int i = 0; i < colCode.length(); i++) {
            result = result * 26 + (colCode.charAt(i) - 'A' + 1);
        }

        return result;
    }
    /**
     *
     * @param number 가로 숫자
     * @return 숫자를 코드로 변환함 (1 -> A, 2 -> B 등)
     */
    public static String convertNumberToColCode(int number) {
        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            number--;
            char ch = (char) ('A' + (number % 26));
            sb.insert(0, ch);
            number /= 26;
        }

        return sb.toString();
    }
}
