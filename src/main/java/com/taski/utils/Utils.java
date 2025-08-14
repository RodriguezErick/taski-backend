package com.taski.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static Long getUserID(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
