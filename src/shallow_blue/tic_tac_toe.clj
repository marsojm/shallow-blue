(ns shallow-blue.tic-tac-toe)

(defn create-board
  [size empty-elem]
  (vec (repeat size (vec (repeat size empty-elem)))))

(defn- contains-element?
  [coll elem]
  (some #{elem} (flatten coll)))

(defn any-moves-left?
  [board empty-elem]
  (contains-element? board empty-elem))

(defn valid-moves
  [board empty-marker]
  (let [coords (for [x (range (count board))
                     y (range (count board))]
                 [x y])
        marked-coords (map #( hash-map %1 %2) (flatten board) coords)]
    (map empty-marker (filter empty-marker marked-coords))))


(defn move
  [board coord player]
  (assoc-in board coord player))


(defn- get-cols
  [board]
  (let [n (count (first board))
        flat-board (flatten board)]
    (loop [i 0
           b flat-board
           res []]
      (if (< i n)
        (recur (inc i) (rest b) (conj res (vec (take-nth n b))))
        res))))

(defn- get-diagonals
  [flat-board n]
  (loop [brd flat-board
         nth-val 1
         results []]
    (if (<= nth-val n)
      (let [diag (vec (take nth-val (take-nth (dec n) brd)))
            new-nth-val (inc nth-val)]
        (recur (rest brd) new-nth-val (conj results diag)))
      results)))


(defn- get-all-diagonals
  [board]
  (let [n (count board)
        brd (flatten board)
        mirrored-brd (flatten (map reverse board))]
    (vec (apply concat (conj []
          (get-diagonals brd n)
          (get-diagonals (reverse brd) n)
          (get-diagonals mirrored-brd n)
          (get-diagonals (reverse mirrored-brd) n))))))


(defn- split-board
  "Splits board into row, col, and diagonal vectors"
  [board n]
  (let [rows board
        cols (get-cols board)
        diags (filter #(>= (count %) n) (get-all-diagonals board))]
    (concat rows cols diags)))

(defn- find-consecutives
  [coll]
  (partition-by identity coll))

(defn- find-n-consecutive-elements
  [n coll]
  (map first (filter #(= (count %) n) (find-consecutives coll))))

(defn player-has-won?
  [player board win-cond]
  (let [parts (split-board board win-cond)]
    (contains? (into #{} (flatten (map #(find-n-consecutive-elements win-cond %) parts))) player)))
