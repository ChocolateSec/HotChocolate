#!/bin/sh

CWD=$(pwd)

mkdir -p /tmp/chocolatesec/buildtools
cd /tmp/chocolatesec/buildtools

curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -jar BuildTools.jar

cd $CWD
rm -rf /tmp/chocolatesec/buildtools