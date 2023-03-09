
set -ex
VERSION=$(grep "ElasticsearchVersion = " build.gradle  | awk '{print $4}' | sed -e "s/'//g")

test -n "${VERSION}"

test $(grep "${VERSION}" src/main/dist/plugin-descriptor.properties | wc -l) -ne 0
