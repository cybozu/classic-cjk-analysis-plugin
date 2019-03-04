# Classic CJK Analysis Plugin
Solr 4.6 で非推奨になったCJK Tokenizerと互換のあるプラグインです

## How to build

```bash
# Replace New Elasticsearch version
bash ./etc/replace-version.sh 6.6.0
./gradlew clean zipPlugin

# Plugin
ls build/dist/classic-cjk-analysis-plugin-0.0.1-es-6.6.0.zip 
```

