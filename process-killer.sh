#!/bin/bash
x=`ps -eo pid,comm |grep chrome$ | awk '{print $1}'`
echo 'All Chrome processes right now ('`date`')- '$x

# global kill switch
killswitch='Y'

function checkActiveChildRecc() {
	child=`pgrep -P $1`
        echo 'All child for '$1' right now('`date`') - '$child
        if [ -n "$child" ]; #has child processes
        then
        	for j in $child; #check if any process is a current process
                do
                	echo 'Reccursing for process '$j
			checkActiveChildRecc $j
			y1=`stat -c %Y /proc/$j`;
                        if [ $? -eq 0 ];
                        then
                        	currTime1=`date +%s`;
                                timeDiff1=$(expr $currTime1 - $y1);
                                if [ "$timeDiff1" -le 600 ]; #chile was spawned in less than 10 minutes(600)
                                then
                                	echo 'Child process '$j' was started in less then 10 minutes, hence wont kill parent '$i
                                        kill='N'
                                fi;
                        fi;
               done;
        else
		echo "No Child process for "$1

	fi;
}



for i in $x;
do
      y=`stat -c %Y /proc/$i`;
      if [ $? -eq 0 ];
              then
                   currTime=`date +%s`;
                   timeDiff=$(expr $currTime - $y);
                   if [ "$timeDiff" -ge 7200 ]; # more than 2 hours (7200)
                    then
                           #kill -9 $i;
			   kill='Y'
			   checkActiveChildRecc $i
			   child=`pgrep -P $i`
#			   echo 'All child for '$i' right now('`date`') - '$child
#			   if [ -n "$child" ]; #has child processes
#			   then
#				for j in $child; #check if any process is a current process
#				do
#					 y1=`stat -c %Y /proc/$j`;
#     					 if [ $? -eq 0 ];
#            				 then
#                				currTime1=`date +%s`;
#                   				timeDiff1=$(expr $currTime1 - $y1);
#                   				if [ "$timeDiff1" -le 5 ]; #chile was spawned in less than 10 minutes(600)
#                    				then
#							echo 'Child process '$j' was started in less then 10 minutes, hence wont kill parent '$i
#							kill='N'
#						fi;
#					fi;
#				done;
#			   fi;
			   if [ "$kill" == 'Y' ] || [ $killswitch == 'Y'  ];
			   then
				echo 'Killing - '$i
				kill -9 $i
			   fi;
                    else
			echo 'Process ' $i' is not older than 2 hours'	
		    fi;
       fi;
done

