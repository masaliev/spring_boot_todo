
package com.github.masaliev.passport;

public class Utils {
    public static String camelCaseToSnakeCase(String camelCaseString){
        return camelCaseString == null ? camelCaseString : camelCaseString.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }
}
