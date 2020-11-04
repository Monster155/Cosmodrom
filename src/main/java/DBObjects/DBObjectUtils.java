package DBObjects;

public class DBObjectUtils {

    public static String strFromIntArray(Integer[] a) {
        StringBuilder b = new StringBuilder();
        b.append(a[0]);
        for (int i = 1; i < a.length; i++) {
            b.append(", " + a[i]);
        }
        return b.toString();
    }

    public static String strFromIntArray(int[] a) {
        StringBuilder b = new StringBuilder();
        b.append(a[0]);
        for (int i = 1; i < a.length; i++) {
            b.append(", " + a[i]);
        }
        return b.toString();
    }

    public static String shieldText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
//                    c == '{' || c == '}' || c == '\'' || c == '\"' || c == '[' || c == ']' || c == '(' || c == ')'
            if (c == '\'') {
                sb.append("\'" + c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
