
import java.text.SimpleDateFormat;
import java.util.Date;


public class Driver {
    public static void main(String[] args) {

        String start_date = null, end_date = null, file_name, limit = null;
        Date endDate ;
        Date startDate ;

        file_name = args[0];
        for (int args_index = 1; args_index < args.length; args_index++) {

            if (args[args_index].contains("s")) {
                start_date = args[args_index].replace("s", "").trim();

            } else if (args[args_index].contains("e")) {
                end_date = args[args_index].replace("e", "").trim();
            } else {
                limit = args[args_index];
            }
        }
        System.out.println("start date :" + start_date);
        System.out.println("end date :" + end_date);
        System.out.println("filename :" + file_name);
        System.out.println("limit:" + limit);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            if (start_date != null && end_date == null) {
                startDate = dateFormat.parse(start_date);
                endDate = new Date(startDate.getTime());
                dateAction(endDate, true);
            } else {
                if (end_date == null) {
                    endDate = new Date();

                } else {
                    endDate = dateFormat.parse(end_date);
                }
                if (start_date == null) {
                    startDate = new Date(endDate.getTime());
                    dateAction(startDate, false);
                } else {
                    startDate = dateFormat.parse(start_date);
                }
            }

            System.out.println(startDate);
            System.out.println(endDate);
            SlowLogOperator.operate(file_name, startDate, endDate, limit);
//            Thread.sleep(30000);
//System.out.println(endDate);
        } catch (Exception e) {
            System.out.println(e);

        }


    }

    public static void dateAction(Date date, Boolean add) {

        long millis;
        if (add) {
            millis = date.getTime() + 86400000;
        } else {
            millis = date.getTime() - 86400000;
        }
        date.setTime(millis);

    }
}

