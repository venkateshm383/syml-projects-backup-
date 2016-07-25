package com.syml.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.syml.service.FilogixCallVO;

public class CalendarMonthTest {

    private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private static void println(Object message) {
        System.out.println(message.toString());
    }


    @Test
    public void test1() throws ParseException {
        final Date date1 = DATEFORMAT.parse("21-11-2004");
        final Date date2 = DATEFORMAT.parse("21-11-2005");
        println(date1 + " - " + date2);
        final int result = FilogixCallVO.createApplicantMonthsAtAddress(date2, date1);
        println(result);
    }


    @Test
    public void testCompare() throws ParseException {
        final Date date1 = DATEFORMAT.parse("21-11-2004");
        final Date date2 = DATEFORMAT.parse("21-11-2005");
        System.out.println(date2.compareTo(date1));
    }


    @Test
    public void arrayListTest() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            String s = list.get(i);
            String next = (i == (listSize - 1)) ? "NEW" : list.get(i + 1);
            println("i:" + i +", dataSize:" + listSize);
            println("Current:" + s + ", Next:" + next);
            if (i == listSize) println("EQUAL");
        }
    }
}
