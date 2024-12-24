package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ca.uqam.h2024.inf2120.grpe30.tp3.CoursFinance;
import ca.uqam.h2024.inf2120.grpe30.tp3.RepertoireCoursFinance;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RepertoireCoursFinance cours = new RepertoireCoursFinance("repertoireCoursFinances.csv");
		//System.out.println(cours.getListeCours());
		
		//System.out.println(cours.obtenirNbCours());
		
		List<CoursFinance> listeCoursParTitre = new ArrayList<>();
		
		listeCoursParTitre = cours.rechercherParEvaluation(5, true);
		System.out.println(listeCoursParTitre.size());
		
		listeCoursParTitre.stream()
		              .forEach(cour->System.out.println(cour));
		
		
		
		

	}

}
