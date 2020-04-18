(ns app.events
  (:require [re-frame.core :as rf]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(rf/reg-event-db
 :initialize-db
 (fn-traced  [_ _]
   {:page :home}))

(rf/reg-event-db
 :routes/home
 (fn-traced  [db _]
   (-> db
       (assoc :page :home) )))

(rf/reg-event-db
 :routes/about
 (fn-traced  [db _]
   (-> db
       (assoc :page :about))))
