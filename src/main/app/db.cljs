(ns app.db)

(def initial-app-db
  {:page    :home
   :showing :all
   :todos   (sorted-map)})
