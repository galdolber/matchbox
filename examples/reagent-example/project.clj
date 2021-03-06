(defproject reagent-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.facebook/react "0.12.2.4"]
                 [reagent "0.5.0"]
                 [reagent-forms "0.4.6"]
                 [reagent-utils "0.1.4"]
                 [secretary "1.2.2"]
                 [org.clojure/clojurescript "0.0-3149" :scope "provided"]
                 [com.cemerick/piggieback "0.1.5"]
                 [weasel "0.6.0"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.4"]
                 [prone "0.8.1"]
                 [compojure "1.3.2"]
                 [selmer "0.8.2"]
                 [environ "1.0.0"]
                 [leiningen "2.5.1" :exclusions [org.apache.httpcomponents/httpcore]]
                 [figwheel "0.2.5"]

                 [matchbox "0.0.5-SNAPSHOT"]]

  :plugins [
            [lein-cljsbuild "1.0.4"]
            [lein-environ "1.0.0"]
            [lein-ring "0.9.1"]
            [lein-asset-minifier "0.2.2"]]

  :ring {:handler reagent-example.handler/app
         :uberwar-name "reagent-example.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "reagent-example.jar"

  :main reagent-example.server

  :clean-targets ^{:protect false} ["resources/public/js"]

  :minify-assets
  {:assets
   {"resources/public/css/site.min.css" "resources/public/css/site.css"}}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :externs       ["react/externs/react.js"]
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns reagent-example.handler
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[ring-mock "0.1.5"]
                                  [ring/ring-devel "1.3.2"]
                                  [pjstadig/humane-test-output "0.7.0"]]

                   :plugins [[lein-figwheel "0.2.5"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler reagent-example.handler/app}

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:source-map true}}}}}

             :uberjar {:hooks [leiningen.cljsbuild minify-assets.plugin/hooks]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}

             :production {:ring {:open-browser? false
                                 :stacktraces?  false
                                 :auto-reload?  false}}})
