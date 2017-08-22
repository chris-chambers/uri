(ns lambdaisland.uri.errors)

(defn ^{:jsdoc ["@constructor"]}
  InvalidURIError [message data cause]
  (this-as this
    (.apply ExceptionInfo this #js [message data cause])))

(set! (.. InvalidURIError -prototype -__proto__) (.-prototype ExceptionInfo))

(defn invalid-uri-error [message data]
  (InvalidURIError. message data nil))
