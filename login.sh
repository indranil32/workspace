#!/bin/bash

echo "Logging into $1"
username=
password=
host=

stg() {
  expect ~/./ssh.exp $host $username $password
}

uat () {
  expect ~/./ssh.exp $host $username $password
}



case $1 in

stg*) stg $1
;;
uat*) uat $1
;;
*) echo "Wrong input";exit;
esac


