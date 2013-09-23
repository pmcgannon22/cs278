#! /bin/bash

mkdir ./JavaTest
mkdir ./JavaTest2

nohup java -jar dropbox.jar ./JavaTest > ./cs278_intTest.log &
sleep 10

ip_=$(ifconfig | grep 'inet addr:' | grep -v '127.0.0.1' | cut -d: -f2 | awk '{ print $1}')

nohup java -jar dropbox.jar ./JavaTest2 $ip_ > ./cs278_intTest2.log &
sleep 15

echo happydays > ./JavaTest/happydays.txt
sleep 2
echo "checking if exists in JavaTest2..."

if [ -e ./JavaTest2/happydays.txt ] 
  then
    echo "file happydays.txt exists in JavaTest2!"
fi

pkill java

rm -rf ./JavaTest
rm -rf ./JavaTest2
