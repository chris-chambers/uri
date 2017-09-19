(ns lambdaisland.uri.normalize-test
  (:require [lambdaisland.uri :as uri]
            [lambdaisland.uri.normalize :as n]
            [clojure.test :refer [deftest testing is are]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test #?(:clj :refer
                                                :cljs :refer-macros) [defspec]]
            [clojure.test.check.properties #?(:clj :refer
                                              :cljs :refer-macros) [for-all]]))


(deftest normalize-test
  (are [x y] (= (-> x uri/parse n/normalize str) y)
    "http://example.com/a b c"     "http://example.com/a%20b%20c"
    "http://example.com/a%20b%20c" "http://example.com/a%20b%20c"
    "/𝍖"                          "/%F0%9D%8D%96"
    "http://foo.bar/?x=%20" "http://foo.bar/?x=%20")

  (are [x y] (= (-> x n/normalize str) y)
    (uri/map->URI {:query "x=y"}) "?x=y"
    (uri/map->URI {:query "x=?y#"}) "?x=?y%23"))


(deftest normalize-path-test
  (are [x y] (= (n/normalize-path x) y)
    "/abc" "/abc"
    "𝍖" "%F0%9D%8D%96"))

(deftest percent-encode-test
  (are [class comp result] (= (n/percent-encode comp class) result)
    :alpha "abcAbc" "abcAbc"
    :alpha "abc123" "abc%31%32%33"
    :path  "abc/123" "abc/123"
    :path  "abc/123:/#" "abc/123:/%23"
    :path  "𝍖" "%F0%9D%8D%96"))

(deftest percent-decode-test
  (are [in out] (= (n/percent-decode in) out)
    "%61%62%63" "abc"
    "%F0%9F%99%88%F0%9F%99%89" "🙈🙉"))

(defspec percent-encode-round-trip-test
  (for-all [s gen/string]
    (= (n/percent-decode (n/percent-encode s)) s)))

(defspec percent-encode-consistency-test
  (for-all [s gen/string]
    (re-find #"^(%[0-9A-F]{2})*$" (n/percent-encode s))))

(defspec normalize-idempotence-test
  (for-all [s gen/string]
    (= (-> s
           uri/parse
           n/normalize)
       (-> s
           uri/parse
           n/normalize
           n/normalize))))

(defspec reverse-uppercase-test
  (for-all [s gen/string]
    (= (-> s
           clojure.string/upper-case
           clojure.string/reverse)
       (-> s
           clojure.string/reverse
           clojure.string/upper-case))))
