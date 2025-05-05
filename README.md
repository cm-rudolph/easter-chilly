# Solver für das c't Osterrätsel 2025

Das Rätsel wurde in c’t 9/2025 auf S. 150 gestellt und der Code dazu befindet sich im folgenden Repository:
[https://github.com/626d7a7179207166206f75646f717a657165/easter-chilly](https://github.com/626d7a7179207166206f75646f717a657165/easter-chilly)

## Problemanalyse

Das Problem ist ein Problem des längsten Weges. Der Graph ist weder zyklenfrei noch von sonst einer erkennbaren
besonderen Struktur, für die ein vereinfachter Lösungsalgorithmus bekannt ist.

## Lösungsansatz

Aufgrund der vergleichsweise geringen Größe des Suchraumes erfolgt die Suche mittels simpler Tiefensuche.
Es gibt auch keinerlei Parallelisierung oder andere Optimierungsmaßnahmen. Den gesamten Suchbaum vom Level 3 zu
scannen benötigt, je nach Hardware, mehrere Tage.

Die Spielregeln werden mittels angepasster Factory in der Main-Klasse (`EdgeVisitingNodeFactory` oder
`NodeVisitingNodeFactory`) ausgewählt.

## Optimale Lösungen

Nachfolgend jeweils eine optimale Lösung:

Rutschpartie 1 (32 Züge): `LULRDRUDULLURLDRLRDUDLDLRURDLRUL`

Rutschpartie 2 (40 Züge): `UDUDLDURLDULDULRDULRURLUDLRDULRULRDRLDRL`

Rutschpartie 3 (83 Züge): `LDURDDULDULRDLURLUDULDLRULDURDLURUDURLRULRLDUDRLRULDRUDRLURLRURLRDURLDRURDRLULDUDRU`
