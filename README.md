# parser
Java Parser Programm was mathematische Ausdrücke einer gewissen Form erkennt und ggf ausrechnet.

Auszug der Aufgabenstellung:

In dieser Aufgabe geht es um das Auswerten von arithmetischen Ausdrücken, welche ausschließ-
lich aus Ziffern, öffnenden und schließenden Klammern und den Operatoren +, −, ∗, / bestehen.
Hierbei steht der Operator „/“ für Ganzzahldivision. Wir definieren einen wohlgeformten arith-
metischen Ausdruck wie folgt:


• Eine Ziffer d ∈ {0, 1, 2, 3, 4, 5, 6, 7, 8, 9} ist ein wohlgeformter arithmetischer Ausdruck.
• Sind X und Y wohlgeformte arithmetische Ausdrücke, dann sind auch die vier Ausdrücke (X + Y), (X − Y), (X ∗ Y) und (X/Y) wohlgeformte arithmetische Ausdrücke.


Beispiele für wohlgeformte arithmetische Ausdrücke entsprechend dieser Definition sind unter
anderem: „((8+7)∗2)“, „(4−(7−1))“ oder „8“. Bei den Ausdrücken „)8+)1(())“, „(8+())“, „−1“,
„( 5 − 7)“, „108“ oder „(8)“ handelt es sich hingegen nicht um wohlgeformte arithmetische
Ausdrücke.
