#!/bin/sh -e
NEW_VERSION=$1

if [ "${NEW_VERSION}" = "" ];
then
   echo "USAGE: ${0} <version>"
fi

sed -E -i "s/^elasticsearch.version=.*$/elasticsearch.version=${NEW_VERSION}/g"  src/main/dist/plugin-descriptor.properties
sed -E -i "s/^def\s+ElasticsearchVersion\s+=.*/def ElasticsearchVersion = '${NEW_VERSION}'/g" build.gradle


