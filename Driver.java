
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

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


        if(start_date!=null){
            try{
                SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                startDate = SDF.parse(start_date);
            }catch (Exception e){
                System.out.println(e);
            }
            if(end_date==null){
                endDate = new Date();
                useLimit = true;
                if(limit!=null){
                    queryLimit=Integer.parseInt(limit);
                }else{
                    queryLimit=10;
                }
            }else{
                try{
                    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                     endDate= SDF.parse(end_date);
//                     startDate = SDF.parse(start_date);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            SlowLogOperator.readFileWithStartDate(file_name,startDate,endDate,queryLimit,useLimit);
        }

        if(start_date==null){
            System.out.println("Startdate null loop");
            if(end_date==null){
                endDate = new Date();

            }else{
                try{
                    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    endDate= SDF.parse(end_date);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            if(limit!=null){
                queryLimit=Integer.parseInt(limit);
            }else{
                queryLimit=10;
            }
            data=SlowLogOperator.readFileWithEndDate(file_name,endDate);
            SlowLogOperator.writeFile(data,queryLimit);
        }




    }
}
//command to execute this program
//    bash slowquerylog.sh  -f /home/ganesh/Documents/slowlog.log -l 3
