(ns lambdaisland.uri.errors)

(gen-class :name lambdaisland.uri.InvalidURIError
           :extends clojure.lang.ExceptionInfo)

(defn construct [class & args]
  (clojure.lang.Reflector/invokeConstructor
   (resolve class)
   (into-array Object args)))

(defn invalid-uri-error [message data]
  (try
    (resolve 'lambdaisland.uri.InvalidURIError)
    (catch java.lang.ClassNotFoundException e
      (compile 'lambdaisland.uri.errors)))
  (construct 'lambdaisland.uri.InvalidURIError "Message" {:foo :bar}))
