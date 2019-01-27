(ns frontend.core
  (:require [rum.core :as rum]))

(enable-console-print!)

(println "This text is printed from src/frontend/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "This is not editable" :counter 0}))

(comment 
 (rum/defcs child-component < (rum/local {:counter 100})
   [local-state name]

   [:div
    [:p name]
    [:button {:on-click (fn [_] (swap! (get local-state :rum/local) update :counter dec))} "No, click me!"]
    [:pre (:counter @(get local-state :rum/local))]]))

(rum/defcs child-component < (rum/local {:counter 100})
  [{:keys [rum/local]} name]

  [:div
   [:p name]
   [:button {:on-click (fn [_] (swap! local update :counter dec))} "No, click me!"]
   [:pre (:counter @local)]])

(rum/defc hello-world < rum/reactive
  []
  (let [db (rum/react app-state)]
    [:div
     [:h1 (:text @app-state)]
     [:h3 "This is editable"]
     (child-component "this is the name")
     [:pre "counter = " (:counter @app-state)]
     [:button {:on-click (fn [e] (swap! app-state update :counter inc))} "Click me"]]))



(rum/mount (hello-world)
           (. js/document (getElementById "app")))

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)

