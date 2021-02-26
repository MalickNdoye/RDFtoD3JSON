# RDFtoD3JSON
A module for to convert RDF Graph into json file compatible with D3.JS.

usage: CLIsample [-f <input>] [-h] [-o <output>]
 -f <input>    Fichier(s) à convertir. Plusieurs fichiers en simultanés
               acceptés.
 -h,--help     Aide
 -o <output>   Répertoire de sortie

#Exemple
$java -jar RDFtoJSON.jar -o ./ -f <fichier1> <fichier2> <fichier3> ...
