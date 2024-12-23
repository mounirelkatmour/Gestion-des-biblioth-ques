import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Classe Livre
class Livre {
    private static int compteur = 1;
    private int id;
    private String titre;
    private String auteur;
    private String categorie;

    public Livre(String titre, String auteur, String categorie) {
        this.id = compteur++;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    @Override
    public String toString() {
        return titre;
    }
}

// Classe Membre
class Membre {
    private static int compteur = 1;
    private int id;
    private String nom;
    private String adresse;
    private String telephone;

    public Membre(String nom, String adresse, String telephone) {
        this.id = compteur++;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}

// Classe Emprunt
class Emprunt {
    private Membre membre;
    private Livre livre;
    private Date dateEmprunt;
    private Date dateRetour;

    public Emprunt(Membre membre, Livre livre, Date dateEmprunt, Date dateRetour) {
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Membre getMembre() {
        return membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public Date getDateRetour() {
        return dateRetour;
    }
}

// Classe principale
public class BibliothequeApp extends JFrame {
    private List<Livre> livres = new ArrayList<>();
    private List<Membre> membres = new ArrayList<>();
    private List<Emprunt> emprunts = new ArrayList<>();

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JTable empruntsTable;
    private JScrollPane empruntsScrollPane;

    // ComboBoxes
    private JComboBox<Membre> cmbMembres;
    private JComboBox<Livre> cmbLivres;

    public BibliothequeApp() {
        setTitle("Gestion de Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barre de navigation
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navBar.setBackground(new Color(50, 50, 150));
        navBar.setPreferredSize(new Dimension(800, 50));

        JButton btnAccueil = new JButton("Accueil");
        JButton btnAjouterMembre = new JButton("Ajouter un Membre");
        JButton btnAjouterLivre = new JButton("Ajouter un Livre");
        JButton btnAjouterEmprunt = new JButton("Ajouter un Emprunt");
        JButton btnAfficherEmprunts = new JButton("Afficher les Emprunts");
        JButton btnQuitter = new JButton("Quitter");

        btnAccueil.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));
        btnAjouterMembre.addActionListener(e -> cardLayout.show(mainPanel, "AjouterMembre"));
        btnAjouterLivre.addActionListener(e -> cardLayout.show(mainPanel, "AjouterLivre"));
        btnAjouterEmprunt.addActionListener(e -> cardLayout.show(mainPanel, "AjouterEmprunt"));
        btnAfficherEmprunts.addActionListener(e -> {
            updateEmpruntsTable();
            cardLayout.show(mainPanel, "AfficherEmprunts");
        });
        btnQuitter.addActionListener(e -> System.exit(0));

        navBar.add(btnAccueil);
        navBar.add(btnAjouterMembre);
        navBar.add(btnAjouterLivre);
        navBar.add(btnAjouterEmprunt);
        navBar.add(btnAfficherEmprunts);
        navBar.add(btnQuitter);

        add(navBar, BorderLayout.NORTH);

        // Pages principales
        mainPanel.add(createAccueilPanel(), "Accueil");
        mainPanel.add(createAjouterMembrePanel(), "AjouterMembre");
        mainPanel.add(createAjouterLivrePanel(), "AjouterLivre");
        mainPanel.add(createAjouterEmpruntPanel(), "AjouterEmprunt");
        mainPanel.add(createAfficherEmpruntsPanel(), "AfficherEmprunts");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "Accueil");
    }

    private JPanel createAccueilPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 220, 255));

        JLabel title = new JLabel("<html><h1>Bienvenue dans la gestion de bibliothèque</h1></html>", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JLabel description = new JLabel(
            "<html><p style='text-align: center;'>"
            + "Cette application permet de gérer les livres, les membres, "
            + "et leurs emprunts.</p></html>",
            JLabel.CENTER
        );
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(description, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAjouterMembrePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(240, 250, 240));

        JTextField txtNom = new JTextField();
        JTextField txtAdresse = new JTextField();
        JTextField txtTelephone = new JTextField();
        JButton btnAjouter = new JButton("Ajouter");

        panel.add(new JLabel("Nom du membre :"));
        panel.add(txtNom);
        panel.add(new JLabel("Adresse :"));
        panel.add(txtAdresse);
        panel.add(new JLabel("Téléphone :"));
        panel.add(txtTelephone);
        panel.add(new JLabel("")); // Placeholder
        panel.add(btnAjouter);

        btnAjouter.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            String adresse = txtAdresse.getText().trim();
            String telephone = txtTelephone.getText().trim();
        
            if (nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (!telephone.matches("0\\d{9}")) {
                JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit commencer par 0 et contenir exactement 10 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            Membre membre = new Membre(nom, adresse, telephone);
            membres.add(membre);
            cmbMembres.addItem(membre);
            JOptionPane.showMessageDialog(this, "Membre ajouté avec succès !");
            txtNom.setText("");
            txtAdresse.setText("");
            txtTelephone.setText("");
            cardLayout.show(mainPanel, "Accueil");
        });

        return panel;
    }

    private JPanel createAjouterLivrePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(250, 240, 230));
    
        JTextField txtTitre = new JTextField();
        JTextField txtAuteur = new JTextField();
        String[] categories = {
            "Roman", "Science-fiction", "Fantasy", "Biographie", 
            "Histoire", "Informatique", "Philosophie", 
            "Psychologie", "Art", "Autre"
        };
        JComboBox<String> cmbCategorie = new JComboBox<>(categories);
        JButton btnAjouter = new JButton("Ajouter");
    
        panel.add(new JLabel("Titre du livre :"));
        panel.add(txtTitre);
        panel.add(new JLabel("Auteur :"));
        panel.add(txtAuteur);
        panel.add(new JLabel("Catégorie :"));
        panel.add(cmbCategorie);
        panel.add(new JLabel(""));
        panel.add(btnAjouter);
    
        btnAjouter.addActionListener(e -> {
            String titre = txtTitre.getText().trim();
            String auteur = txtAuteur.getText().trim();
            String categorie = (String) cmbCategorie.getSelectedItem();
        
            if (titre.isEmpty() || auteur.isEmpty() || categorie.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            Livre livre = new Livre(titre, auteur, categorie);
            livres.add(livre);
            cmbLivres.addItem(livre);
            JOptionPane.showMessageDialog(this, "Livre ajouté avec succès !");
            txtTitre.setText("");
            txtAuteur.setText("");
            cmbCategorie.setSelectedIndex(0);
            cardLayout.show(mainPanel, "Accueil");
        });
        
        return panel;
    }    

    private JPanel createAjouterEmpruntPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(230, 250, 250));
    
        cmbMembres = new JComboBox<>(membres.toArray(new Membre[0]));
        cmbLivres = new JComboBox<>(livres.toArray(new Livre[0]));
    
        JTextField txtDateEmprunt = new JTextField("YYYY-MM-DD");
        JTextField txtDateRetour = new JTextField("YYYY-MM-DD");
    
        JButton btnAjouter = new JButton("Ajouter");
    
        panel.add(new JLabel("Sélectionnez un membre :"));
        panel.add(cmbMembres);
        panel.add(new JLabel("Sélectionnez un livre :"));
        panel.add(cmbLivres);
        panel.add(new JLabel("Date d'emprunt (YYYY-MM-DD) :"));
        panel.add(txtDateEmprunt);
        panel.add(new JLabel("Date de retour (YYYY-MM-DD) :"));
        panel.add(txtDateRetour);
        panel.add(new JLabel(""));
        panel.add(btnAjouter);
    
        btnAjouter.addActionListener(e -> {
            Membre membre = (Membre) cmbMembres.getSelectedItem();
            Livre livre = (Livre) cmbLivres.getSelectedItem();
            String dateEmpruntStr = txtDateEmprunt.getText().trim();
            String dateRetourStr = txtDateRetour.getText().trim();
        
            if (membre == null || livre == null || dateEmpruntStr == null || dateRetourStr == null) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                Date dateEmprunt = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateEmpruntStr);
                Date dateRetour = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateRetourStr);
        
                Date aujourdhui = new Date();
                if (dateEmprunt.before(aujourdhui)) {
                    JOptionPane.showMessageDialog(this, "La date d'emprunt ne peut pas être dans le passé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                if (!dateRetour.after(dateEmprunt)) {
                    JOptionPane.showMessageDialog(this, "La date de retour doit être strictement après la date d'emprunt.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                emprunts.add(new Emprunt(membre, livre, dateEmprunt, dateRetour));
                JOptionPane.showMessageDialog(this, "Emprunt ajouté avec succès !");
                txtDateEmprunt.setText("YYYY-MM-DD");
                txtDateRetour.setText("YYYY-MM-DD");
                cardLayout.show(mainPanel, "Accueil");
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(this, "Les dates doivent être au format yyyy-MM-dd.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }

    private JPanel createAfficherEmpruntsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 240, 255));

        String[] columnNames = {"Membre", "Livre", "Date d'Emprunt", "Date de Retour"};
        empruntsTable = new JTable(new String[0][4], columnNames);
        empruntsScrollPane = new JScrollPane(empruntsTable);
        panel.add(empruntsScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void updateEmpruntsTable() {
        String[] columnNames = {"Membre", "Livre", "Date d'Emprunt", "Date de Retour"};
        String[][] data = new String[emprunts.size()][4];
        for (int i = 0; i < emprunts.size(); i++) {
            Emprunt emprunt = emprunts.get(i);
            data[i][0] = emprunt.getMembre().getNom();
            data[i][1] = emprunt.getLivre().getTitre();
            data[i][2] = emprunt.getDateEmprunt().toString();
            data[i][3] = emprunt.getDateRetour().toString();
        }
        empruntsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BibliothequeApp().setVisible(true));
    }
}

