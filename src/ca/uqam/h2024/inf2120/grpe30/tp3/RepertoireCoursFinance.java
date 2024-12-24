package ca.uqam.h2024.inf2120.grpe30.tp3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepertoireCoursFinance {
	
	 List<CoursFinance> listeDesCours;
	
	 
	 public RepertoireCoursFinance(String cheminFichier) {
		 //TO DO penser a gerer les cas ou le chemin du fichier n'est pas valide.
		 
		Path chemin = Paths.get(cheminFichier);
		String caracteresPremiereligne= "ID;Titre;Prix;Nb. D'etudiant(e)s;Nb. d'evaluations;Evaluation;Niveaux d'experience2;Duree;Date et l'heure de publication";
		listeDesCours = new ArrayList<>();
		try (Stream<String> lignes = Files.lines(chemin)){
			
			lignes.filter(ligne-> !ligne.contains(caracteresPremiereligne))
				  .map(ligne -> ligne.split(";"))
				  .forEach(coursFinances -> {
					  if(coursFinances.length==9) {
						  try {
							  int id = Integer.parseInt(coursFinances[0]);
							  String titre = coursFinances[1].trim();
							  float prix = Float.parseFloat(coursFinances[2]);
							  int nbEtudiants = Integer.parseInt(coursFinances[3]);
							  int nbEvaluations = Integer.parseInt(coursFinances[4]);
							  float evaluation = Float.parseFloat(coursFinances[5]);
							  String experience = coursFinances[6].trim();
							  int duree = Integer.parseInt(coursFinances[7]);
							  
							  String dateEnString = coursFinances[8].trim();
							  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							  LocalDateTime dateTime = LocalDateTime.parse(dateEnString, formatter);
							  
							  try{
								  CoursFinance cours = new CoursFinance(id,titre,prix,nbEtudiants,nbEvaluations,evaluation,experience,duree,dateTime);
								  listeDesCours.add(cours);
								  
							  }catch(CoursFinanceInvalideException e) {
								  System.out.println("Erreur de creation du cours finance . ");
								  
							  }

						  }catch(NumberFormatException e) {
							  
						  }
					  }
					  
				  }
				  
						  				 
						  );	  
			
		} catch (IOException e) {
		System.out.println("Erreur d'entrée / sortie");
		 
	 }

}
	 
	public int obtenirNbCours() {
		return (int)listeDesCours.stream().count();
	}
	
	public List<CoursFinance> rechercherParTitre(String chaineCaracteres){
		
		List<CoursFinance> listeCoursParTitre = new ArrayList<>();

		if(chaineCaracteres != null && !chaineCaracteres.trim().isEmpty() ) {
			 listeCoursParTitre = listeDesCours.stream()
											   .filter(cours->cours.getTitre().toLowerCase().contains(chaineCaracteres.trim().toLowerCase()))
											   .sorted((cours1,cours2)->cours1.getTitre().compareToIgnoreCase(cours2.getTitre()))
											   .collect(Collectors.toList());			
		}

		return listeCoursParTitre;
	}
	
	public List<CoursFinance> rechercherParEvaluation(float eval, boolean plusPetite){
		
			List<CoursFinance> listeCoursParEvaluation= new ArrayList<>();
		
			Comparator<CoursFinance> trierParEvaluation = Comparator.comparingDouble(cours->cours.getEvaluation());
			Predicate<CoursFinance> appliquerFiltre = cours->  plusPetite ? (float)cours.getEvaluation()<eval:(float)cours.getEvaluation()>=eval;
			
			listeCoursParEvaluation = listeDesCours.stream()
							                       .filter(appliquerFiltre)
							                       .sorted(trierParEvaluation)   
							                       .sorted((cours1,cours2)->{
							                    	   int val=0;
							                    	   if(cours1.getEvaluation() == cours2.getEvaluation())
							                    		  val= cours1.getNiveauExperience().compareToIgnoreCase(cours2.getNiveauExperience());
							                    	   else if((cours1.getEvaluation() == cours2.getEvaluation()) && cours1.getNiveauExperience().equalsIgnoreCase(cours2.getNiveauExperience()))
							                    		   val= Integer.compare(cours1.getId(), cours2.getId());
													return val;
							                       })
							                       .collect(Collectors.toList());							                       
		
		return listeCoursParEvaluation;
	}

	
	public List<CoursFinance> rechercherParEvaluation2(float eval, boolean plusPetite){
		
		List<CoursFinance> listeCoursParEvaluation= new ArrayList<>();

		Predicate<CoursFinance> appliquerFiltre = cours->  plusPetite ? (float)cours.getEvaluation()<eval:(float)cours.getEvaluation()>=eval;
		
		listeCoursParEvaluation = listeDesCours.stream()
						                       .filter(appliquerFiltre)
						                       //.sorted(trierParEvaluation)   
						                       .sorted((cours1,cours2)->{
						                    	   int val=0;
						                    	   if(cours1.getEvaluation() == cours2.getEvaluation())
						                    		  val= cours1.getNiveauExperience().compareToIgnoreCase(cours2.getNiveauExperience());
						                    	   else if((cours1.getEvaluation() == cours2.getEvaluation()) && cours1.getNiveauExperience().equalsIgnoreCase(cours2.getNiveauExperience()))
						                    		   val= Integer.compare(cours1.getId(), cours2.getId());
												return val;
						                       })
						                       .collect(Collectors.toList());							                       
	
	return listeCoursParEvaluation;
}
	
	
	public List<CoursFinance> rechercherParNiveauExperience(List<String> niveauxExperience){
		List<CoursFinance> listeCoursParNiveauExperience= new ArrayList<>();
		
		if(niveauxExperience != null && !niveauxExperience.isEmpty() ) {
			Comparator<CoursFinance> trierParTitreId = Comparator.comparing((CoursFinance cours)->cours.getTitre())
					.thenComparingInt((CoursFinance cours)->cours.getId());
			
			Predicate<CoursFinance> appliquerFiltre = cours-> niveauxExperience.contains(cours.getNiveauExperience().toLowerCase());
			
			listeCoursParNiveauExperience = listeDesCours.stream()
						                                 .filter(appliquerFiltre)
						                                 .sorted(trierParTitreId)
						                                 .collect(Collectors.toList());
			
		}
		
		return listeCoursParNiveauExperience; 
		
	} 

	 
}
