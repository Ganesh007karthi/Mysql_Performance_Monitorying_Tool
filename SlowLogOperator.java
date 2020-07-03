import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SlowLogOperator {
    public static void operate(String file, Date start_date, Date end_date, String limit){
        String strLine;
        Boolean isChecked =false;
        int query_limit=0;
        if (limit != null) {
            query_limit = Integer.parseInt(limit);
        }
        String s;
            try{
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                FileWriter fileWriter =new FileWriter("/home/ganesh/test.txt");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                strLine =bufferedReader.readLine();
                int count1 =0;
                System.out.println("inside startdate method");
                System.out.println(start_date);
                System.out.println(end_date);
                while (true){
                    if(strLine.startsWith("# Time:")){
                        String[] words = strLine.split("\\s");
                        String time = words[2];
                        Date CurrentTime = formatter.parse(time);
                        if(((start_date.compareTo(CurrentTime)<=0))&&((end_date.compareTo(CurrentTime)>=0))){
                            count1++;
                            s="\n\n"+count1;
                            fileWriter.write(s);
                            strLine =bufferedReader.readLine();
                            strLine =bufferedReader.readLine();
                            words = strLine.split("\\s");
                            String query_time= words[2];
                            String Lock_time= words[5];
                            String rows_sent = words[7];
                            String rows_examined = words[10];
                            s ="\nTime = "+time;
                            fileWriter.write(s);
                            s="\nQuery Time = "+query_time+"  Lock Time = "+Lock_time+" Rows Sent = "+rows_sent+" Rows Examined = "+rows_examined;
                            fileWriter.write(s);
                            s="\nQuery Executed :\n";
                            fileWriter.write(s);
                            strLine =bufferedReader.readLine();
                            isChecked =false;
                            while(true){
                                if(strLine.contains("# Time:")){
                                    isChecked =true;
                                    break;
                                }
                                else{
                                    fileWriter.write(strLine+"\n");

                                }
                                strLine =bufferedReader.readLine();
                                if(strLine ==null){
                                    break;
                                }
                            }
                        }
                        if(limit!=null){
                            if(count1>=query_limit){
                                break;
                            }
                        }
                        if(end_date.compareTo(CurrentTime)<0){
                            break;
                        }
                    }
                    if(!isChecked){
                        strLine =bufferedReader.readLine();
                    }
                    if(strLine ==null){
                        break;
                    }
                }
                fileInputStream.close();
                fileWriter.close();
            }catch (Exception e){
                System.out.println(e);
            }
    }
}
