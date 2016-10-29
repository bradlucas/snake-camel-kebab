(ns snake-camel-kebab.core
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]))

;; this-is-kebab-case
;; thisIsCamelCase
;; this_is_snake_case

;; Note CamelCase can have an upper or lower case first letter
;; Here I'll default to a lower case


;; Tokenize the string
;; either by dashes, underscores or capitalized letters
(defn tokenize [s]
  (let [tokens (string/split s #"-|_")]
    (if (> (count tokens) 1)
      tokens
      ;; else try to split on capitalized letters
      (string/split s #"(?=[A-Z])"))))


;; Output the tokens in the desired format
(defn convert [type words]
  (case type
    :snake (string/join "_" words)
    :camel (str (string/lower-case (first words)) (string/join (map string/capitalize (rest words))))
    :kebab (string/join "-" words)))

(def cli-options
  [["-s" "--snake"]
   ["-c" "--camel"]
   ["-k" "--kebab"]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> [""
        "Usage: snake-camel-kebab [option] STRING"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main 
  [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (cond
     (and (or (:snake options) (:camel options) (:kebab options)) (not-empty arguments)) (println (convert (first (keys options)) (tokenize (first arguments))))
     (:help options) (exit 0 (usage summary))
     errors (exit 1 (error-msg errors))
     true (exit 0 (usage summary)))))
