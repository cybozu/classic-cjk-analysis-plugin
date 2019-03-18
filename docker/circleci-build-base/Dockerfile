
FROM docker-registry.dev.cybozu.xyz/ubuntu:16.04
# プライベートレジストリのイメージ更新手順
# docker build -t docker-registry.dev.cybozu.xyz/classic-cjk-tokenizer/circleci-build-base docker/circleci-build-base
# docker push docker-registry.dev.cybozu.xyz/classic-cjk-tokenizer/circleci-build-base

# install packages
RUN apt-get update && apt-get install --no-install-recommends -y \
        language-pack-ja \
        git-core \
        openjdk-8-jdk \
        openjdk-8-jre-headless

# 日本語環境
RUN update-locale LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP:ja
ENV LC_ALL ja_JP.UTF-8
RUN ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

RUN mkdir "/root/.m2"
ADD settings.xml "/root/.m2/settings.xml"
