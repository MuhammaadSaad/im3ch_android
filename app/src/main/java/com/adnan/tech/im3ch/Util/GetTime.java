package com.adnan.tech.im3ch.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTime {
    public String GetTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
