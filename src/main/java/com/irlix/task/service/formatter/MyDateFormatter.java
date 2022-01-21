package com.irlix.task.service.formatter;

import java.text.SimpleDateFormat;

public class MyDateFormatter {

    public static final SimpleDateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    public static final SimpleDateFormat formatterWithoutTime = new SimpleDateFormat("yyyy-MM-dd");

}
