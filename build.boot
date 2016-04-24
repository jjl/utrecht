(set-env!
 :project 'irresponsible/utrecht
  :version "0.2.0"
  :resource-paths #{"src"}
  :source-paths #{"src"}
  :dependencies '[[org.clojure/clojure       "1.8.0" :scope "provided"]
                  [org.clojure/java.jdbc     "0.6.0-alpha2"]
                  [cheshire                  "5.6.1"]
                  [hikari-cp                 "1.6.1"]
                  [clojure.java-time         "0.2.0"]
                  ;; don't force the user to use component
                  [com.stuartsierra/component "0.3.1" :scope "provided"]
                  [suspendable "0.1.1"                :scope "provided"]
                  ;; build and test
                  [org.postgresql/postgresql "9.4.1208" :scope "test"]
                  [environ "1.0.2"                      :scope "test"]
                  [adzerk/boot-test          "1.1.0"    :scope "test"]])
                  
(require '[adzerk.boot-test :as t])

(task-options!
  pom {:project (get-env :project)
       :version (get-env :version)
       :description "Just enough rope to wrangle a jdbc"
       :url "https://github.com/irresponsible/utrecht"
       :scm {:url "https://github.com/irresponsible/utrecht.git"}
       :license {"MIT" "https://en.wikipedia.org/MIT_License"}}
  target  {:dir #{"target"}})

(deftask testing []
  (set-env! :source-paths   #(conj % "test")
            :resource-paths #(conj % "test"))
  identity)

(deftask test []
  (comp (testing) (speak) (t/test)))

(deftask autotest []
  (comp (testing) (watch) (speak) (t/test)))

(deftask make-release-jar []
  (comp (pom) (jar)))