(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::app-view
 (fn [{:keys [page]}]
   {:page-id page}))
