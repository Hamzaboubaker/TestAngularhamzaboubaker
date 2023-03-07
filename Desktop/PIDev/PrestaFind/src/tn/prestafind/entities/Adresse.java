package tn.prestafind.entities;

import java.util.Objects;

public class Adresse {

    private int id, numBatiment, codePostal;
    String rue, ville, gouvernorat, pays;

    public Adresse() {
    }

    public Adresse(int id, int numBatiment, String rue, String ville, String gouvernorat, int codePostal, String pays) {
        this.id = id;
        this.numBatiment = numBatiment;
        this.codePostal = codePostal;
        this.rue = rue;
        this.ville = ville;
        this.gouvernorat = gouvernorat;
        this.pays = pays;
    }

    public Adresse(int numBatiment, String rue, String ville, String gouvernorat, int codePostal, String pays) {
        this.numBatiment = numBatiment;
        this.codePostal = codePostal;
        this.rue = rue;
        this.ville = ville;
        this.gouvernorat = gouvernorat;
        this.pays = pays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumBatiment() {
        return numBatiment;
    }

    public void setNumBatiment(int numBatiment) {
        this.numBatiment = numBatiment;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Adresse{" + "id=" + id + ", numBatiment=" + numBatiment + ", codePostal=" + codePostal + ", rue=" + rue + ", ville=" + ville + ", gouvernorat=" + gouvernorat + ", pays=" + pays + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
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
        final Adresse other = (Adresse) obj;
        if (this.numBatiment != other.numBatiment) {
            return false;
        }
        if (!Objects.equals(this.rue, other.rue)) {
            return false;
        }
        if (!Objects.equals(this.ville, other.ville)) {
            return false;
        }
        if (!Objects.equals(this.gouvernorat, other.gouvernorat)) {
            return false;
        }
        return Objects.equals(this.pays, other.pays);
    }

}
