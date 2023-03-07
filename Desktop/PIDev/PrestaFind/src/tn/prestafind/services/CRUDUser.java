package tn.prestafind.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.prestafind.entities.User;
import tn.prestafind.utils.DBConnection;

public class CRUDUser implements InterfaceCRUDUser {

    Connection PrestaFindDB = DBConnection.getConnection();

    @Override
    public void ajouterUser(User user) throws SQLException {
        // Check if the email is already in use
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
        try (PreparedStatement pstmt = PrestaFindDB.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
//                    System.out.println("Email address already in use.");
//                    return;
                    throw new SQLException("Email address already in use.");
                }
            }
        }

        // generating a random string (salt)
        byte[] chars = new byte[7];
        new Random().nextBytes(chars);
        String salt = new String(chars, Charset.forName("US-ASCII"));

        PreparedStatement stmt = PrestaFindDB.prepareStatement("INSERT INTO utilisateurs(email, nom, prenom, pseudo, mdp, adresseId, role, dateNaissance, sexe, numTel, etat, URLPhotoProfil, salt, token) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getNom());
        stmt.setString(3, user.getPrenom());
        stmt.setString(4, user.getPseudo());
        stmt.setString(5, hashPassword(user.getMdp(), salt));
        stmt.setInt(6, user.getAdresseId());
        stmt.setString(7, user.getRole().toString());
        stmt.setDate(8, new java.sql.Date(user.getDateNaissance().getTimeInMillis()));
        stmt.setString(9, user.getSexe().toString());
        stmt.setLong(10, user.getNumTel());
        stmt.setString(11, user.getEtat().toString());
        stmt.setString(12, user.getURLPhotoProfil());
        stmt.setString(13, salt);
        stmt.setString(14, null);
        stmt.executeUpdate();
    }

    @Override
    public List<User> afficherUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Statement stmt = PrestaFindDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateurs");
        while (rs.next()) {
            User user = getUserFromResultSet(rs);
            System.out.println(user);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public void modifierUser(User user) throws SQLException {
        PreparedStatement stmt = PrestaFindDB.prepareStatement("UPDATE utilisateurs SET email=?, nom=?, prenom=?, pseudo=?, mdp=?, adresseId=?, role=?, dateNaissance=?, sexe=?, numTel=?, etat=?, URLPhotoProfil=?, salt=?, token=? WHERE id=?");
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getNom());
        stmt.setString(3, user.getPrenom());
        stmt.setString(4, user.getPseudo());
//        stmt.setString(5, hashPassword(user.getMdp(), user.getSalt()));
        stmt.setString(5, user.getMdp());
        stmt.setInt(6, user.getAdresseId());
        stmt.setString(7, user.getRole().toString());
        stmt.setDate(8, new Date(user.getDateNaissance().getTimeInMillis()));
        stmt.setString(9, user.getSexe().toString());
        stmt.setLong(10, user.getNumTel());
        stmt.setString(11, user.getEtat().toString());
        stmt.setString(12, user.getURLPhotoProfil());
        stmt.setString(13, user.getSalt());
        stmt.setString(14, user.getToken());
        stmt.setInt(15, user.getId());
        stmt.executeUpdate();
    }

    @Override
    public void supprimerUser(String email) throws SQLException {
        PreparedStatement stmt = PrestaFindDB.prepareStatement("DELETE FROM utilisateurs WHERE email=?");
        stmt.setString(1, email);
        stmt.executeUpdate();
    }

    public User getUserByEmail(String email) throws SQLException {
        PreparedStatement stmt = PrestaFindDB.prepareStatement("SELECT * FROM utilisateurs WHERE email=?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return getUserFromResultSet(rs);
        } else {
            return null;
        }
    }

    public User getUserById(int id) throws SQLException {
        PreparedStatement stmt = PrestaFindDB.prepareStatement("SELECT * FROM utilisateurs WHERE id=?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return getUserFromResultSet(rs);
        } else {
            return null;
        }
    }

    public String getSaltFromDB(String email) throws SQLException {
        PreparedStatement stmt = PrestaFindDB.prepareStatement("SELECT salt FROM utilisateurs WHERE email=?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("salt");
        } else {
            return null;
        }
    }

    public boolean login(String email, String password) throws SQLException {
        User user = getUserByEmail(email);
//        if (user != null && user.getEtat() != User.EtatUsr.ACTIF) {
        if (user != null) {
            String hashedPassword = hashPassword(password, user.getSalt());
            return hashedPassword.equals(user.getMdp());
        } else {
            return false;
        }
    }

    public String setToken(String email) throws SQLException {
        User user = getUserByEmail(email);
        if (user != null && user.getEtat() != User.EtatUsr.ACTIF) {
            String token = generateToken();
            user.setToken(token);
            user.setEtat(User.EtatUsr.INACTIF);
            modifierUser(user);
            return token;
        } else {
            return null;
        }
    }

    public String getToken(String email) throws SQLException {
        User user = getUserByEmail(email);
        if (user != null) {
            return user.getToken();
        } else {
            return null;
        }
    }

    public void logout(String email) throws SQLException {
        User user = getUserByEmail(email);
        if (user != null) {
            user.setToken(null);
            modifierUser(user);
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setPseudo(rs.getString("pseudo"));
        user.setMdp(rs.getString("mdp"));
        user.setAdresseId(rs.getInt("adresseId"));
        user.setRole(User.Role.valueOf(rs.getString("role")));
        Calendar dateNaissance = Calendar.getInstance();
        dateNaissance.setTime(rs.getDate("dateNaissance"));
        user.setDateNaissance(dateNaissance);
        user.setSexe(User.Sexe.valueOf(rs.getString("sexe")));
        user.setNumTel(rs.getLong("numTel"));
        user.setEtat(User.EtatUsr.valueOf(rs.getString("etat")));
        user.setURLPhotoProfil(rs.getString("URLPhotoProfil"));
        user.setSalt(rs.getString("salt"));
        user.setToken(rs.getString("token"));
        return user;
    }

    private String hashPassword(String password, String salt) {
        // Implement a secure password hashing algorithm, e.g. bcrypt
        // simple SHA-256 hash
        String saltedPassword = salt + password;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not hash password", e);
        }
    }

    private String generateToken() {
        // Generate a secure random token (simple UUID)
        return UUID.randomUUID().toString();
    }

    public static int nbrUsrers() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Users";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDUser.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
}
