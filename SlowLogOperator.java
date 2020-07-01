import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
public class SlowLogOperator {



    public static void readFileWithStartDate(String file,Date start_date,Date end_date,int query_limit,Boolean useLimit){
        String strLine;
        Boolean isChecked =false;
        String s;
            try{
                FileInputStream fs = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                FileWriter Bw =new FileWriter("/home/ganesh/test.txt");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                strLine =br.readLine();
                int count =0;
                int count1 =0;
                System.out.println("inside startdate method");
                System.out.println(start_date);
                System.out.println(end_date);
                while (true){
                    if(strLine.startsWith("# Time:")){
                        String[] words = strLine.split("\\s");
                        String time = words[2];
                        Date CurrentTime = formatter.parse(time);
                        count++;
                        if(((start_date.compareTo(CurrentTime)<=0))&&((end_date.compareTo(CurrentTime)>=0))){
                            count1++;
                            s="\n\n"+count1;
                            Bw.write(s);
                            strLine =br.readLine();
                            strLine =br.readLine();
                            words = strLine.split("\\s");
                            String query_time= words[2];
                            String Lock_time= words[5];
                            String rows_sent = words[7];
                            String rows_examined = words[10];
                            s ="\nTime = "+time;
                            Bw.write(s);
                            s="\nQuery Time = "+query_time+"  Lock Time = "+Lock_time+" Rows Sent = "+rows_sent+" Rows Examined = "+rows_examined;
                            Bw.write(s);
                            s="\nQuery Executed :\n";
                            Bw.write(s);
                            strLine =br.readLine();
                            isChecked =false;
                            while(true){
                                if(strLine.contains("# Time:")){
                                    isChecked =true;
                                    break;
                                }
                                else{
                                    Bw.write(strLine+"\n");

                                }
                                strLine =br.readLine();
                                if(strLine ==null){
                                    break;
                                }
                            }
                        }
                        if(useLimit){
                            if(count1>=query_limit){
                                break;
                            }
                        }

                    }
                    if(!isChecked){
                        strLine =br.readLine();
                    }
                    if(strLine ==null){
                        break;
                    }
                }
                fs.close();
                Bw.close();
            }catch (Exception e){
                System.out.println(e);
            }

    }

    public static ArrayList<SlowQueryLog> readFileWithEndDate(String file,Date end_date){
        String strLine;
        Boolean isChecked =false;
        ArrayList<SlowQueryLog> data = new ArrayList<>();

        try{
            FileInputStream fs = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            strLine =br.readLine();
            int count =0;
            int count1 =0;
            while (true){
                if(strLine.startsWith("# Time:")){
                    String[] words = strLine.split("\\s");
                    String time = words[2];
                    Date CurrentTime = formatter.parse(time);
                    count++;
                    if(((end_date.compareTo(CurrentTime)==0)||(end_date.compareTo(CurrentTime)>0))){
                        count1++;
                        strLine =br.readLine();
                        strLine =br.readLine();
                        words = strLine.split("\\s");
                        String query_time= words[2];
                        String Lock_time= words[5];
                        String rows_sent = words[7];
                        String rows_examined = words[10];
                        SlowQueryLog log = new SlowQueryLog();
                        log.setTime(time);
                        log.setQuery_time(query_time);
                        log.setLock_time(Lock_time);
                        log.setRows_sent(rows_sent);
                        log.setRows_examined(rows_examined);
                        ArrayList<String> querylist = new ArrayList<>();
                        strLine =br.readLine();
                        isChecked =false;
                        while(true){
                            if(strLine.contains("# Time:")){
                                isChecked =true;
                                break;
                            }
                            else{
                                querylist.add(strLine);

                            }
                            strLine =br.readLine();
                            if(strLine ==null){
                                break;
                            }
                        }
                        log.setQueries(querylist);
                        data.add(log);
                    }
                    if((end_date.compareTo(CurrentTime)<0)){
                        break;
                    }
                }
                if(!isChecked){
                    strLine =br.readLine();
                }
                if(strLine ==null){
                    break;
                }
            }
            fs.close();
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }

    public static void writeFile(ArrayList<SlowQueryLog> data,int query_limit){
       try{
           String s;
           SlowQueryLog lg;
           int start_index = data.size() -  query_limit;
//            FileOutputStream fo = new FileOutputStream("/home/ganesh/test.txt"); //declaring output file stream for printing in output file
           FileWriter Bw =new FileWriter("/home/ganesh/test.txt");
           ArrayList<String> query = new ArrayList<>();
           int size;
           int count =0;
           System.out.println("writeloop"+query_limit);
            for(int i=0;i<query_limit;i++){
                count++;
                System.out.println(i);
                lg=(SlowQueryLog) data.get(start_index);
                s = "\n\n"+count;
                Bw.write(s);        //writing the byte array into the file
                s ="\nTime = "+lg.getTime();
                Bw.write(s);
                s="\nQuery Time = "+lg.getQuery_time()+"  Lock Time = "+lg.getLock_time()+" Rows Sent = "+lg.getRows_sent()+" Rows Examined = "+lg.getRows_examined();
                Bw.write(s);
                s="\nQuery Executed :\n";
                Bw.write(s);
                query = lg.getQueries();
                size = query.size();
                for(int j=0;j<size;j++){
                    s=query.get(j)+"\n";
                    Bw.write(s);
                }
                query.clear();
                start_index++;
            }
           Bw.close();

       }catch (Exception e){
           System.out.println(e);
       }
    }
}
