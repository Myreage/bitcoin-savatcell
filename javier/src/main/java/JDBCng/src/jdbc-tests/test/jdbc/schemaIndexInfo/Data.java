package test.jdbc.schemaIndexInfo;

import test.jdbc.Macro;

public class Data {
	
	public static class UserDMSP {
		public static String[] lastNames = 
		{"all", "Dupont", "Martin", "Lefebvre", "NumeroUn"};
		public static String[] firstNames = 
		{"all", "Pierre", "Roger", "Julien", "Patient"};
		public static String[] MF = 
		{"0", "1"};
		public static String[] adress = 
		{"1 rue du marechal Joffre", "1 rue du general Leclerc", "1 rue du général Leclerc"};
		public static String[] towns = 
		{"Paris", "Metz", "Grenoble", "Marseille", "Bordeaux"};
		public static String[] zipCodes = 
		{"75000", "57000", "38000", "13000", "33000"};
		public static String[] tel1 = 
		{"0123456789", "0987654321", "0231456789"};
		public static String[] tel2 = 
		{"0000000000", "1111111111", "2222222222"};
		public static int[] userTypes = 
		{0,1,2,3};
	}
	
	public static class Role {
		public static String[] names = 
		{"patient", "medecin", "referentSocial"};
	}
	
	public static class Formulaire {
		public static String[] names = 
		{"form"
		,"form2"
		,"form"	
		,"Examen clinique standard"
		,"Examen clinique"
		,"Examen cardiovasculaire"
		,"Examen gastroenterologique"
		,"Examen gynecologique"
		,"Examen neurosensoriel"
		,"Examen pneumologique"
		,"Examen psychocomportemental"
		,"Examen rhumatologique"
		,"Examen urologique"
		,"Examen buccodentaire"
		,"Bilan Infirmier"
		,"Bilan kinesitherapeute"
		,"Bilan medical du reseau"
		,"Bilan psychologique"
		,"Consultation memoire"
		,"Fiche synthese sociale"
		,"Grille AGGIR"
		,"Hospitalisation"
		,"form"
		,"Observations gerontologiques"
		,"Observations medicales"
		,"Organisation du retour a domicile"
		,"Plan d accompagnement"
		,"Plan d aide APA"
		,"Reperage fragilite"
		,"Resume de consultation specialisee"
		,"CR reunion de concertation"
		,"Vie et Habitat"
		,"Antecedent"
		,"Traitement"
		,"Entourage"
		,"Vaccin"
		,"Intervenant"
		,"Alerte"
		,"Resultats d examen biologique"
		,"Patient"};
	}
	
	public static class Episode {
		public static String[] names = 
		{"nonEpisode", "Cancer des os", "Grossesse", "Appendicite"};
	}
	
	public static class Event {
		public static int[] years = {2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009};
		public static int[] months = {1,2,3,4,5,6,7,8,9,10,11,0};
		public static int[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
	}
	
	public static class Concept{
		public static int[] units = 
		{0, 1, 2, 3, 4, 5};
		public static String[] names = 
		{"effectuePar",
		"motif",
		"commentaire",
		"poids",
		"temperature",
		"anorexie",
		"amaigrissement",
		"asthenie",
		"cachexie",
		"denutrition",
		"deshydratation",
		"oedeme",
		"adenopathie",
		"localisationAdenopathie",
		"douleurs",
		"escarres",
		"autresSymptGen",
		"examCardVasc",
		"examNeuro",
		"examPsycho",
		"examPneumo",
		"examUro",
		"examGastro",
		"examGyneco",
		"examBucco",
		"examRhumato",
		"freqCardCouche",
		"paSystoAssis",
		"paDiastoAssis",
		"paSystoDebout",
		"paDiastoDebout",
		"paSystoCouche",
		"paDiastoCouche",
		"symptCardVasc",
		"bruitsCoeur",
		"douleurThoracique",
		"dyspnee",
		"signesInsuffCard",
		"oedemeMembresInf",
		"locOedemeMembresInf",
		"poulsMembresInf",
		"poulsFemG",
		"poulsFemD",
		"poulsPopG",
		"poulsPopD",
		"poulsTibPostG",
		"poulsTibPostD",
		"poulsPedG",
		"poulsPedD",
		"signesInsuffVeineuse",
		"sueurs",
		"symptNeuro",
		"etatConscience",
		"motricite",
		"rot",
		"rotAchilG",
		"rotAchilD",
		"rotRotulG",
		"rotRotulD",
		"rotTriG",
		"rotTriD",
		"rotBiG",
		"rotBiD",
		"ideesDelirantes",
		"hallucinations",
		"agitation",
		"agressivite",
		"depression",
		"anxiete",
		"apathie",
		"somnolence",
		"deambulation",
		"insomnie",
		"symptPneumo",
		"encombrement",
		"hemoptysie",
		"toux",
		"symptUro",
		"dysurie",
		"retention",
		"pollakiurie",
		"hematurie",
		"incontinenceUro",
		"precisionsIncontinenceUro",
		"SymptGastro",
		"ascite",
		"constipation",
		"diarrhee",
		"dysphagie",
		"ictere",
		"nausees",
		"vomissements",
		"occlusion",
		"hepatomegalie",
		"splenomegalie",
		"douleursAbdo",
		"incontinenceAnale",
		"fecalome",
		"toucherRectal",
		"alimentation",
		"symptomesGyneco",
		"metrorragies",
		"pertes",
		"examSeins",
		"dateMammographie",
		"preventionOsteoporose",
		"protheses",
		"symptRhumato",
		"douleursRhumato",
		"impotenceFonctionnelle",
		"raideur",
		"dateVisiteDomicile",
		"travailleurSocial",
		"gir",
		"montantMaxPlanAide",
		"servicePrestaAgree",
		"qteParMois",
		"servicePrestaNonAgree",
		"assoMandataire",
		"salarieDirect",
		"forfaitFoyerLogement",
		"forfaitAccueilFamilial",
		"teleassistance",
		"produitsHygiene",
		"divers",
		"autre",
		"montantPlanAide",
		"dateDossierComplet",
		"dateMiseOeuvre",
		"txMaParticipation",
		"modCoherence",
		"modOrientation",
		"modToilette",
		"modHabillage",
		"modAlimentation",
		"modElimination",
		"modTransfert",
		"modDeplacementInt",
		"modDeplacementExt",
		"modCommunicationADistance",
		"incapacitePhy",
		"incapacitePsy",
		"datePlan",
		"origineSignalement",
		"natureIntervention",
		"vision",
		"audition",
		"nutrition",
		"memoire",
		"tristeDeprime",
		"dateDerniereChute",
		"circonstancesDerniereChute",
		"nbChute",
		"dateDerniereAnnee",
		"nbHospitalisationDerniereAnnee",
		"geneRespiratoire",
		"troubleSommeil",
		"score",
		"typeBilan",
		"conditionVie",
		"contactExt",
		"presenceAnimaux",
		"statut",
		"typeLogement",
		"milieu",
		"faciliteAcces",
		"salubriteSanitaire",
		"precisionsSalubriteSanitaire",
		"salubriteChambre",
		"precisionsSalubriteChambre",
		"salubriteSalonSalleAManger",
		"precisionsSalubriteSalonSalleAManger",
		"salubriteCuisine",
		"precisionsSalubriteCuisine",
		"salubriteEscalier",
		"precisionsSalubriteEscalier",
		"salubriteAutre",
		"precisionSalubriteautre",
		"salubriteReseaux",
		"precisionsSalubriteReseaux",
		"dossierReputeCompletLe",
		"txParticipationFinanciere",
		"natureDemande",
		"nomDemandeur",
		"prenomDemandeur",
		"situationFamiliale",
		"numSecu",
		"caisseRetraite",
		"domicileSecours",
		"adresseLieuVie",
		"departementDomicileSecours",
		"nomRepresentant",
		"nomReferent",
		"nomMedecin",
		"aides",
		"ressources",
		"certificatMedical",
		"observations",
		"nom",
		"specialite",
		"recommandations",
		"orientations",
		"precisionsOrientation",
		"nouveauTraitement",
		"date",
		"orientationSpecialisteRef",
		"precisionsSpecialisteRef",
		"mms",
		"comportement",
		"traitement",
		"suiteCadreMaintienDomicile",
		"prochaineConsultMemoire",
		"alerte",
		"crSuiviReseau",
		"bilanInclusion",
		"rapport",
		"drains",
		"urines",
		"transit",
		"glycemieDextro",
		"tpInr",
		"hydratation",
		"pansement",
		"sondes",
		"soins",
		"changementComportement",
		"activites",
		"deficit",
		"pbRespiratoires",
		"plaintes",
		"objTherapeutiques",
		"service",
		"lieuHospitalisation",
		"dateEntreeService",
		"personneOrigineHospitalisation",
		"dareFinPriseChargeService",
		"resumeCompteRendu",
		"lieu",
		"participants",
		"conclusion",
		"commentaires",
		"obsCoherence",
		"obsOrientation",
		"obsToilette",
		"obsHabillage",
		"obsAlimentation",
		"obsElimination",
		"obsTransfert",
		"obsDeplacementInt",
		"obsDeplacementExt",
		"obsCommunication",
		"symptPsycho",
		"precisionsDouleursRhumato",
		"horaires",
		"localisationImpoFonct",
		"autreOrigine",
		"autreMotif",
		"autreNatureIntervention",
		"libelle",
		"typeAtcd",
		"atcd",
		"traitement",
		"quantite",
		"formeGalenique",
		"fois",
		"frequence",
		"observations",
		"duree",
		"nom",
		"nomJF",
		"prenom",
		"sexe",
		"profession",
		"codeINSEE",
		"jourDateNaissance",
		"moisDateNaissance",
		"anneeDateNaissance",
		"communeNaissance",
		"departementNaissance",
		"paysNaissance",
		"adresse",
		"complementAdresse",
		"telephone",
		"cp",
		"ville",
		"immeuble",
		"acces",
		"code",
		"etage",
		"ascenseur",
		"porte",
		"couverture",
		"regime",
		"numeroSecu",
		"mutuelle",
		"numAdherent",
		"caisseRetraite",
		"numImmat",
		"ALD",
		"protectionJuridique",
		"groupeSanguin",
		"rhesus",
		"situationFamiliale",
		"dateProchainBilan",
		"autreOrigine",
		"autreMotif",
		"autreNature",
		"motifPlan",
		"DouleursOuiNon"};
		public static int[] dataTypes = 
		{10, 11, 12, 13, 14, 15};
	}
	
	public static class Comment {
		public static String[] names = 
		{ "noComment"
		, "Problemes respiratoires bronchiques"
		, "Douleur"
		, "Objectifs therapeutiques"
		, "Commentaires"
		, "Presence d escarres"
		, "Deficit"
		, "Principales plaintes du patient"
		, "Activites"
		, "a suivre"};
	}
	
	public static class Info {
		public static String[] valChars={
		"NumeroUn",
		"Patient",
		"lecture",
		"Arras",
		"France",
		"Mal de dos"
		};
		public static int[] valNums={0,23,123,99,65,23};
		public static int[] positions={10,123,1123,199,165,123};
	}
	
	public static class MatricePatient {
		public static int CategorieUser = Macro.CATEGORIE_USERDMSP;
		public static int CategorieFormulaire = Macro.CATEGORIE_FORMULAIRE;
		public static int CategorieEpisode = Macro.CATEGORIE_EPISODE;
		
		public static int ActeurUser = Macro.ACTEUR_USERDMSP;
		public static int ActeurRole = Macro.ACTEUR_ROLE;
		
		public static char autorisations_0= '0';
		public static char autorisations_1= '1';
		public static char autorisations_notDefine= '?';
		
		public static char[] autorisations = {'0','1','?'};
	}

}
