#1/bin/bash

app=$2
srcid=$3
output="~/Documents/apps/"
dir=/local/sites/logs/
bkupdir=/Logbackup/

echo "Downloading from $1"

prod () {
   host="beta1.host.com"
   username="indranilm"
   password="abcd1234"
   expect ~/./scp.exp $host $username $password $dir/$srcid_$app
   if [ $? != 0 ] then
   	expect ~/./scp.exp $host $username $password $dir/failed_$srcid_$app
	if [ $? != 0 ] then
		expect ~/./scp.exp $host $username $password $bkupdir/$srcid_$app
		if [ $? != 0 ] then
			expect ~/./scp.exp $host $username $password $bkupdir/failed_$srcid_$app
		fi
	fi
   fi
}


stg() {
  host="$1.host.com"
  username="dev_user"
  password="dev@123"
  expect ~/./scp.exp $host $username $password $dir/$srcid_$app
   if [ $? != 0 ] then
        expect ~/./scp.exp $host $username $password $dir/failed_$srcid_$app
        if [ $? != 0 ] then
                expect ~/./scp.exp $host $username $password $bkupdir/$srcid_$app
                if [ $? != 0 ] then
                        expect ~/./scp.exp $host $username $password $bkupdir/failed_$srcid_$app
                fi
        fi
   fi
}

uat () {
 echo "Not yet implemented!!"
 exit
}

case $1 in

prod) prod
;;
stg*) stg $1
;;
uat*) uat $1
;;
*) echo "Wrong input";exit;
esac


