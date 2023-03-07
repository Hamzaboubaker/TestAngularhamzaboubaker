package tn.prestafind.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Article {

    private int id, nbrVues, nbrPartages, nbrLikes, nbrDislikes, nbrRapports, auteurId;
    private String titre, contenu;
    private LocalDateTime datePublication, dateModification;
    private boolean retireParModerateur;
    private List<Tag> tags;

    public Article() {
    }

    public Article(int id, int auteurId, String titre, String contenu, LocalDateTime datePublication, LocalDateTime dateModification, int nbrVues, int nbrPartages, int nbrLikes, int nbrDislikes, int nbrRapports, boolean retireParModerateur, List<Tag> tags) {
        this.id = id;
        this.auteurId = auteurId;
        this.nbrVues = nbrVues;
        this.nbrPartages = nbrPartages;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrRapports = nbrRapports;
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.dateModification = dateModification;
        this.retireParModerateur = retireParModerateur;
        this.tags = tags;
    }

    public Article(int auteurId, String titre, String contenu, LocalDateTime datePublication, LocalDateTime dateModification, int nbrVues, int nbrPartages, int nbrLikes, int nbrDislikes, int nbrRapports, boolean retireParModerateur, List<Tag> tags) {
        this.auteurId = auteurId;
        this.nbrVues = nbrVues;
        this.nbrPartages = nbrPartages;
        this.nbrLikes = nbrLikes;
        this.nbrDislikes = nbrDislikes;
        this.nbrRapports = nbrRapports;
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.datePublication = dateModification;
        this.retireParModerateur = retireParModerateur;
        this.tags = tags;
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

    public void setAuteurId(int auteur) {
        this.auteurId = auteur;
    }

    public int getNbrVues() {
        return nbrVues;
    }

    public void setNbrVues(int nbrVues) {
        this.nbrVues = nbrVues;
    }

    public int getNbrPartages() {
        return nbrPartages;
    }

    public void setNbrPartages(int nbrPartages) {
        this.nbrPartages = nbrPartages;
    }

    public int getNbrLikes() {
        return nbrLikes;
    }

    public void setNbrLikes(int nbrLikes) {
        this.nbrLikes = nbrLikes;
    }

    public int getNbrDislikes() {
        return nbrDislikes;
    }

    public void setNbrDislikes(int nbrDislikes) {
        this.nbrDislikes = nbrDislikes;
    }

    public int getNbrRapports() {
        return nbrRapports;
    }

    public void setNbrRapports(int nbrRapports) {
        this.nbrRapports = nbrRapports;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public boolean isRetireParModerateur() {
        return retireParModerateur;
    }

    public void setRetireParModerateur(boolean retireParModerateur) {
        this.retireParModerateur = retireParModerateur;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", titre=" + titre + ", contenu=" + contenu + ", datePublication=" + datePublication + ", dateModification=" + dateModification + ", nbrVues=" + nbrVues + ", nbrPartages=" + nbrPartages + ", nbrLikes=" + nbrLikes + ", nbrDislikes=" + nbrDislikes + ", nbrRapports=" + nbrRapports + ", Auteur=" + auteurId + ", retireParModerateur=" + retireParModerateur + '}' + "\n";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
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
        final Article other = (Article) obj;
        return this.id == other.id;
    }

}
