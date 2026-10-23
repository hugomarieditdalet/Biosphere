#!/bin/bash

# Script pour copier votre code source vers votre dépôt, avec les bons droits.

# 2 variables à régler :

# votre identifiant (exemple : ogauwin) :
IDENTIFIANT=hmarieditdal            
# chemin vers le répertoire de votre projet NetBeans, sur votre compte (là où se trouvent src/ et test/) :
REP_NETBEANS="/mnt/netta/users/hmarieditdal/Documents/R1.01_AlgoProg/algoprogs1/Biosphere7"

# ce qui suit n'a pas besoin d'être modifié :

JEU=biosphere7
PROMO=info_s1
REP_SRC="$REP_NETBEANS/src/$JEU"
REP_TEST="$REP_NETBEANS/test/$JEU"
# répertoire de votre dépôt sur Remise
REP_DEPOT="/mnt/netta/users/$IDENTIFIANT/mnt-remise/$JEU/$PROMO/$IDENTIFIANT/depot"
# copie
cp -r "$REP_SRC"/*.java "$REP_TEST"/*.java "$REP_DEPOT/$JEU"
# si vous voulez lancer la validation automatiquement :
# rm -f "$REP_DEPOT/sortie.log" "$REP_DEPOT/compilation.log"
