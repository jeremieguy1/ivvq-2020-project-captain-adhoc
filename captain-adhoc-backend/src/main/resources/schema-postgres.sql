DROP TABLE IF EXISTS acheteurs;
CREATE TABLE acheteurs(id_acheteur INTEGER PRIMARY KEY NOT NULL, nom_acheteur VARCHAR(255), identifiant_acheteur VARCHAR(255));

DROP TABLE IF EXISTS marchands;
CREATE TABLE marchands(id_marchand INTEGER PRIMARY KEY NOT NULL, identifiant_marchand VARCHAR(255));

DROP TABLE IF EXISTS commandes;
CREATE TABLE commandes(id_commande INTEGER PRIMARY KEY NOT NULL, date_commande TIMESTAMP, code , id_acheteur INTEGER FOREIGN KEY REFERENCES acheteurs(id_acheteur));

DROP TABLE IF EXISTS produits;
CREATE TABLE produits(id_produit INTEGER PRIMARY KEY NOT NULL, quantite_produit INTEGER, nom_produit VARCHAR(255), description_produit VARCHAR(255), id_marchand integer FOREIGN KEY REFERENCES marchands(id_marchand));

DROP TABLE IF EXISTS commande_produit_assoc;
CREATE TABLE commande_produit_assoc(quantite INTEGER, id_commande INTEGER FOREIGN KEY REFERENCES commandes(id_commande), id_produit INTEGER FOREIGN KEY REFERENCES commandes(id_produit));

