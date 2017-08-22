(ns ruby.addressable
  (:require [zweikopf.core :refer :all]
            [clojure.java.io :as io]))

(init-ruby-context)

(ruby-eval "$:.unshift('resources/gems/addressable-2.5.1')")
(ruby-eval "$:.unshift('resources/gems/public-suffix-2.0.5')")

(ruby-require "addressable/uri")

(def AddressableURI (ruby-eval "Addressable::URI"))

(defprotocol URIMethods
  (parse-uri [this string])
  (normalize-uri [this])
  (to-s [this])
  (to-h [this]))

(extend-protocol URIMethods
  org.jruby.RubyBasicObject
  (parse-uri [this string] (call-ruby this :parse string))
  (normalize-uri [this] (call-ruby this :normalize))
  (to-s [this string] (call-ruby this :to_s string))
  (to-h [this] (clojurize (call-ruby this :to_hash))))

(defn parse* [uri]
  (parse-uri AddressableURI uri))

(defn parse [uri]
  (to-h (parse* uri)))

(defn normalize [uri]
  (to-h (normalize-uri (parse* uri))))
