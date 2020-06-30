
import java.util.TreeMap;

public class Driver {
    static TreeMap<Integer, SlowQueryLog> data = new TreeMap<>();
    static String start_date, end_date, file_name,limit;
    public static void main(String args[]){
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

        if(file_name !=null&& start_date !=null&& end_date !=null&&limit==null){ //start date and end date are given
            //file
            //start date
            //end date
            System.out.println("operation with filename start date and end date");
            data = SlowLogOperator.readFile(file_name, start_date,end_date);
            SlowLogOperator.writeFile(data);
        }else if(file_name !=null&& start_date !=null&& end_date ==null&&limit==null){ //start date alone without limit and end date

            //file
            //start date
            // default limit
            System.out.println("Operation with file name and Start date and default limit");
            SlowLogOperator.readFileWithStartDate(file_name,start_date,limit);

        }else if(file_name !=null&& start_date !=null&& end_date ==null&&limit!=null){ //start date with limit and without end date

            //file
            //start date
            //limit
            System.out.println("Operation with filename and start date and limit");
            SlowLogOperator.readFileWithStartDate(file_name,start_date,limit);
        }else if(file_name !=null&& start_date ==null&& end_date !=null&&limit!=null){
            //file
            //end date
            // limit
            System.out.println("Operation with file name and end date and  limit");
            data = SlowLogOperator.readFileWithEndDate(file_name,end_date);
            SlowLogOperator.writeFile(data,limit);
        }else if(file_name !=null&& start_date ==null&& end_date !=null&&limit==null){
            //file
            //end date         default limit
            System.out.println("Operation with file name and end date and default limit");
            data = SlowLogOperator.readFileWithEndDate(file_name,end_date);
            SlowLogOperator.writeFile(data,limit);
        }else if(file_name !=null&& start_date ==null&& end_date ==null&&limit!=null){
            //file
            //limit        user defined
            System.out.println("Operation with file name and limit");
            data = SlowLogOperator.readFileWithLimit(file_name);
            SlowLogOperator.writeFile(data,limit);
        }else if(file_name !=null&& start_date ==null&& end_date ==null&&limit==null){
            //file            default limit
            System.out.println("Operation with file name and default limit");
            data = SlowLogOperator.readFileWithLimit(file_name);
            SlowLogOperator.writeFile(data,limit);
        }

    }
}
