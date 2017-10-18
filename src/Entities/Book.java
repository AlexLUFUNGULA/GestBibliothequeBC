/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex LUFUNGULA
 */
@Entity
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
    , @NamedQuery(name = "Book.findByIdBook", query = "SELECT b FROM Book b WHERE b.idBook = :idBook")
    , @NamedQuery(name = "Book.findByNameBook", query = "SELECT b FROM Book b WHERE b.nameBook = :nameBook")
    , @NamedQuery(name = "Book.findByNameBookLIKE", query = "SELECT b FROM Book b WHERE b.nameBook LIKE :nameBookLIKE")
    , @NamedQuery(name = "Book.findByAuthorBook", query = "SELECT b FROM Book b WHERE b.authorBook = :authorBook")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_book")
    private Integer idBook;
    @Basic(optional = false)
    @Column(name = "name_book")
    private String nameBook;
    @Basic(optional = false)
    @Column(name = "author_book")
    private String authorBook;

    public Book() {
    }

    public Book(Integer idBook) {
        this.idBook = idBook;
    }

    public Book(Integer idBook, String nameBook, String authorBook) {
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.authorBook = authorBook;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBook != null ? idBook.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.idBook == null && other.idBook != null) || (this.idBook != null && !this.idBook.equals(other.idBook))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Book[ idBook=" + idBook + " ]";
    }
    
}
