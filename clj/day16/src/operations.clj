(ns operations)

(defn greater?
  "Returns 1 if a > b, else 0"
  [a b]
  (if (> a b) 1 0))

(defn equal?
  "Returns 1 if a == b, else 0"
  [a b]
  (if (= a b) 1 0))

;;;;;;;;;;;;;;;;;;;;;;;;;
;; register operations ;;
;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- exec
  "Sets register C to (ofn (mapa a) (mapb b))."
  [ofn r i & {:keys [mapa mapb] :or {mapa (partial get r) mapb (partial get r)}}]
  (let [[_ a b c] i]
    (assoc r c (ofn (mapa a) (mapb b)))))

(defn addr
  "add register"
  [r i]
  (exec + r i))

(defn addi
  "add immediate"
  [r i]
  (exec + r i :mapb identity))

(defn mulr
  "multiply register"
  [r i]
  (exec * r i))

(defn muli
  "multiply immediate"
  [r i]
  (exec * r i :mapb identity))

(defn banr
  "bitwise AND register"
  [r i]
  (exec bit-and r i))

(defn bani
  "bitwise AND immediate"
  [r i]
  (exec bit-and r i :mapb identity))

(defn borr
  "bitwise OR register"
  [r i]
  (exec bit-or r i))

(defn bori
  "bitwise OR immediate"
  [r i]
  (exec bit-or r i :mapb identity))

(defn setr
  "set register"
  [r i]
  (exec (fn [ra _] ra) r i))

(defn seti
  "set immediate"
  [r i]
  (exec (fn [a _] a) r i :mapa identity))

(defn gtir
  "greater-than immediate/register"
  [r i]
  (exec (fn [a rb] (greater? a rb)) r i :mapa identity))

(defn gtri
  "greater-than register/immediate"
  [r i]
  (exec (fn [ra b] (greater? ra b)) r i :mapb identity))

(defn gtrr
  "greater-than register/register"
  [r i]
  (exec (fn [ra rb] (greater? ra rb)) r i))

(defn eqir
  "equal immediate/register"
  [r i]
  (exec (fn [a rb] (equal? a rb)) r i :mapa identity))

(defn eqri
  "equal register/immediate"
  [r i]
  (exec (fn [ra b] (equal? ra b)) r i :mapb identity))

(defn eqrr
  "equal register/register"
  [r i]
  (exec (fn [ra rb] (equal? ra rb)) r i))
