package tn.prestafind.services;

import tn.prestafind.entities.Commentaire;
import java.security.Timestamp;
import tn.prestafind.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CRUDCommentaire implements InterfaceCRUDCommentaire {

    Statement statement = null;
    Connection PrestaFindDB = DBConnection.getConnection();

    @Override
    public void ajouterCommenaire(Commentaire commentaire) throws SQLException {
        String datePubString = commentaire.getDatePublication()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BadWords.loadConfigs();

        PreparedStatement stmt = PrestaFindDB.prepareStatement("INSERT INTO commentaires VALUES(?, ?,?, ?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, commentaire.getId());
        stmt.setInt(2, commentaire.getAuteurId());
        stmt.setString(3, commentaire.getContenu());
        stmt.setString(4, datePubString);
        stmt.setInt(5, commentaire.getNbLikes());
        stmt.setInt(6, commentaire.getNbDisLikes());
        stmt.setInt(7, commentaire.getNbSignals());
        stmt.setBoolean(8, commentaire.isRetireParModrateur());
                stmt.setString(9, commentaire.getImage());

        stmt.executeUpdate();

    }

    @Override
    public void modifierCommentaire(Commentaire commentaire) throws SQLException {
        String datePubString = commentaire.getDatePublication().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PreparedStatement stmt = PrestaFindDB.prepareStatement("UPDATE commentaires SET auteurId=?, contenu=?, datePublication=?, nbrLikes=?, nbrDislikes=?, nbrSignals=?, retireParModerateur=? WHERE id=?");
        stmt.setInt(1, commentaire.getAuteurId());
        stmt.setString(2, commentaire.getContenu());
        stmt.setString(3, datePubString);
//        stmt.setDate(7, new Date(commentaire.getDateNaissance().getTimeInMillis()));
        stmt.setInt(4, commentaire.getNbLikes());
        stmt.setInt(5, commentaire.getNbDisLikes());
        stmt.setInt(6, commentaire.getNbSignals());
        stmt.setBoolean(7, commentaire.isRetireParModrateur());
        stmt.setInt(8, commentaire.getId());
        stmt.executeUpdate();
    }

    @Override
    public void supprimerCommentaire(int id) throws SQLException {
        try {
            String req = "DELETE FROM `commentaires` WHERE id = " + id;
            Statement st = PrestaFindDB.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Commentaire> afficherCommentaire() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        try {
            Timestamp datePub = null;
            String req = "SELECT * FROM `commentaires`  ORDER BY datePublication DESC LIMIT 10";
            Statement st = PrestaFindDB.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(rs.getInt("id"));
                commentaire.setAuteurId(rs.getInt("auteurId"));
                commentaire.setContenu(rs.getString("contenu"));
                commentaire.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
//                if ((datePub = rs.getTimestamp("datePublication")) != null) {
//                    commentaire.setDatePublication(datePub.toLocalDateTime());
//                }
           commentaire.setImage(rs.getString("image"));
                commentaire.setNbLikes(rs.getInt("nbrLikes"));
                commentaire.setNbDisLikes(rs.getInt("nbrDislikes"));
                commentaire.setNbSignals(rs.getInt("nbrSignals"));
                commentaire.setRetireParModrateur(rs.getBoolean("retireParModerateur"));
                commentaires.add(commentaire);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commentaires;
    }
    
    
       @Override
         public ObservableList<Commentaire> RECHERCHE(String contenu ) {
     
  
         ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();
     
        String req = "SELECT * FROM `commentaires` WHERE contenu LIKE '" + contenu + "%'   ORDER BY datePublication DESC LIMIT 10";
       
        try {
             Statement st = PrestaFindDB.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while (rs.next()){
                   Commentaire commentaire = new Commentaire();
                commentaire.setId(rs.getInt("id"));
                commentaire.setAuteurId(rs.getInt("auteurId"));
                commentaire.setContenu(rs.getString("contenu"));
                commentaire.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
//                if ((datePub = rs.getTimestamp("datePublication")) != null) {
//                    commentaire.setDatePublication(datePub.toLocalDateTime());
//                }
           commentaire.setImage(rs.getString("image"));
                commentaire.setNbLikes(rs.getInt("nbrLikes"));
                commentaire.setNbDisLikes(rs.getInt("nbrDislikes"));
                commentaire.setNbSignals(rs.getInt("nbrSignals"));
                commentaire.setRetireParModrateur(rs.getBoolean("retireParModerateur"));
                commentaires.add(commentaire);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (commentaires);
     }
          @Override
          public List<Commentaire> getID(int id){
              
         ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();
                String req = "SELECT * FROM `commentaires`WHERE id = ?";
                     try {
             Statement st = PrestaFindDB.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while (rs.next()){
                   Commentaire commentaire = new Commentaire();
                commentaire.setId(rs.getInt("id"));
                commentaire.setAuteurId(rs.getInt("auteurId"));
                commentaire.setContenu(rs.getString("contenu"));
                commentaire.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
//                if ((datePub = rs.getTimestamp("datePublication")) != null) {
//                    commentaire.setDatePublication(datePub.toLocalDateTime());
//                }
           commentaire.setImage(rs.getString("image"));
                commentaire.setNbLikes(rs.getInt("nbrLikes"));
                commentaire.setNbDisLikes(rs.getInt("nbrDislikes"));
                commentaire.setNbSignals(rs.getInt("nbrSignals"));
                commentaire.setRetireParModrateur(rs.getBoolean("retireParModerateur"));
                commentaires.add(commentaire);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (commentaires);
          }
}
