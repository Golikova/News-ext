package chrome_extencion.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class QryTools {

    public static String removeSymbols(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpaceChar(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String replaceSpaces(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isSpaceChar(c)) {
                result.append('+');
            }
            else result.append(c);
        }
        return result.toString();
    }

    public static String replaceSpacesPercent(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isSpaceChar(c)) {
                result.append("%20");
            }
            else result.append(c);
        }

        return result.toString();
    }

    public static String replaceSpacesUnderscore(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isSpaceChar(c)) {
                result.append("_");
            }
            else result.append(c);
        }
        return result.toString();
    }

    public static String convertInputToJSON(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        StringBuilder sb = new StringBuilder();

        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }


}
