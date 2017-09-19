(ns lambdaisland.uri.platform-test
  (:require [lambdaisland.uri.platform :as p]
            [clojure.test :refer [deftest testing is are]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test #?(:clj :refer
                                                :cljs :refer-macros) [defspec]]
            [clojure.test.check.properties :refer [for-all]]))

(defspec string->utf8-byte-seq-roundtrip-test
  (for-all [s gen/string]
    (= s
       (p/utf8-byte-seq->string (p/string->utf8-byte-seq s)))))

(defspec byte->hex-round-trip-test
  (for-all [b (gen/choose 0 255)]
    (= b
       (p/hex->byte (p/byte->hex b)))))
