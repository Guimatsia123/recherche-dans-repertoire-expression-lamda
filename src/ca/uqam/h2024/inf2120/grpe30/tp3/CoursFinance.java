package ca.uqam.h2024.inf2120.grpe30.tp3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;


/**
 * UQAM - Hiver 2024 - INF2120 - Groupe 30 - TP3
 * 
 * CoursFinance : Cette classe modelise un cours de finance.
 * 
 * @author Ismael Doukoure
 * @version 3 avril 2024
 */
public class CoursFinance {

   //Les differents niveaux d'experiences requis pour suivre un cours
   public static final String[] NIVEAUX_EXPERIENCE = {
      "Junior", "Intermediaire", "Senior", "Tous"
   };

   //Attributs d'instance
   private int id;        
   private String titre;   
   private float prix;
   private int nbEtudiantsInscrits;
   private int nbEvaluations;
   private float evaluation;
   private String niveauExperience;
   private int duree;
   private LocalDateTime dateHeurePublication;
  
   
   

   /**
    * @param id le numero unique de ce cours
    * @param titre le titre de ce cours
    * @param prix le prix de ce cours
    * @param nbEtudiantsInscrits le nombre d'etudiants inscrits 
    * @param nbEvaluations le nombre d'evaluations
    * @param evaluation l'evaluation moyenne de ce cours
    * @param niveauExperience le niveau d'experience requis pour prendre ce cours
    * @param duree la duree de ce cours
    * @param dateHeurePublication la date et l'heure de publication de ce cours
    */
   public CoursFinance(int id, String titre, float prix, int nbEtudiantsInscrits, int nbEvaluations, float evaluation,
         String niveauExperience, int duree, LocalDateTime dateHeurePublication) throws CoursFinanceInvalideException {
      
    //Si l'un des parametres est invalide, leve une CoursFinanceInvalideException
      if (id <= 0 ||
            titre == null || titre.isEmpty() ||
            prix < 0 || nbEtudiantsInscrits < 0 || nbEvaluations < 0 ||evaluation < 0 || 
            niveauExperience == null || !existeValeurDansTab(NIVEAUX_EXPERIENCE, niveauExperience) ||
            duree <= 0 || dateHeurePublication == null) {
         
         throw new CoursFinanceInvalideException();
      }
      
      this.id = id;
      this.titre = titre;
      this.prix = prix;
      this.nbEtudiantsInscrits = nbEtudiantsInscrits;
      this.nbEvaluations = nbEvaluations;
      this.evaluation = evaluation;
      this.niveauExperience = niveauExperience;
      this.duree = duree;
      this.dateHeurePublication = dateHeurePublication;
   }

   
   /**
    * Permet d'obtenir l'id de ce cours.
    * @return l'id de ce cours.
    */
   public int getId() {
      return id;
   }

   /**
    * Permet d'obtenir le titre de ce cours.
    * @return le titre de ce cours. 
    */
   public String getTitre() {
      return titre;
   }

   /**
    * Permet d'obtenir la duree (en minutes) de ce cours.
    * @return la duree (en minutes) de ce cours.
    */
   public int getDuree() {
      return duree;
   }

   /**
    * Permet d'obtenir l'evaluation (moyenne) de ce cours.
    * @return l'evaluation (moyenne) de ce cours.
    */
   public double getEvaluation() {
      return evaluation;
   }
   
   

 
   /**
    * Permet d'obtenir le prix de ce cours.
    * @return le prix du cours
    */
   public float getPrix() {
      return prix;
   }


   /**
    * Permet d'obtenir le nombre d'etudiants inscrits.
    * @return le nombre d'etudiants inscrits
    */
   public int getNbEtudiantsInscrits() {
      return nbEtudiantsInscrits;
   }


   /**
    * Permet d'obtenir le nombre d'evaluations.
    * @return le nombre d'evaluations de ce cours
    */
   public int getNbEvaluations() {
      return nbEvaluations;
   }


   /**
    * Permet d'obtenir le niveau d'experience requis pour ce cours.
    * @return le niveau d'experience
    */
   public String getNiveauExperience() {
      return niveauExperience;
   }


   /**
    * Permet d'obtenir la date et l'heure de publication de ce cours.
    * @return la date et l'heure de publication de ce cours.
    */
   public LocalDateTime getDateHeurePublication() {
      return dateHeurePublication;
   }


   /**
    * Construit une representation sous forme de chaine de caracteres de 
    * ce cours, sur une ligne, ayant le format suivant :
    * 
    * id | titre | nb d'etudiants inscrits | niveau d'experience |  
    * [evaluation/nbEvaluations] | (duree min) | prix | date et heure de publication
    * 
    * @return une representation sous forme de chaine de caracteres de ce cours
    */
   @Override
   public String toString() {
      return String.format("%7d | ", id) 
              + String.format("%" + -65 + "s", titre)+ " | "
              + String.format("%" + -6 + "s", nbEtudiantsInscrits) + " | "
              + String.format("%" + -14 + "s", niveauExperience)
              + " | [" + String.format(Locale.ENGLISH, "%3.1f", evaluation) + "/" 
              + String.format("%" + -4 + "s", nbEvaluations) + "] | ("
              + String.format("%" + -4 + "s", duree) + " min) | "              
              + dateHeurePublication.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " | "
              + String.format(Locale.ENGLISH, "%3.2f$", prix);
   }
   
   /**
    * Deux cours sont consideres egaux si leurs attributs titre (sans tenir compte de la casse), 
    * dateHeurePublication, et duree sont egaux. Si obj est null, cette methode retourne false.
    * 
    * @param obj le cours avec lequel on veut comparer ce cours.
    * @return true si ce cours est egal a obj, false sinon.
    */
   @Override
   public boolean equals(Object obj) {
      CoursFinance autreCours;
      boolean egaux = this == obj;
      
      if (!egaux) {
         if (obj != null && getClass() == obj.getClass()) {
            autreCours = (CoursFinance)obj;
         
            egaux = Objects.equals(this.titre.toLowerCase(), 
                                   autreCours.titre.toLowerCase())
                  && Objects.equals(this.dateHeurePublication, autreCours.dateHeurePublication)
                  && Objects.equals(this.duree, autreCours.duree);
         }
      }
      return egaux;
   }
   
   /**
    * Construit et retourne un hashcode pour ce cours.  
    * Si deux cours c1 et c2 sont egaux (c1.equals(c2) retourne true), cette 
    * methode retourne le meme hashcode pour c1 et c2.
    * 
    * Note : 
    *    Cette methode est necessaire pour que l'utilisation de la methode 
    *    Stream.distinct() sur un stream de cours fonctionne.
    * 
    * @return un hashcode pour ce cours. 
    */
   @Override
   public int hashCode() {
      int hash = 17;
      hash = 31 * hash + Objects.hashCode(this.titre.toLowerCase());
      hash = 31 * hash + Objects.hashCode(this.dateHeurePublication);
      hash = 31 * hash + Objects.hashCode(this.duree);
         
      return hash;
   }

   
   /**
    * Teste si la valeur passee en parametre existe dans le tableau passe en 
    * parametre (sans tenir compte de la casse). 
    * 
    * @param tab le tableau dans lequel chercher la valeur donnee (sans tenir
    *            compte de la casse).
    *            ANT : tab est non null, et ne contient aucune valeur null.
    * @param valeur la valeur dont on veut savoir si elle appartient au tab donne
    * @return true si valeur est dans tab, false sinon.
    */
   private boolean existeValeurDansTab(String[] tab, String valeur) {
      int i = 0;
      boolean trouve = false;
      while (i < tab.length && !trouve) {
         trouve = tab[i].equalsIgnoreCase(valeur);
         i++;
      }
      return trouve;
   }

}