INSERT INTO acheteurs(id_acheteur;nom_acheteur;identifiant_acheteur) VALUES(1, 'acheteur1','identifiant_a');
INSERT INTO commande_produit_assoc(id_commande;id_produit;quantite) VALUES(1, 1,5);
INSERT INTO commandes(id_commande;date_commande;code;id_acheteur) VALUES(1, 1280000);
INSERT INTO marchands(id_marchand;identifiant_marchand) VALUES(1, 'identifiant_m');
INSERT INTO produits(id_produit;quantite_produit;nom_produit;description_produit;id_marchand) VALUES(1, 50, 'produit', 'description_produit', 1);
