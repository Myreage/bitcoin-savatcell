package test.jdbc.schemaIndexInfo;

import test.jdbc.Macro;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.sql.Date;

import org.inria.dmsp.tools.DMSP_QEP_IDs;

import test.jdbc.Tools;

public class Tools_schemaIndexInfo {
	
	public PrintWriter out;
	
	public Tools_schemaIndexInfo(PrintWriter out) {
		this.out = out;
	}

	/* QUERIES */
	//COMMENT
	public static void Test_COMMENT_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			String ValComment,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, ValComment);
		Pstmt.executeUpdate();
	}
	public static java.sql.ResultSet Test_COMMENT_NOAC_AC_INSERT(
			int Author,
			int TSSPT,
			int TSSanteos,
			String ValComment,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos);
		Pstmt.setString(4, ValComment);
		Pstmt.executeUpdate();
		java.sql.ResultSet r = Pstmt.getGeneratedKeys();
		return r;
	}
	
	//CONCEPT
	public static void Test_CONCEPT_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			int Unit,
			String Nom,
			int DataType,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		Pstmt.setInt(5, Unit);
		Pstmt.setString(6, Nom);
		Pstmt.setInt(7, DataType);
		Pstmt.executeUpdate();
	}

	//EPISODE
	public static void Test_EPISODE_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			String Nom,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, Nom);
		Pstmt.executeUpdate();
	}

	//EVENT
	public static void Test_EVENT_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			int IdForm,
			int IdUser,
			int IdEpisode,
			Date DateEvent,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		Pstmt.setInt(5, IdForm);
		Pstmt.setInt(6, IdUser);
		Pstmt.setInt(7, IdEpisode);
		Pstmt.setDate(8, DateEvent);
		Pstmt.executeUpdate();
	}
	public static java.sql.ResultSet Test_EVENT_NOAC_AC_INSERT(
			int Author,
			int TSSPT,
			int TSSanteos,
			int IdForm,
			int IdUser,
			int IdEpisode,
			Date DateEvent,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos);
		Pstmt.setInt(4, IdForm);
		Pstmt.setInt(5, IdUser);
		Pstmt.setInt(6, IdEpisode);
		Pstmt.setDate(7, DateEvent);
		Pstmt.executeUpdate();
		java.sql.ResultSet r = Pstmt.getGeneratedKeys();
		return r;
	}

	//FORMULAIRE
	public static void Test_FORMULAIRE_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			String Nom,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		//Data Fields
		Pstmt.setString(5, Nom);
		Pstmt.executeUpdate();
	}

	//HABILITATION
	public static void Test_HABILITATION_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			int IdRole,
			int IdUser,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		//Data fields
		Pstmt.setInt(5, IdRole);
		Pstmt.setInt(6, IdUser);
		Pstmt.executeUpdate();
	}

	//INFO
	  public static java.sql.ResultSet Test_INFO_NOAC_AC_INSERT(int Author,
		      int TSSPT,
		      int TSSanteos,
		      int IdEvent,
		      int IdConcept,
		      int IdComment,
		      String ValChar,
		      int ValNum,
		      int Position,
		      java.sql.PreparedStatement Pstmt)
		  throws Exception {
		    //Sync info : not filled
		    // Synchro Fields
		    Pstmt.setInt(1, Author);
		    Pstmt.setInt(2, TSSPT);
		    Pstmt.setInt(3, TSSanteos);
		    // Data Fields
		    Pstmt.setInt(4, IdEvent);
		    Pstmt.setInt(5, IdConcept);
		    Pstmt.setInt(6, IdComment);
		    Pstmt.setString(7, ValChar);
		    Pstmt.setInt(8, ValNum);
		    Pstmt.setInt(9, Position);
		    Pstmt.executeUpdate();
		    java.sql.ResultSet r = Pstmt.getGeneratedKeys();
		    return r;
		  }

	public static void Test_INFO_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			int IdEvent,
			int IdConcept,
			int IdComment,
			String Titre,
			int ValInfoNum,
			int Position,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		//Data Fields
		Pstmt.setInt(5, IdEvent);
		Pstmt.setInt(6, IdConcept);
		Pstmt.setInt(7, IdComment);
		Pstmt.setString(8, Titre);
		Pstmt.setInt(9, ValInfoNum);
		Pstmt.setInt(10, Position);
		Pstmt.executeUpdate();
	}
	public static int Test_INFO_NOAC_DELETE_BY_EVENT_ID(
			int b,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		int res = 0;
		Pstmt.setInt(1, b);
		res = Pstmt.executeUpdate();
		return res;
	}
	public static int Test_INFO_NOAC_UPDATE_Valchar_BY_ID(
			int b, String newValue, java.sql.PreparedStatement Pstmt)
	throws Exception {
		int res = 0;
		Pstmt.setString(1, newValue);
		Pstmt.setInt(2, b);
		res = Pstmt.executeUpdate();
		return res;
	}
	public static java.sql.ResultSet Test_INFO_SELECT_STAR_BY_EVENT_ID(
			int b, 
			java.sql.PreparedStatement Pstmt) 
	throws Exception {
		Pstmt.setInt(1, b);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}


	//MATRICEDMSP
	public static void Test_MATRICEDMSP_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			int IdForm,
			int IdRole,
			int Autorization,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		//Data Fields
		Pstmt.setInt(5, IdForm);
		Pstmt.setInt(6, IdRole);
		Pstmt.setInt(7, Autorization);
		Pstmt.executeUpdate();
	}

	//MATRICEPATIENT
	public static java.sql.PreparedStatement Get_EP_MATRICE_PATIENT_NOAC_INSERT(
			int IdCategorie,
			int TypeCat,
			int IdActeur,
			int TypeActeur,
			java.sql.Connection connSPT)
	throws Exception{
		java.sql.PreparedStatement Pstmt;

		//if IdCategorie == null , so we are setting the authorization for the "Other Category" row of the
		//patient rights matrix. TypeCat tells whether it is "Other Forms" , "Other User" or "Other Episode"
		if (IdCategorie == 0
				&& TypeActeur == Macro.ACTEUR_USERDMSP) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_OTHER_USER_WITH_IDS);
		} else if (IdCategorie == 0
				&& TypeActeur == Macro.ACTEUR_ROLE) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_OTHER_ROLE_WITH_IDS);
		} 
		
		else if (TypeCat == Macro.CATEGORIE_EPISODE
				&& TypeActeur == Macro.ACTEUR_USERDMSP) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_EPISODE_USER_WITH_IDS);
		} else if (TypeCat == Macro.CATEGORIE_EPISODE
				&& TypeActeur == Macro.ACTEUR_ROLE) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_EPISODE_ROLE_WITH_IDS);
		}

		else if (TypeCat == Macro.CATEGORIE_FORMULAIRE
				&& TypeActeur == Macro.ACTEUR_USERDMSP) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_FORM_USER_WITH_IDS);
		} else if (TypeCat == Macro.CATEGORIE_FORMULAIRE
				&& TypeActeur == Macro.ACTEUR_ROLE) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_FORM_ROLE_WITH_IDS);
		}

		else if (TypeCat == Macro.CATEGORIE_USERDMSP
				&& TypeActeur == Macro.ACTEUR_USERDMSP) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_USER_USER_WITH_IDS);
		} else if (TypeCat == Macro.CATEGORIE_USERDMSP
				&& TypeActeur == Macro.ACTEUR_ROLE) {
			Pstmt = ((org.inria.jdbc.Connection)connSPT).prepareStatement(
					DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_INSERT_USER_ROLE_WITH_IDS);
		}


		else {
			return null;
		}
		return Pstmt;
	}
	public static void Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			int IdCategorie,
			int TypeCat,
			int IdActeur,
			int TypeActeur,
			int Autorization,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		//Data Fields
		if (IdCategorie == 0) {
			Pstmt.setInt(5, 0);
		} else {
			Pstmt.setInt(5, IdCategorie);
		}
		Pstmt.setInt(6, TypeCat);
		Pstmt.setInt(7, IdActeur);
		Pstmt.setInt(8, TypeActeur);
		Pstmt.setInt(9, Autorization);
		Pstmt.executeUpdate();
	}

	//ROLE
	public static void Test_ROLE_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			String Nom,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		//Data Fields
		Pstmt.setString(5, Nom);
		Pstmt.executeUpdate();
	}

	//USERDMSP
	public static java.sql.ResultSet Test_USERDMSP_NOAC_AC_INSERT(
			int Author,
			int TSSPT,
			int TSSanteos,
			String Nom,
			String Prenom,
			String Sexe,
			String Adresse,
			String Ville,
			String CodePost,
			String Tel1,
			String Tel2,
			int UserType,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		// Synchro Fields
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos);
		// Data Fields
		Pstmt.setString(4, Nom);
		Pstmt.setString(5, Prenom);
		Pstmt.setString(6, Sexe);
		Pstmt.setString(7, Adresse);
		Pstmt.setString(8, Ville);
		Pstmt.setString(9, CodePost);
		Pstmt.setString(10, Tel1);
		Pstmt.setString(11, Tel2);
		Pstmt.setInt(12, UserType);
		Pstmt.executeUpdate();
		java.sql.ResultSet r = Pstmt.getGeneratedKeys();
		return r;
	}
	public static void Test_USERDMSP_NOAC_AC_INSERT_WITH_IDS(
			int b,
			int Author,
			int TSSPT,
			int TSSanteos,
			String Nom,
			String Prenom,
			String Sexe,
			String Adresse,
			String Ville,
			String CodePost,
			String Tel1,
			String Tel2,
			int UserType,
			java.sql.PreparedStatement Pstmt)
	throws Exception {
		//Sync info : not filled
		Pstmt.setInt(1, b);
		Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT);
		Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, Nom);
		Pstmt.setString(6, Prenom);
		Pstmt.setString(7, Sexe);
		Pstmt.setString(8, Adresse);
		Pstmt.setString(9, Ville);
		Pstmt.setString(10, CodePost);
		Pstmt.setString(11, Tel1);
		Pstmt.setString(12, Tel2);
		Pstmt.setInt(13, UserType);
		Pstmt.executeUpdate();
	}
	
	//QUERY
	public static java.sql.ResultSet Test_SELECT_NOAC_EVENT_USER_BY_FORM(
			int b, 
			java.sql.PreparedStatement Pstmt) 
	throws Exception {
		Pstmt.setInt(1, b);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}

	public static java.sql.ResultSet Test_SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE(
			int b, 
			Date DateEvent,
			java.sql.PreparedStatement Pstmt) 
	throws Exception {
		Pstmt.setInt(1, b);
		Pstmt.setDate(2, DateEvent);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
	
	public static java.sql.ResultSet Test_SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE_FORM(
			int b, 
			Date DateEvent,
			String c, 
			java.sql.PreparedStatement Pstmt) 
	throws Exception {
		Pstmt.setInt(1, b);
		Pstmt.setDate(2, DateEvent);
		Pstmt.setString(3, c);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
	
	public byte[] loadSchema(String schema) throws Exception {
	    DataInputStream is = new DataInputStream(Tools_schemaIndexInfo.class.getResourceAsStream(schema));
	    byte[] schemaDesc = loadSchema(is);
	    is.close();
	    return schemaDesc;
	}

    public byte[] loadSchema(DataInputStream is) throws Exception {
        byte[] schemaDesc;
        if(Tools.perf==0){
            out.println("Loading schema... ");
        }
        schemaDesc = new byte[is.available() + 1];
        is.readFully(schemaDesc, 0, schemaDesc.length - 1);
        schemaDesc[schemaDesc.length - 1] = 0;
        if(Tools.perf==0){
            out.print("Schema loaded, size: ");
            out.println(schemaDesc.length);
        }
        return schemaDesc;
    }

}
