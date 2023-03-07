package tn.prestafind.utils;

import tn.prestafind.entities.Adresse;
import tn.prestafind.entities.Article;
import tn.prestafind.entities.Commentaire;
import tn.prestafind.entities.User;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import tn.prestafind.services.CRUDAdresse;
import tn.prestafind.services.CRUDArticle;
import tn.prestafind.services.CRUDCommentaire;
import tn.prestafind.services.CRUDUser;

public class HelperFunctions {

    public static void afficherMenu() throws SQLException {

        DBConnection PrestaFindDB = DBConnection.getInstance();

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n==== Menu Modules ====");
            System.out.println("1. Articles");
            System.out.println("2. Utilisateurs");
            System.out.println("3. Commentaires");
            System.out.println("4. Commandes");
            System.out.println("5. Gigs");
            System.out.println("6. Menu Modules");
            System.out.print("\nChoisissez une option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
//          ------------------------------- Blog -----------------------------------------------------
//          ------------------------------------------------------------------------------------------
                case 1: {
                    int randomInt = ThreadLocalRandom.current().nextInt(55, 999 + 1);
                    LocalDateTime datePub = LocalDateTime.now();

                    Article article = new Article(randomInt, 1,
                            "titre2", "contenu2", datePub,
                            LocalDateTime.now(),
                            1, 2, 3, 1, 2, true, null);

                    do {
                        System.out.println("\n==== Menu CRUD Articles ====");
                        System.out.println("1. Ajouter Article");
                        System.out.println("2. Supprimer Article");
                        System.out.println("3. Modifier Article");
                        System.out.println("4. Afficher Articles");
                        System.out.println("5. Menu Modules");
                        System.out.print("\nChoisissez une option: ");

                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice. Please enter a number.");
                            continue;
                        }

                        CRUDArticle crudArticle = new CRUDArticle();

                        switch (choice) {
                            case 1:
                                System.out.println("Ajout d'un article...");
                                crudArticle.ajouterArticle(article);
                                break;

                            case 2:
                                System.out.println("Suppression d'un article...");
                                crudArticle.supprimerArticle(randomInt);
                                break;

                            case 3:
                                System.out.println("Modification d'un article...");
                                article = new Article(randomInt, 1,
                                        "titre2", "contenu2", datePub, LocalDateTime.now(),
                                        1, 2, 3, 1, 2, true, null);
                                crudArticle.modifierArticle(article);
                                break;

                            case 4:
                                System.out.println("Les articles:");
                                System.out.println(crudArticle.getArticlesMax10());
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                                break;
                        }

                    } while (choice != 5);
                }
                break;

//          --------------------------------- Users --------------------------------------------------
//          ------------------------------------------------------------------------------------------
                case 2: {
                    int randomInt = ThreadLocalRandom.current().nextInt(55, 999 + 1);
                    Adresse adresse = new Adresse(randomInt, 120, "HabibBourguiba", "Tunis", "GrandTunis", 1000, "Tunisie");
                    CRUDAdresse crudAdresse = new CRUDAdresse();
                    crudAdresse.ajouterAdresse(adresse);

                    User user = new User("jdoe@example.com" + randomInt, "Johs",
                            "Doe", "johndoe" + randomInt, "password", adresse.getId(),
                            User.Role.USER, Calendar.getInstance(), User.Sexe.HOMME,
                            123456789, User.EtatUsr.INACTIF, "https://example.com/profile.jpg");

                    do {
                        System.out.println("\n==== Menu CRUD Users ====");
                        System.out.println("1. Ajouter User");
                        System.out.println("2. Supprimer User");
                        System.out.println("3. Modifier User");
                        System.out.println("4. Afficher Users");
                        System.out.println("5. Login");
                        System.out.println("6. Logout");
                        System.out.println("7. Menu modules");
                        System.out.print("\nChoisissez une option: ");

                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice. Please enter a number.");
                            continue;
                        }
                        CRUDUser crudUser = new CRUDUser();

                        switch (choice) {
                            case 1:
                                System.out.println("Ajout d'un utilisateur...");
                                crudUser.ajouterUser(user);
                                break;

                            case 2:
                                System.out.println("Suppression de l'utilisateur...");
                                crudUser.supprimerUser("jdoe@example.com" + randomInt);
                                break;

                            case 3:
                                System.out.println("Modification de l'utilisateur...");
                                crudUser.modifierUser(user);
                                break;

                            case 4:
                                System.out.println("Les utilisateurs:");
                                crudUser.afficherUsers();
                                break;

                            case 5:
                                System.out.println("logging in...");
                                if (crudUser.login(user.getEmail(), user.getMdp())) {
//                                    crudUser.setToken(user.getEmail());
                                    String token = UUID.randomUUID().toString();
                                    user.setToken(token);
                                    user.setEtat(User.EtatUsr.ACTIF);
                                    crudUser.modifierUser(user);
                                } else {
                                    System.out.println("erreur login.");
                                }
                                break;

                            case 6:
                                System.out.println("logging out...");
                                crudUser.logout(user.getEmail());
                                user.setEtat(User.EtatUsr.INACTIF);
                                break;

                            case 7:
                                break;

                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                                break;
                        }

                    } while (choice != 7);
                }
                break;

//          --------------------------------- Commentaire --------------------------------------------
//          ------------------------------------------------------------------------------------------
                case 3: {
                    Commentaire commentaire = new Commentaire(5, 1, "contenu", 0, 0, 0, LocalDateTime.now(), false);

                    do {
                        System.out.println("\n==== Menu CRUD Commentaires ====");
                        System.out.println("1. Ajouter Commentaire");
                        System.out.println("2. Supprimer Commentaire");
                        System.out.println("3. Modifier Commentaire");
                        System.out.println("4. Afficher Commentaires");
                        System.out.println("5. Menu modules");
                        System.out.print("\nChoisissez une option: ");

                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice. Please enter a number.");
                            continue;
                        }
                        CRUDCommentaire crudCommentaire = new CRUDCommentaire();

                        switch (choice) {
                            case 1:
                                System.out.println("Ajout d'un commentaire..");
                                crudCommentaire.ajouterCommenaire(commentaire);
                                break;

                            case 2:
                                System.out.println("Suppression de l'commentaire...");
                                crudCommentaire.supprimerCommentaire(1);
                                break;

                            case 3:
                                System.out.println("Modification de l'commentaire...");
                                crudCommentaire.modifierCommentaire(commentaire);
                                break;

                            case 4:
                                System.out.println("Les commentaires:");
                                crudCommentaire.afficherCommentaire();
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                                break;
                        }

                    } while (choice != 5);

                }
                break;

//          ---------------------------------  --------------------------------------------
//          ------------------------------------------------------------------------------------------
                case 4:
                    System.out.println("You chose Option 3.");
                    break;
                case 5:
                    System.out.println("You chose Option 3.");
                    break;
                case 6:
                    System.out.println("You chose Option 3.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }

        } while (choice != 6);
    }

}
