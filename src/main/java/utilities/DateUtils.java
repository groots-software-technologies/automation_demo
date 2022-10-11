package utilities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class DateUtils {
    // Get The Current Day plus days. You can change this method based on your
    // needs.

    public static String getDate(int days, String format) throws ParseException {
        String dt = null;
        SimpleDateFormat format1;
        SimpleDateFormat format2;
        Date date1;
        String strDate;

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DATE, days);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/d/yyyy");
        strDate = formatter.format(calendar.getTime());
        //System.out.println(strDate);

        switch(format)
        {
            case"dd-MMM-yyyy":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("dd-MMM-yyyy");
                date1 = format1.parse(strDate);
                dt = format2.format(date1);
                break;

            case "MM/d/yyyy":
                dt = strDate;
                break;
            case "MM/dd/yyyy":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("MM/dd/yyyy");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;
            case "M/d/yyyy":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("M/d/yyyy");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;
            case "dd/MM/yyyy":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("dd/MM/yyyy");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;

            case "d MMM yyyy":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("d MMM yyyy");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;

            case "dayName":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("EEEE");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;
            case "monthName":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("MMMM");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;

            case "day":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("d");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;

            case "month":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("m");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;

            case "year":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("yyyy");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;

            case "EEEE, dd/MM/yyyy":
                format1 = new SimpleDateFormat("MM/d/yyyy");
                format2 = new SimpleDateFormat("EEEE, dd/MM/yyyy");
                date1 = format1.parse(strDate);
                //System.out.println(format2.format(date1));
                dt = format2.format(date1);
                break;
            default:
                break;
        }
        return dt;
    }


    //This function will check the date is in current month, previous or next month
    public static int checkDate(int days) throws ParseException {
        Date date1, date2;
        String newDate, currDate;

        Calendar calendar;
        SimpleDateFormat formatter = null;
        calendar= Calendar.getInstance(TimeZone.getDefault());

        calendar.add(Calendar.DATE, 0);
        formatter = new SimpleDateFormat("MMMM yyyy");
        currDate = formatter.format(calendar.getTime());

        calendar.add(Calendar.DATE, days);
        formatter = new SimpleDateFormat("MMMM yyyy");
        newDate = formatter.format(calendar.getTime());

        date1 = formatter.parse(newDate);
        date2 = formatter.parse(currDate);

        int result = date1.compareTo(date2);
        System.out.println("Result: " + result);
        return date1.compareTo(date2);
    }
}
