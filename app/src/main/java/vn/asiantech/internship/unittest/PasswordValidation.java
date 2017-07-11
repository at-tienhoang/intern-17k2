package vn.asiantech.internship.unittest;

import java.util.regex.Pattern;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
public class PasswordValidation {

    private PasswordValidation() {
    }

    public static boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }

    public static boolean checkPasswordSpace(String password) {
        return !password.contains(" ");
    }

    public static boolean checkPasswordHaveOneUpperCase_OneNumber_OneSpecialCharacter(String password) {
        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        boolean flag = true;
        if (!specialCharPatten.matcher(password).find()) {
            flag = false;
        }
        if (!UpperCasePatten.matcher(password).find()) {
            flag = false;
        }
        if (!digitCasePatten.matcher(password).find()) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkPasswordDifferentWithUserName(String password, String username) {
        return !password.equals(username);
    }
}
