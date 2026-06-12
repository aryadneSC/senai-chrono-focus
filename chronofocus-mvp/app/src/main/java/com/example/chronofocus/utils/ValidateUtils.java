package com.example.chronofocus.utils;

import java.util.List;

public class ValidateUtils {
    public static boolean isValidOperation(List<?> list, int index, Object obj) {
        return index < list.size() && index >= 0 && obj != null;
    }
}
