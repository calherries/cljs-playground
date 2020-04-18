(ns app.events
  (:require [re-frame.core :as rf]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(rf/reg-event-db
 :initialize-db
 (fn-traced  [_ _]
   {:page :home}))

(rf/reg-event-db
  :set-active-nav
  (fn-traced  [db [_ active-nav]]
              (assoc db :page active-nav)))
