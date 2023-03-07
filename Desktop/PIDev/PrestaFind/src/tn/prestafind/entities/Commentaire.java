package tn.prestafind.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Commentaire {

    String contenu;
    int id, auteurId, nbrLikes, nbrDislikes, nbrSignals;
    LocalDateTime datePublication;
    boolean retireParModrateur;
  String image;
    public Commentaire() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Commentaire(String contenu, int auteurId, int nbrLikes, int nbrDislikes, int nbrSignals, LocalDateTime datePublication, boolean retireParModrateur, String image) {
        this.contenu = contenu;
        this.auteurId = auteurId;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrSignals = nbrSignals;
        this.datePublication = datePublication;
        this.retireParModrateur = retireParModrateur;
        this.image = image;
    }

    public Commentaire(String contenu, int id, int auteurId, int nbrLikes, int nbrDislikes, int nbrSignals, LocalDateTime datePublication, boolean retireParModrateur, String image) {
        this.contenu = contenu;
        this.id = id;
        this.auteurId = auteurId;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrSignals = nbrSignals;
        this.datePublication = datePublication;
        this.retireParModrateur = retireParModrateur;
        this.image = image;
    }

    public Commentaire(int id, int auteurId, String contenu, int nbrLikes, int nbrDislikes, int nbrSignals, LocalDateTime datePublication, boolean retireParModrateur) {
        this.id = id;
        this.auteurId = auteurId;
        this.contenu = contenu;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrSignals = nbrSignals;
        this.datePublication = datePublication;
        this.retireParModrateur = retireParModrateur;
    }

    public Commentaire(int auteurId, String contenu, int nbrLikes, int nbrDislikes, int nbrSignals, LocalDateTime datePublication, boolean retireParModrateur) {
        this.auteurId = auteurId;
        this.contenu = contenu;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrSignals = nbrSignals;
        this.datePublication = datePublication;
        this.retireParModrateur = retireParModrateur;
    }

    public Commentaire(String contenu, int id, int i, int i0, int i1, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getNbLikes() {
        return nbrLikes;
    }

    public void setNbLikes(int nbrLikes) {
        this.nbrLikes = nbrLikes;
    }

    public int getNbDisLikes() {
        return nbrDislikes;
    }

    public void setNbDisLikes(int nbrDislikes) {
        this.nbrDislikes = nbrDislikes;
    }

    public int getNbSignals() {
        return nbrSignals;
    }

    public void setNbSignals(int nbrSignals) {
        this.nbrSignals = nbrSignals;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public boolean isRetireParModrateur() {
        return retireParModrateur;
    }

    public void setRetireParModrateur(boolean retireParModrateur) {
        this.retireParModrateur = retireParModrateur;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.auteurId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commentaire other = (Commentaire) obj;
        if (!Objects.equals(this.auteurId, other.auteurId)) {
            return false;
        }
        return Objects.equals(this.contenu, other.contenu);
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", auteur=" + auteurId + ", contenu=" + contenu + ", nbrLikes=" + nbrLikes + ", nbrDislikes=" + nbrDislikes + ", nbrSignals=" + nbrSignals + ", datePublication=" + datePublication + ", retireParModrateur=" + retireParModrateur + '}';
    }

    public Commentaire(String contenu, int auteurId, int nbrLikes, int nbrDislikes, int nbrSignals, LocalDateTime datePublication, boolean retireParModrateur) {
        this.contenu = contenu;
        this.auteurId = auteurId;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrSignals = nbrSignals;
        this.datePublication = datePublication;
        this.retireParModrateur = retireParModrateur;
    }
    
    
    

}
