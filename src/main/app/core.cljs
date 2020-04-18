(ns app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [reagent.dom :as rdom]
            [app.events]
            [app.subs]
            [app.views]
            [app.routes]))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (rdom/render [app.views/app-root]
               (.getElementById js/document "app")))

(defn ^:export init []
  (rf/dispatch-sync [:initialize-db])
  (app.routes/app-routes rf/dispatch)
  (mount-root))

(comment (map inc (range 10)))
