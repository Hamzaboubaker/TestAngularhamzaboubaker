package tn.prestafind.services;

import tn.prestafind.entities.Article;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import tn.prestafind.utils.DBConnection;

public class CRUDArticle implements InterfaceCRUDArticle {

    Statement statement = null;
    Connection PrestaFindDB = DBConnection.getConnection();

    @Override
    public void ajouterArticle(Article article) throws SQLException {
        try {
            statement = PrestaFindDB.createStatement();
            String dateModString = null;

            String datePubString = article.getDatePublication()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (article.getDateModification() != null) {
                dateModString = article.getDateModification()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            String req = "INSERT INTO articles VALUES("
                    + article.getId() + ",'"
                    + article.getAuteurId() + "','"
                    + article.getTitre() + "','"
                    + article.getContenu() + "','"
                    + datePubString + "','"
                    + dateModString + "',"
                    + article.getNbrVues() + ","
                    + article.getNbrPartages() + ","
                    + article.getNbrLikes() + ","
                    + article.getNbrDislikes() + ","
                    + article.getNbrRapports() + ","
                    + article.isRetireParModerateur()
                    + ")";

//            System.out.println("reqAjouter: " + req);
            statement.executeUpdate(req);
            System.out.println("Article ajoute avec succes!");
        } catch (SQLException exception) {
//            throw new SQLException("Article non ajoute!");
            System.out.println("Article non ajoute!");
            return;
        }
    }

    @Override
    public void modifierArticle(Article article) {
        try {
            String dateModString = null;
            String datePubString = article.getDatePublication()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (article.getDateModification() != null) {
                dateModString = article.getDateModification()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            String req = "UPDATE `articles` SET"
                    + " `auteur` = '" + article.getAuteurId()
                    + "', `titre` = '" + article.getTitre()
                    + "', `contenu` = '" + article.getContenu()
                    + "', `datePublication` = '" + datePubString
                    + "', `dateModification` = '" + dateModString
                    + "', `nbrVues` = " + article.getNbrVues()
                    + ", `nbrPartages` = " + article.getNbrPartages()
                    + ", `nbrLikes` = " + article.getNbrLikes()
                    + ", `nbrDislikes` = " + article.getNbrDislikes()
                    + ", `nbrRapports` = " + article.getNbrRapports()
                    + ", `retireParModerateur` = " + article.isRetireParModerateur()
                    + " WHERE `articles`.`id` = " + article.getId();

//            System.out.println("reqModifier: " + req);
            Statement st = PrestaFindDB.createStatement();
            st.executeUpdate(req);
            System.out.println("Article updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerArticle(int id) {
        try {
            String req = "DELETE FROM `articles` WHERE id = " + id;
            Statement st = PrestaFindDB.createStatement();
            st.executeUpdate(req);
            System.out.println("Article deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Article> getArticlesMax10() {
        List<Article> articles = new ArrayList<>();
        try {
            Timestamp dateMod = null;
            String req = "SELECT * FROM `articles` ORDER BY datePublication DESC LIMIT 10";
            Statement st = PrestaFindDB.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setAuteurId(rs.getInt("auteur"));
                article.setTitre(rs.getString("titre"));
                article.setContenu(rs.getString("contenu"));
                article.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
                if ((dateMod = rs.getTimestamp("dateModification")) != null) {
                    article.setDateModification(dateMod.toLocalDateTime());
                }
                article.setNbrVues(rs.getInt("nbrVues"));
                article.setNbrPartages(rs.getInt("nbrPartages"));
                article.setNbrLikes(rs.getInt("nbrLikes"));
                article.setNbrDislikes(rs.getInt("nbrDislikes"));
                article.setNbrRapports(rs.getInt("nbrRapports"));
                article.setRetireParModerateur(rs.getBoolean("retireParModerateur"));
                articles.add(article);
            }
//            System.out.println(articles);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return articles;
    }
}
