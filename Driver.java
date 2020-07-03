
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Driver {
    public static void main(String args[]) {

        String start_date = null, end_date = null, file_name = null, limit = null;
        Date endDate = null;
        Date startDate = null;
        file_name = args[0];
        for (int i = 1; i < args.length; i++) {

            if (args[i].contains("s")) {
                start_date = args[i].replace("s", "").trim();

            } else if (args[i].contains("e")) {
                end_date = args[i].replace("e", "").trim();
            } else {
                limit = args[i];
            }
        }
        System.out.println("start date :" + start_date);
        System.out.println("end date :" + end_date);
        System.out.println("filename :" + file_name);
        System.out.println("limit:" + limit);
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            if (start_date != null && end_date == null) {
                startDate = SDF.parse(start_date);
                endDate = new Date(startDate.getTime());
                endDate = DateAction(endDate, true);
            } else {
                if (end_date == null) {
                    endDate = new Date();

                } else {
                    endDate = SDF.parse(end_date);
                }
                if (start_date == null) {
                    startDate=new Date(endDate.getTime());
                    startDate = DateAction(startDate, false);
                } else if (start_date != null) {
                    startDate = SDF.parse(start_date);
                }
            }

            System.out.println(startDate);
            System.out.println(endDate);
            SlowLogOperator.operator(file_name, startDate, endDate, limit);
//            Thread.sleep(30000);
//System.out.println(endDate);
        } catch (Exception e) {
            System.out.println(e);

        }


    }

    public static Date DateAction(Date date, Boolean add) {

        long millis;
        if(add) {
            millis = date.getTime() + 86400000;
        }else{
            millis = date.getTime() - 86400000;
        }
        date.setTime(millis);

        return date;
    }
}
//command to execute this program
//    bash slowquerylog.sh  -f /home/ganesh/Documents/slowlog.log -l 3
