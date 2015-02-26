(ns shallow-blue.core
  (:gen-class)
  (require [shallow-blue.tic-tac-toe :as game]
           [clojure.string :as s]))


(def winning-condition (atom 3))
(def player-x :x)
(def player-o :o)
(def empty-marker :e)

(defn ask-board-size
  [q]
  (print "Give me a size of the game board: ")
  (let [input (read-line)]
    (read-string input)))


;; Printing functions
(defn print-board
  [board]
  (let [n (count board)]
    (dotimes [i n] (println (nth board i)))))

;; --------------------

(defn parse-move
  [input]
  (let [[x y] (s/split input #" ")]
    [(read-string x) (read-string y)]))

(defn valid-move?
  [board move]
  (let [v (set (game/valid-moves board empty-marker))]
    (contains? v move)))

(defn valid-format?
  [input]
  (re-find #"[0-9]+ [0-9]+" input))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Welcome to the world of Tic-Tac-Toe!")
  (let [size 3
        win-cond 3
        board (game/create-board size empty-marker)]
    (print-board board)
    (println "Move (:x): ")
    (loop [input (read-line)
           brd board
           player player-x]
      (when-not (= ":quit" input)
        (if (and (valid-format? input) (valid-move? brd (parse-move input)))
          (let [m (parse-move input)
                b (game/move brd (parse-move input) player)
                p (if (= player player-x) player-o player-x)]
            (println)
            (print-board b)
            (cond
             (game/player-has-won? player b win-cond) (println "Player " player " won!")
             (not (game/any-moves-left? b empty-marker)) (println "It's a draw!")
             :else
             (do
               (println "Move (" p "): ")
               (recur (read-line) b p))))
          (do
            (println "Illegal move!")
            (print-board brd)
            (println "Move (" player "): ")
            (recur (read-line) brd player)))))))
