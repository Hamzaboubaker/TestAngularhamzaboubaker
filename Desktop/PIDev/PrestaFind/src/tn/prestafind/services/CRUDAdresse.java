package tn.prestafind.services;

import tn.prestafind.entities.Adresse;
import java.util.List;
import tn.prestafind.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUDAdresse implements InterfaceCRUDAdresse {

    Connection PrestaFindDB = DBConnection.getConnection();

    @Override
    public void ajouterAdresse(Adresse adresse) throws SQLException {
        PreparedStatement stmt = PrestaFindDB.prepareStatement("INSERT INTO adresses VALUES(?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, adresse.getId());
        stmt.setInt(2, adresse.getNumBatiment());
        stmt.setString(3, adresse.getRue());
        stmt.setString(4, adresse.getVille());
        stmt.setString(5, adresse.getGouvernorat());
        stmt.setInt(6, adresse.getCodePostal());
        stmt.setString(7, adresse.getPays());
        stmt.executeUpdate();
    }

    @Override
    public void modifierAdresse(Adresse adresse) throws SQLException {
        String sql = "UPDATE adresses SET numBatiment = ?, rue = ?, ville = ?, gouvernorat = ?, codePostal = ?, pays = ? WHERE id = ?";
        PreparedStatement stmt = PrestaFindDB.prepareStatement(sql);
        stmt.setInt(1, adresse.getNumBatiment());
        stmt.setString(2, adresse.getRue());
        stmt.setString(3, adresse.getVille());
        stmt.setString(4, adresse.getGouvernorat());
        stmt.setInt(5, adresse.getCodePostal());
        stmt.setString(6, adresse.getPays());
        stmt.setInt(7, adresse.getId());
        stmt.executeUpdate();
    }

    @Override
    public void supprimerAdresse(int id) throws SQLException {
        String sql = "DELETE FROM adresses WHERE id = ?";
        PreparedStatement stmt = PrestaFindDB.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<Adresse> afficherAdresses() throws SQLException {
        List<Adresse> adresseList = new ArrayList<>();
        Statement stmt = PrestaFindDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateurs");
        while (rs.next()) {
            adresseList.add(getAddressFromResultSet(rs));
        }
        System.out.println(adresseList);
        return adresseList;
    }

    private Adresse getAddressFromResultSet(ResultSet rs) throws SQLException {
        Adresse adresse = new Adresse();
        adresse.setId(rs.getInt("id"));
        adresse.setNumBatiment(rs.getInt("numBatiment"));
        adresse.setRue(rs.getString("rue"));
        adresse.setVille(rs.getString("ville"));
        adresse.setGouvernorat(rs.getString("gouvernorat"));
        adresse.setCodePostal(rs.getInt("codePostal"));
        adresse.setPays(rs.getString("pays"));
        return adresse;
    }

    public Adresse getIdressByID(int id) throws SQLException {
        Adresse adresse = null;
        String sql = "SELECT * FROM adresses WHERE id = ?";
        PreparedStatement stmt = PrestaFindDB.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            adresse = new Adresse(
                    rs.getInt("id"),
                    rs.getInt("numBatiment"),
                    rs.getString("rue"),
                    rs.getString("ville"),
                    rs.getString("gouvernorat"),
                    rs.getInt("codePostal"),
                    rs.getString("pays"));
        }
        return adresse;
    }

}
