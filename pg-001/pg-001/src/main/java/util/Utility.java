package util;

import java.text.DecimalFormat;

public class Utility {
    public static String format (long value){
        return new DecimalFormat("#,###,###.00").format(value);
    }
}
