import java.util.ArrayList;

public class SlowQueryLog {
    String time,query_time,lock_time,rows_sent,rows_examined;
    ArrayList<String> queries = new ArrayList<>();



    public ArrayList<String> getQueries() {
        return queries;
    }

    public void setQueries(ArrayList<String> queries) {
        this.queries = queries;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuery_time() {
        return query_time;
    }

    public void setQuery_time(String query_time) {
        this.query_time = query_time;
    }

    public String getLock_time() {
        return lock_time;
    }

    public void setLock_time(String lock_time) {
        this.lock_time = lock_time;
    }

    public String getRows_sent() {
        return rows_sent;
    }

    public void setRows_sent(String rows_sent) {
        this.rows_sent = rows_sent;
    }

    public String getRows_examined() {
        return rows_examined;
    }

    public void setRows_examined(String rows_examined) {
        this.rows_examined = rows_examined;
    }


}
