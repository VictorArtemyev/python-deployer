package com.vitman.python_deployer;

import android.graphics.Color;

import java.util.HashMap;

public final class ReservedWords {

    //Container of pythons reserved word
    public static final HashMap<String, Integer> RESERVED_WORDS = new HashMap<String, Integer>();
    //init reserved words
    static {
        RESERVED_WORDS.put(" and ", Color.BLUE);
        RESERVED_WORDS.put(" assert ", Color.BLUE);
        RESERVED_WORDS.put(" break ", Color.BLUE);
        RESERVED_WORDS.put(" class ", Color.BLUE);
        RESERVED_WORDS.put(" continue ", Color.BLUE);
        RESERVED_WORDS.put(" def ", Color.MAGENTA);
        RESERVED_WORDS.put(" del ", Color.BLUE);
        RESERVED_WORDS.put(" elif ", Color.BLUE);
        RESERVED_WORDS.put(" else ", Color.BLUE);
        RESERVED_WORDS.put(" except ", Color.BLUE);
        RESERVED_WORDS.put(" for ", Color.BLUE);
        RESERVED_WORDS.put(" from ", Color.BLUE);
        RESERVED_WORDS.put(" global ", Color.BLUE);
        RESERVED_WORDS.put(" if ", Color.BLUE);
        RESERVED_WORDS.put(" import ", Color.BLUE);
        RESERVED_WORDS.put(" in ", Color.BLUE);
        RESERVED_WORDS.put(" is ", Color.BLUE);
        RESERVED_WORDS.put(" lambda ", Color.BLUE);
        RESERVED_WORDS.put(" yield ", Color.BLUE);
        RESERVED_WORDS.put(" with ", Color.BLUE);
        RESERVED_WORDS.put(" while ", Color.BLUE);
        RESERVED_WORDS.put(" try ", Color.BLUE);
        RESERVED_WORDS.put(" return ", Color.BLUE);
        RESERVED_WORDS.put(" raise ", Color.BLUE);
        RESERVED_WORDS.put(" print ", Color.BLUE);
        RESERVED_WORDS.put(" pass ", Color.BLUE);
        RESERVED_WORDS.put(" or ", Color.BLUE);
        RESERVED_WORDS.put(" not ", Color.BLUE);
    }
}
