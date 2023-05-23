# Simple Parquet Bindings


Single dependency read/write of csv and parquet.


## Efficiency

This library includes bindings to logback classic which [disable debug logging](resources/logback.xml).  This is crucial
to get any [performance whatsoever out of parquet](https://techascent.github.io/tech.ml.dataset/tech.v3.libs.parquet.html) as
by default it includes extremely verbose debug write logging.


## Usage
```clojure
 ;; setup either log4j2 or logback-classic to get rid of logger warnings
user> (require '[tech.v3.dataset :as ds])
nil
user> (require '[tech.v3.dataset.io.csv :as ds-csv])
nil
user> (require '[tech.v3.libs.parquet :as parquet])
nil
user> (require '[tech.v3.io :as io])
nil
user> (->> (io/gzip-input-stream "https://github.com/techascent/tech.ml.dataset/raw/master/test/data/ames-train.csv.gz")
           (ds-csv/csv->dataset-seq)
           (parquet/ds-seq->parquet "result.parquet"))
:ok
```
