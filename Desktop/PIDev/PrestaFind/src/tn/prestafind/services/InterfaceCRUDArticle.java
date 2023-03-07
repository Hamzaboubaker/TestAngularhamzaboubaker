package tn.prestafind.services;

import tn.prestafind.entities.Article;
import java.util.List;
import java.sql.SQLException;

public interface InterfaceCRUDArticle {

    public void ajouterArticle(Article article) throws SQLException;

    public void modifierArticle(Article article) throws SQLException;

    public void supprimerArticle(int id) throws SQLException;

    public List<Article> getArticlesMax10() throws SQLException;
}
