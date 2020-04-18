(ns app.views
  (:require [app.subs]
            [re-frame.core :as rf]))

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
   {:content "This is about it."}])

(defn home []
  [page-view
   {:content [:a {:href     "#about"
                  :on-click #(rf/dispatch [:set-active-nav :about])}
              "Learn More"]}])

(defn pages [{:keys [page-id]}]
  (case page-id
    :home  [home]
    :about [about]))

(defn app-root []
  (let [active-nav @(rf/subscribe [:active-nav])]
    (pages active-nav)))

(comment (map inc (range 2)))
(comment (about))
