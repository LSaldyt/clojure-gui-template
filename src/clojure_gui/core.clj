(ns clojure-gui.core
  (:require [seesaw.core :refer :all])
  (:gen-class))

(defn create-split
  [& args]
  (def lb (listbox :model (-> 'seesaw.core ns-publics keys sort)))
  (def area (text :multi-line? true :font "MONOSPACED-PLAIN-14"
                                  :text "This
                                        is 
                                        multi
                                        line
                                        text"))

  (defn doc-str [s] (-> (symbol "seesaw.core" (name s)) resolve meta :doc))
  (listen lb :selection
    (fn [e]
	  (when-let [s (selection e)]
	  (-> area
	  (text!   (doc-str s))
	  (scroll! :to :top)))))
  (left-right-split (scrollable lb) (scrollable area) :divider-location 1/3))

(defn main-frame
  "Create the main frame for the GUI program"
  [& args]
  (def f (frame :title "Seesaw GUI template"))
  (-> f pack! show!)
  (config! f :size [300 :by 300])
  (defn display [content]
      (config! f :content content)
      content)
  (def split (create-split))
  (def rbs (for [i [:source :doc]] 
             (radio :id i :class :type :text (name i))))
  (display (border-panel
			 :north (horizontal-panel :items rbs)
			 :center split
			 :vgap 5 :hgap 5 :border 5)))

(defn -main
  "Initialize the main frame and handle command line arguments"
  [& args]
  (main-frame args))
