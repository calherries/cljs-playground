(ns app.views
  (:require [app.routes]
            [app.subs]
            [re-frame.core :as rf]))

(defn page-view [{:keys [header content]}]
  [:div.page-wrapper
   [:header
    [:a.logo {:href (app.routes/home)} "Home"]
    [:h1 "Demo"]]
   [:main content]])

(defn about []
  [page-view
   {:content "This is about it."}])

(defn home []
  [page-view
   {:content [:a {:href (app.routes/about)} "Learn More"]}])

(defn app-view [{:keys [page-id]}]
  (case page-id
    :home
    [home]
    :about
    [about]))

(defn app-root []
  (app-view @(rf/subscribe [::app.subs/app-view])))

(comment (map inc (range 2)))
(comment (about))
