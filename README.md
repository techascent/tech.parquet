# Easy Parquet Bindings

A single jar that enables read/write of csv and parquet.

## Use

Depend on these maven coordinates:

    [com.techascent/tmd-parquet "1.000-beta-39"]

Then, for small datasets:

```clj
(require '[tech.v3.dataset :as ds])
(require '[tech.v3.libs.parquet :as parquet])
(-> (ds/->>dataset {:x (concat (repeat 3 "a") (repeat 3 "b"))
                    :y (range 6)
                    :z (repeatedly 6 rand)})
                   (parquet/ds->parquet "little.parquet"))
```

And for bigger datasets (streaming a batch at a time):

```clj
(require '[tech.v3.dataset :as ds])
(require '[tech.v3.dataset.io.csv :as ds-csv])
(require '[tech.v3.libs.parquet :as parquet])
(require '[tech.v3.io :as io])
(->> (io/gzip-input-stream "https://github.com/techascent/tech.ml.dataset/raw/master/test/data/ames-train.csv.gz")
     (ds-csv/csv->dataset-seq)
     (parquet/ds-seq->parquet "result.parquet"))
```

## A Note About Possible Performance Issues

If Parquet read/write performance is degraded by profoundly verbose debug-level logging, be sure to disable that.

An example `logback.xml` might look something like:

```
<configuration debug="false">
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <!-- encoders are assigned the type
         ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>

  <root level="info">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```
