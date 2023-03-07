package tn.prestafind.services;

import tn.prestafind.entities.Adresse;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceCRUDAdresse {

    public void ajouterAdresse(Adresse adresse) throws SQLException;

    public void modifierAdresse(Adresse adresse) throws SQLException;

    public void supprimerAdresse(int id) throws SQLException;

    public List<Adresse> afficherAdresses() throws SQLException;

}
