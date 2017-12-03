package com.cc.functions;

import com.google.common.base.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by madiredd on 12/3/17.
 */
public class EmailValidatorFn implements Function<String, Boolean> {

    final String regex = "[a-z0-9\\.\\-+_]+@[a-z0-9\\.\\-+_]+\\.[a-z]+";
    @Override
    public Boolean apply(String input) {
        Matcher matcher;
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
