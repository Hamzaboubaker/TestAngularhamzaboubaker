package tn.prestafind.entities;

import java.util.Calendar;

public class User {

    int id;
    protected String email;
    protected String nom;
    protected String prenom;
    protected String pseudo;
    protected String mdp;
    protected int adresseId;
    protected Role role;
    protected Calendar dateNaissance;
    protected Sexe sexe;
    protected long numTel;
    protected EtatUsr etat;
    protected String URLPhotoProfil;
    protected String salt;
    protected String token;

    public User() {
    }

    public User(int id, String email, String nom, String prenom, String pseudo, String mdp, int adresseId, Role role, Calendar dateNaissance, Sexe sexe, long numTel, EtatUsr etat, String URLPhotoProfil) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.adresseId = adresseId;
        this.role = role;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.numTel = numTel;
        this.etat = etat;
        this.URLPhotoProfil = URLPhotoProfil;
    }

    public User(String email, String nom, String prenom, String pseudo, String mdp, int adresseId, Role role, Calendar dateNaissance, Sexe sexe, long numTel, EtatUsr etat, String URLPhotoProfil) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.adresseId = adresseId;
        this.role = role;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.numTel = numTel;
        this.etat = etat;
        this.URLPhotoProfil = URLPhotoProfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public int getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(int adresseId) {
        this.adresseId = adresseId;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Calendar getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Calendar dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public long getNumTel() {
        return numTel;
    }

    public void setNumTel(long numTel) {
        this.numTel = numTel;
    }

    public EtatUsr getEtat() {
        return etat;
    }

    public void setEtat(EtatUsr etat) {
        this.etat = etat;
    }

    public String getURLPhotoProfil() {
        return URLPhotoProfil;
    }

    public void setURLPhotoProfil(String URLPhotoProfil) {
        this.URLPhotoProfil = URLPhotoProfil;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{"
                + "email='" + email + '\''
                + ", nom='" + nom + '\''
                + ", prenom='" + prenom + '\''
                + ", pseudo='" + pseudo + '\''
                + ", mdp='" + mdp + '\''
                + ", adresseId='" + adresseId + '\''
                + ", role=" + role
                + ", dateNaissance=" + dateNaissance
                + ", sexe=" + sexe
                + ", numTel=" + numTel
                + ", etat=" + etat
                + ", URLPhotoProfil='" + URLPhotoProfil + '\''
                + ", salt='" + salt + '\''
                + ", token='" + token + '\''
                + '}';
    }

    public enum Role {
        ADMIN,
        USER,
        MOD
    }

    public enum Sexe {
        HOMME,
        FEMME
    }

    public enum EtatUsr {
        ACTIF,
        INACTIF,
        BLOQUE,
        NONBLOQUE,
        ENATTENTECONFIRMATION
    }
}
