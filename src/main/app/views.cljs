(ns app.views
  (:require [app.subs]
            [re-frame.core :as rf]
            [reagent.core :as r]))

(defn todo-input [{:keys [title on-save on-stop]}]
  (let [val  (r/atom title)
        stop #(do (reset! val "")
                  (when on-stop (on-stop)))
        save #(when (seq @val)
                (do
                  (on-save @val)
                  (stop)))]
    (fn []
      [:input {:type        "text"
               :value       @val
               :on-blur     save
               :on-change   #(reset! val (-> % .-target .-value))
               :on-key-down #(case (.-which %)
                               13 (save)
                               27 (stop)
                               nil)}])))

(defn todo-item []
  (let [editing? (r/atom false)]
    (fn [{:keys [id title done]}]
      [:li
       [:div
        [:input
         {:type      "checkbox"
          :checked   done
          :on-change #(rf/dispatch [:toggle-done id])}]
        [:label
         {:on-double-click #(reset! editing? true)}
         title]
        [:button
         {:on-click #(rf/dispatch [:delete-todo id])}
         "x"]]
       (when @editing?
         [todo-input {:on-save #(rf/dispatch [:save id %])
                      :title   title
                      :on-stop #(reset! editing? false)}])])))

(comment (def i {:id 1 :title "hello" :done true}))
(comment (todo-item i))

(defn todo-list
  []
  (let [visible-todos @(rf/subscribe [:visible-todos])]
    [:section#main
     [:ul
      (for [todo visible-todos]
        ^{:key (:id todo)}
        [todo-item todo])]]))

(comment (todo-list))
(comment (rf/dispatch [:add-todo "that's cool"]))

(defn page-view [{:keys [header content]}]
  [:div.page-wrapper
   [:header
    [:a.logo {:href     "#home"
              :on-click #(rf/dispatch [:set-active-nav :home])}
     "Home"]
    [:h1 "Demo"]]
   [:main content]])

(defn about []
  [page-view
   {:content "This was about it."}])


(defn home []
  [page-view
   {:content
    [:div
     [:section#todo
      [todo-input {:title   ""
                   :on-save #(rf/dispatch [:add-todo %])}]
      [todo-list]
      [:a {:href     "#about"
           :on-click #(rf/dispatch [:set-active-nav :about])}
       "Learn lots"]]]}])

(defn pages [{:keys [page-id]}]
  (case page-id
    :home  [home]
    :about [about]))

(defn app-root []
  (let [active-nav @(rf/subscribe [:active-nav])]
    (pages active-nav)))

(comment (map inc (range 2)))
(comment (about))
(comment (seq "hello"))
