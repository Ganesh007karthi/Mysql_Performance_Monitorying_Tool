
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Driver {



    public static void main(String args[]){
        ArrayList<SlowQueryLog> data = new ArrayList<>();
        String start_date = null, end_date=null, file_name=null,limit=null;
        Boolean useLimit=false;
        int queryLimit=0;
        Date endDate = null;
        Date startDate=null;
        file_name = args[0];
        for(int i=0;i<args.length;i++){
            if(i==0){
                continue;
            }
            if(args[i].contains("s")){
                start_date = args[i].replace("s","").trim();

            }else if(args[i].contains("e")){
                end_date = args[i].replace("e","").trim();
            }else{
                limit = args[i];
            }
        }
        System.out.println("start date :"+start_date);
        System.out.println("end date :"+end_date);
        System.out.println("filename :"+file_name);
        System.out.println("limit:"+limit);
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try{
            if(start_date!=null&&end_date==null){
                startDate = SDF.parse(start_date);
                endDate = DateAction(startDate,1,true);
            }else{
                if(end_date==null){
                    endDate = new Date();

                }else{
                    endDate= SDF.parse(end_date);
                }
                if(start_date==null){
                    startDate = DateAction(endDate,1,false);
                }else if(start_date!=null){
                    startDate = SDF.parse(start_date);
                }
            }

            if(limit!=null){
                useLimit = true;
                queryLimit=Integer.parseInt(limit);
            }else{
                useLimit=false;
            }
            SlowLogOperator.readAndWriteFile(file_name,startDate,endDate,queryLimit,useLimit);

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static Date DateAction(Date date, int days,Boolean add) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        if(add){
            cal.add(Calendar.DATE, days);
        }else{
            cal.add(Calendar.DATE, -days);
        }

        return cal.getTime();
    }

}
//command to execute this program
//    bash slowquerylog.sh  -f /home/ganesh/Documents/slowlog.log -l 3
