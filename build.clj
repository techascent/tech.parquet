(ns build
  (:require [clojure.tools.build.api :as b])
  (:refer-clojure :exclude [compile]))

(def lib 'com.techascent/tmd-parquet)
(def version "1.001")
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s.jar" (name lib)))

(defn clean [_]
  (b/delete {:path "pom.xml"})
  (b/delete {:path "target"}))

(defn jar [_]
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]
                :scm {:url "https://github.com/techascent/tech.parquet"}
                :pom-data [[:licenses
                            [:license
                             [:name "MIT License"]
                             [:url "https://github.com/cnuernber/charred/blob/master/LICENSE"]]]]})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))
