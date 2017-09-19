(ns lambdaisland.uri.platform)

(defn string->utf8-byte-seq [s]
  (.getBytes s "UTF8"))

(defn utf8-byte-seq->string [arr]
  (String. (byte-array arr) "UTF8"))

(defn hex->byte [hex]
  (Integer/parseInt hex 16))

(defn byte->hex [byte]
  (format "%02X" byte))

(defn char-code-at [str pos]
  (long (.charAt str pos)))

(defn string->long [str]
  (Long/parseLong str))
