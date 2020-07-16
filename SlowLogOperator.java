import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SlowLogOperator {
    public static void operate(String file, Date start_date, Date end_date, String limit)throws Exception {
        String strLine,queryDetails,schemaName=null;
        boolean isChecked =false;
        int query_limit=0;
        if (limit != null) {
            query_limit = Integer.parseInt(limit);
        }
        String s;
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        FileWriter fileWriter =new FileWriter("/home/temp/test.txt");
            try{

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                strLine =bufferedReader.readLine();
                int count1 =0;
                System.out.println("inside start date method");
                System.out.println(start_date);
                System.out.println(end_date);
                while (true){
                    if(strLine.startsWith("# User@Host:")){
                        queryDetails=bufferedReader.readLine();
                        strLine=bufferedReader.readLine();
                        if(strLine.startsWith("use")){
                            schemaName=strLine;
                            strLine=bufferedReader.readLine();
                        }
                        int startIndex = strLine.indexOf("=");
                        int lastIndex = strLine.indexOf(";");
                        String queryTime = strLine.substring(startIndex+1,lastIndex);
                        long queryTimeStamp = Long.parseLong(queryTime);
                        long startTimeStamp = start_date.getTime()/1000;
                        long endTimeStamp = end_date.getTime()/1000;
                        if((startTimeStamp<=queryTimeStamp)&&((endTimeStamp>=queryTimeStamp))){
                            count1++;
                            s="\n\nQuery Count: "+count1;
                            fileWriter.write(s);
                            Date date1 = new Date(queryTimeStamp*1000);
                            s="\nQuery Execution Time: "+date1;
                            fileWriter.write(s);
                            strLine =bufferedReader.readLine();
                            String[] words = queryDetails.split("\\s");
                            String query_time= words[2];
                            String Lock_time= words[5];
                            String rows_sent = words[7];
                            String rows_examined = words[10];
                            s="\nQuery Details: \nQuery Time = "+query_time+"  Lock Time = "+Lock_time+" Rows Sent = "+rows_sent+" Rows Examined = "+rows_examined;
                            fileWriter.write(s);
                            if(schemaName!=null){
                                fileWriter.write("\nSchema Name :"+schemaName);
                                schemaName=null;
                            }
                            s="\nQuery Executed :\n";
                            fileWriter.write(s);
                            isChecked =false;
                            while(true){
                                if(strLine.contains("# User@Host:")){
                                    isChecked =true;
                                    break;
                                }else if(strLine.contains("# Time:")){

                                    strLine =bufferedReader.readLine();
                                    continue;
                                }
                                else if(!strLine.contains("# Time:")||!strLine.contains("# User@Host:")){
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
                        if(endTimeStamp<queryTimeStamp){
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

            }catch (Exception exception){
                System.out.println(exception);
               throw exception;
            }finally{
                fileInputStream.close();
                fileWriter.close();
            }
    }
}
