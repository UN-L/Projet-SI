#!/bin/bash

# Affichage du nom de l'hôte
echo "Nom de l'hôte : $(hostname)"

# Affichage du système d'exploitation
echo "Système d'exploitation : $(uname -o)"

# Affichage de la version du noyau
echo "Version du noyau : $(uname -r)"

# Affichage de l'architecture du système
echo "Architecture : $(uname -m)"

# Affichage de la mémoire disponible
echo "Mémoire disponible :"
free -h | grep "Mem:" | awk '{print $2 " de RAM disponible"}'

# Affichage de l'espace disque disponible
echo "Espace disque disponible :"
df -h | grep "^/dev" | awk '{print $6 ": " $4 " disponibles"}'
