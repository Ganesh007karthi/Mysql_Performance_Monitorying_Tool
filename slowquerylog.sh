dir=/home/ganesh/IdeaProjects/MysqlSlowQueryLog/src
class_name=Driver


helpFunction()
{
   echo ""
   echo "Usage: $0 -s startdate -e enddate -f filename -l limit"
   echo -e "\t-s Enter Start date (yyyy-MM-ddTHH:mm:ss)"
   echo -e "\t-e Enter End date (yyyy-MM-ddTHH:mm:ss)"
   echo -e "\t-f Enter filename with file directory (home/user/slowlog.log)"
   echo -e "\t-l Enter limit"
   exit 1 # Exit script after printing help
}

while getopts "s:e:f:l:" opt
do
   case "$opt" in
      s ) startdate="s$OPTARG" ;;
      e ) enddate="e$OPTARG" ;;
      f ) filename="$OPTARG" ;;
      l ) limit="$OPTARG" ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

if [  -z "$filename" ] 
then
   echo "Enter the log file to be scanned!";
   helpFunction

fi

if [ ! -z "$filename" ] && [ ! -z "$startdate" ] && [ ! -z "$enddate" ] && [ ! -z "$limit" ]
then
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $startdate $enddate $limit
elif [ ! -z "$filename" ] && [ ! -z "$startdate" ] && [ -z "$enddate" ] && [ -z "$limit" ]
then
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $startdate 
elif [ ! -z "$filename" ] && [ ! -z "$startdate" ] && [ -z "$enddate" ] && [ ! -z "$limit" ]
then
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $startdate $limit
elif [ ! -z "$filename" ] && [ -z "$startdate" ] && [ ! -z "$enddate" ] && [ -z "$limit" ]
then 
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $enddate 
elif [ ! -z "$filename" ] && [ -z "$startdate" ] && [ ! -z "$enddate" ] && [ ! -z "$limit" ]
then
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $enddate $limit

elif [ ! -z "$filename" ] && [ -z "$startdate" ] && [ -z "$enddate" ] && [ -z "$limit" ]
then 
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename
elif [ ! -z "$filename" ] && [ -z "$startdate" ] && [ -z "$enddate" ] && [ ! -z "$limit" ]
then 
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $limit
elif [ ! -z "$filename" ] && [ ! -z "$startdate" ] && [ ! -z "$enddate" ] 
then
  javac -cp .:$dir $dir/$class_name.java  && java -cp .:$dir $class_name $filename $startdate $enddate 
fi
  

