import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
public class SlowLogOperator {



    public static void readAndWriteFile(String file, Date start_date, Date end_date,int query_limit,boolean useLimit){
        String strLine;
        Boolean isChecked =false;
        System.out.println(useLimit);
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
                        if(end_date.compareTo(CurrentTime)<0){
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
            }catch (Exception e){
                System.out.println(e);
            }

    }

}
