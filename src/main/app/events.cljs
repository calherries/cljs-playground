(ns app.events
  (:require [re-frame.core :as rf]
            [app.db :as db]
            [day8.re-frame.tracing :refer-macros [fn-traced]]))

(defn allocate-next-id
  [todos]
  ((fnil inc 0) (last (keys todos))))


(rf/reg-event-db
  :initialize-db
  (fn-traced  [_ _]
              db/initial-app-db))

(rf/reg-event-db
  :set-active-nav
  (fn-traced  [db [_ active-nav]]
              (assoc db :page active-nav)))

(rf/reg-event-db
  :toggle-done
  (fn-traced  [db [_ id]]
              (update-in db [:todos id :done] not)))

(rf/reg-event-db
  :save
  (fn-traced  [db [_ id title]]
              (assoc-in db [:todos id :title] title)))

(rf/reg-event-db
  :delete-todo
  (fn-traced  [db [_ id]]
              (update-in db [:todos] dissoc id)))

;; usage: (dispatch [:add-todo "a description string"])
(rf/reg-event-db
  :add-todo
  (fn-traced  [{:keys [todos] :as db} [_ text]]
              (let [id (allocate-next-id todos)]
                (assoc-in db [:todos id] {:id id :title text :done false}))))
