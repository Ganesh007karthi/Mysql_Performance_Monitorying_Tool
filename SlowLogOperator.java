import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
public class SlowLogOperator {
    static String strLine;
    static Boolean isChecked =false;
    static TreeMap<Integer, SlowQueryLog> data = new TreeMap<>();
    static TreeMap<Integer,SlowQueryLog> new_data = new TreeMap<Integer,SlowQueryLog>();
    static int query_limit;

    public static void readFileWithStartDate(String file,String StartTime,String limit){
        String s;
        if(limit!=null) {
            query_limit = Integer.parseInt(limit);
        }else if(limit==null){
            query_limit = 10;
        }
            try{
                FileInputStream fs = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                FileWriter Bw =new FileWriter("/home/ganesh/test.txt");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date start_date = SDF.parse(StartTime);
                strLine =br.readLine();
                int count =0;
                int count1 =0;
                while (true){
                    if(strLine.startsWith("# Time:")){
                        String[] words = strLine.split("\\s");
                        String time = words[2];
                        Date CurrentTime = formatter.parse(time);
                        count++;
                        if(((start_date.compareTo(CurrentTime)<=0))&&count1<query_limit){
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
                        if(count1>=query_limit){
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
                Bw.close();
                System.out.println("Total query count: "+count);
                System.out.println("if loop iteration inside startdatemethod :"+count1);
                System.out.println("data map size: "+data.size());
            }catch (Exception e){
                System.out.println(e);
            }

    }

    public static TreeMap<Integer,SlowQueryLog> readFileWithEndDate(String file,String EndTime){


        try{
            FileInputStream fs = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date end_date = SDF.parse(EndTime);
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
                        data.put(count1,log);
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
            System.out.println("Total query count: "+count);
            System.out.println("if loop iteration:"+count1);
            System.out.println("data map size: "+data.size());
            return data;
        }catch (Exception e){
            System.out.println(e);
        }

        return data;
    }

    public static TreeMap<Integer,SlowQueryLog> readFileWithLimit(String file){
        try{
            FileInputStream fs = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            strLine =br.readLine();
            int count1 =0;
            while (true){
                if(strLine.startsWith("# Time:")){
                    String[] words = strLine.split("\\s");
                    String time = words[2];
                    Date CurrentTime = formatter.parse(time);


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
                        data.put(count1,log);

                }
                if(!isChecked){
                    strLine =br.readLine();
                }
                if(strLine ==null){
                    break;
                }
            }
            fs.close();
            System.out.println("if loop iteration:"+count1);
            System.out.println("data map size: "+data.size());
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }

    public static TreeMap<Integer, SlowQueryLog> readFile(String Filename,String StartTime,String EndTime){
        try{
            FileInputStream fs = new FileInputStream(Filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date start_date = SDF.parse(StartTime);
            Date end_date = SDF.parse(EndTime);
            strLine =br.readLine();
            int count =0;
            int count1 =0;
            while (true){
                if(strLine.startsWith("# Time:")){
                    String[] words = strLine.split("\\s");
                    String time = words[2];
                    Date CurrentTime = formatter.parse(time);
                    count++;
                    if(((start_date.compareTo(CurrentTime)==0)||(start_date.compareTo(CurrentTime)<0))&&((end_date.compareTo(CurrentTime)==0)||(end_date.compareTo(CurrentTime)>0))){
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
                        data.put(count1,log);
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

            System.out.println("Total query count: "+count);
            System.out.println("if loop iteration:"+count1);
            System.out.println("data map size: "+data.size());
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }

    public static void writeFile(TreeMap data){
        try{
            int count=0;
            String s;
            int size;
            SlowQueryLog lg;
//            FileOutputStream fo = new FileOutputStream("/home/ganesh/test.txt"); //declaring output file stream for printing in output file
            FileWriter Bw =new FileWriter("/home/ganesh/test.txt");
            Iterator it = data.entrySet().iterator();
            ArrayList<String> query = new ArrayList<>();
            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                count++;
                lg = (SlowQueryLog) pair.getValue();
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

                for(int i=0;i<size;i++){
                    s=query.get(i)+"\n";
                    Bw.write(s);
                }
                query.clear();
            }
            Bw.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void writeFile(TreeMap data,String limit){
        if(limit!=null) {
            query_limit = Integer.parseInt(limit);
        }else if(limit==null){
            query_limit = 10;
        }
       try{
           String s;
           SlowQueryLog lg;
           int start_index = data.size() -  query_limit + 1;
//            FileOutputStream fo = new FileOutputStream("/home/ganesh/test.txt"); //declaring output file stream for printing in output file
           FileWriter Bw =new FileWriter("/home/ganesh/test.txt");
           ArrayList<String> query = new ArrayList<>();
           int size;
           int count =0;
            for(int i=0;i<query_limit;i++){
                count++;
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
