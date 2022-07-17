(ns app
  (:require [promesa.core :as p]))

(defn parse-event
  [event]
  (let [clj-event (js->clj event :keywordize-keys true)]
    (if (get clj-event :requestContext)                   ;; invoked via API Gateway.
      (-> clj-event (get :body) (js/JSON.parse) (js->clj :keywordize-keys true))
      clj-event)))

(defn handler [event _ctx]
  (p/let [response (-> event (parse-event))]
   (if response
     (clj->js {:statusCode 200
               :body       (js/JSON.stringify (clj->js response))})
     (clj->js {:statusCode 500}))))

;; exports
#js {:handler handler}