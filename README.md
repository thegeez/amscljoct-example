# October Amsterdam Clojure

Code from the presentation "Simpler webapps with Clojure" - Gijs
Stuurman

Presented at [#OctAmsClj](http://amsclj.nl/october.html) on October 27th 2012

# Run 
Install [leiningen](https://github.com/technomancy/leiningen)

Run the webapp:
```
lein run -m example.core
```
Browse the webapp at [http://localhost:8888/guess](http://localhost:8888/guess)

Run the tests (can't be run while the webapp runs, due to also using
port 8888):
```
lein test
```
