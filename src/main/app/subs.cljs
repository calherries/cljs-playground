(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :active-nav
  (fn [{:keys [page]}]
    {:page-id page}))

(rf/reg-sub
  :visible-todos
  (fn [{:keys [todos]}]
    (vals todos)))
