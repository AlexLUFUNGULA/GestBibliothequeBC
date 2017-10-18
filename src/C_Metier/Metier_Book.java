/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package C_Metier;

import Entities.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alex LUFUNGULA
 */
public class Metier_Book {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryManager_1.0PU");
    private final EntityManager em = emf.createEntityManager();

    //
    public void afficheBookDemarage(JFrame frameM, JTable tableB) {
        //
        try {
            Query req = em.createNamedQuery("Book.findAll");
            String[][] tabRet = tabOrdonnE(req);
            //
            tableB.setModel(new javax.swing.table.DefaultTableModel(
                    tabRet,
                    new String[]{
                        "Title of the book", "Name of the author", "Number order"
                    }
            ));
            //
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frameM, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //
    public void afficheBookLIKE(JFrame frameM, JTable tableB, JTextField tfdRech) {
        //
        try {
            Query req = em.createNamedQuery("Book.findByNameBookLIKE");
            req.setParameter("nameBookLIKE", "%" + tfdRech.getText() + "%");
            String[][] tabRet = tabOrdonnE(req);
            //
            tableB.setModel(new javax.swing.table.DefaultTableModel(
                    tabRet,
                    new String[]{
                        "Title of the book", "Name of the author", "Number order"
                    }
            ));
            //
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frameM, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editBook(JTable tabBooks, JFrame frameM, JTextField jTfBook, JTextField jTfAuthor) {
        //
        Book bkNew = new Book();
        int rewI = tabBooks.getSelectedRow();
        int IdBks = Integer.parseInt(tabBooks.getValueAt(rewI, 2).toString());
        //
        bkNew.setIdBook(IdBks);
        bkNew.setNameBook(jTfBook.getText());
        bkNew.setAuthorBook(jTfAuthor.getText());
        //
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.merge(bkNew);
            et.commit();
            //
            JOptionPane.showMessageDialog(frameM, "Great, you have UPDATED one book", "Success", JOptionPane.INFORMATION_MESSAGE);
            //
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frameM, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Transfert les données du tableau vers les TxtFld
    public void formModBook(JTable tabBooks, JTextField jTfBook, JTextField jTfAuthor, JFrame frameM) {
        //
        int rewI = tabBooks.getSelectedRow();
        //
        jTfBook.setText(tabBooks.getValueAt(rewI, 0).toString());
        jTfAuthor.setText(tabBooks.getValueAt(rewI, 1).toString());
        //
    }

    public void suppressionB(JTable tabBooks, JFrame frameM, JTextField jTfBook, JTextField jTfAuthor) {
        //On doit supprimer par le nom mais il est probable d'avoir plusieurs livre avec un seule nom, cela revien à supprimer selon l'ID
        Book bkNew = new Book();
        int rewI = tabBooks.getSelectedRow();
        int IdBks = Integer.parseInt(tabBooks.getValueAt(rewI, 2).toString());
        //
        bkNew.setIdBook(IdBks);
        bkNew.setNameBook(jTfBook.getText());
        bkNew.setAuthorBook(jTfAuthor.getText());
        //
        try {
            if (FindBook(bkNew.getIdBook())) {
                EntityTransaction et = em.getTransaction();
                et.begin();
                if (!em.contains(bkNew)) {
                    bkNew = em.merge(bkNew);
                }
                em.remove(bkNew);
                et.commit();
            }
            JOptionPane.showMessageDialog(frameM, "Great, you have removed one book", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frameM, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Par conséquand cela revient egalement à trouver par l'ID
    private boolean FindBook(int IdBook) {
        //
        boolean testFBk = false;
        //
        Query req = em.createNamedQuery("Book.findByIdBook");
        req.setParameter("idBook", IdBook);
        //
        List<Book> bList = req.getResultList();
        if (bList.size() > 0) {
            testFBk = true;
        }
        //
        return testFBk;
    }

    public void insertB(Book newBook, JFrame frameM) {
        //
        try {
            //
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(newBook);
            et.commit();
            //
            JOptionPane.showMessageDialog(frameM, "Great, you have saved a new book", "Success", JOptionPane.INFORMATION_MESSAGE);
            //
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frameM, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        //
    }

    private String[][] tabOrdonnE(Query req) {
        String[][] tabRet;
        int nElt;
        int comparisonResult = 0;
        //
        //Query req = em.createNamedQuery("Book.findAll");
        List<Book> bList = req.getResultList();
        nElt = bList.size();
        tabRet = new String[nElt][3];
        String stgTampB = "";
        String stgTampA = "";
        String idBkStrg = "";
        //Recupere les elts de la BD et les met dans un tableau
        for (int i = 0; i < bList.size(); i++) {
            tabRet[i][0] = bList.get(i).getNameBook();
            tabRet[i][1] = bList.get(i).getAuthorBook();
            tabRet[i][2] = bList.get(i).getIdBook().toString();
        }
        //Ordonne les elts du tableau
        for (int i = 1; i < tabRet.length; i++) {
            //
            for (int j = i; j < tabRet.length; j++) {
                comparisonResult = tabRet[j - 1][0].compareTo(tabRet[j][0]);
                //
                if (comparisonResult > 0) {
                    stgTampB = tabRet[j][0];
                    stgTampA = tabRet[j][1];
                    idBkStrg = tabRet[j][2];
                    //
                    tabRet[j][0] = tabRet[j - 1][0];
                    tabRet[j][1] = tabRet[j - 1][1];
                    tabRet[j][2] = tabRet[j - 1][2];
                    //
                    tabRet[j - 1][0] = stgTampB;
                    tabRet[j - 1][1] = stgTampA;
                    tabRet[j - 1][2] = idBkStrg;
                }
                //
            }
        }
        //
        return tabRet;
    }
}
