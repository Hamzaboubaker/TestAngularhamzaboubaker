package tn.prestafind.services;

import tn.prestafind.entities.Commentaire;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

public interface InterfaceCRUDCommentaire {

    public void ajouterCommenaire(Commentaire commentaire) throws SQLException;

    public void modifierCommentaire(Commentaire commentaire) throws SQLException;

    public void supprimerCommentaire(int id) throws SQLException;

    public List<Commentaire> afficherCommentaire() throws SQLException;
      public List<Commentaire> RECHERCHE(String contenu );
       public List<Commentaire> getID(int id);

}
