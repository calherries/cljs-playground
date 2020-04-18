(ns app.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :active-nav
  (fn [{:keys [page]}]
    {:page-id page}))

(rf/reg-sub
  :showing
  (fn [db _]
    (get-in db [:showing])))

(rf/reg-sub
  :sorted-todos
  (fn [db _]
    (get-in db [:todos])))

(rf/reg-sub
  :todos
  (fn [query-v _]
    (rf/subscribe [:sorted-todos]))
  (fn [sorted-todos query-v _]
    (vals sorted-todos)))

(rf/reg-sub
  :visible-todos
  :<- [:todos]
  :<- [:showing]
  ;; (fn [query-v _]
  ;;   [(rf/subscribe [:todos])
  ;;    (rf/subscribe [:showing])])

  (fn [[todos showing] _]
    (let [filter-fn (case showing
                      :active (complement :done)
                      :done   :done
                      :all    identity)]
      (filter filter-fn todos))))

(rf/reg-sub
  :all-complete?
  :<- [:todos]
  (fn [todos _]
    (every? :done todos)))
