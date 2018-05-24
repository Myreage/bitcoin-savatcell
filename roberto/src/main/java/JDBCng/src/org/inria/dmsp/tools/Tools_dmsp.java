package org.inria.dmsp.tools;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.sql.Date;

import test.jdbc.Tools;

public class Tools_dmsp {
	
	public PrintWriter out;
	
	public Tools_dmsp (PrintWriter out) {
		this.out = out;
	}

	public byte[] loadSchema(String schema) throws Exception {
	    DataInputStream is = new DataInputStream(Tools_dmsp.class.getResourceAsStream(schema));
	    byte[] schemaDesc = loadSchema(is);
	    is.close();
	    return schemaDesc;
	}

    public byte[] loadSchema(DataInputStream is) throws Exception {
        byte[] schemaDesc;
        if(Tools.perf==0){ out.println("Loading schema... ");}
        schemaDesc = new byte[is.available() + 1];
        is.readFully(schemaDesc, 0, schemaDesc.length - 1);
        schemaDesc[schemaDesc.length - 1] = 0;
        if(Tools.perf==0){
            out.print("Schema loaded, size: ");
            out.println(schemaDesc.length);
        }
        return schemaDesc;
    }
    /*
	 *  SELECT QUERIES 
	 */
    public static java.sql.ResultSet Test_SELECT_BY_INT(
    		int IntVal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, IntVal);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_INT_AND_INT(
    		int IntVal1, int IntVal2, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, IntVal1);
		Pstmt.setInt(2, IntVal2);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_INT_AND_INT_AND_INT(
    		int IntVal1, int IntVal2, int IntVal3, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, IntVal1);
		Pstmt.setInt(2, IntVal2);
		Pstmt.setInt(3, IntVal3); 
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_INT_AND_DATE_AND_DATE(
    		int IntVal, Date DateVal1, Date DateVal2, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, IntVal);
		Pstmt.setDate(2, DateVal1);
		Pstmt.setDate(3, DateVal2); 
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}  
    public static java.sql.ResultSet Test_SELECT_BY_INT_AND_DATE(
    		int IntVal, Date DateVal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, IntVal);
		Pstmt.setDate(2, DateVal);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_BINARY(
    		byte[] BytesVal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setBytes(1, BytesVal);
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_STRING_AND_STRING(
    		String StringVal1, String StringVal2, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setString(1, StringVal1);
		Pstmt.setString(2, StringVal2); 
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_STRING(
    		String StringVal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setString(1, StringVal); 
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT(
    		java.sql.PreparedStatement Pstmt)
			throws Exception {
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}
    public static java.sql.ResultSet Test_SELECT_BY_STRING_AND_INT(
    		String StringVal, int IntVal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setString(1, StringVal);
		Pstmt.setInt(2, IntVal); 
		java.sql.ResultSet rs = Pstmt.executeQuery();
		return rs;
	}

	/*
	 *  INSERT QUERIES 
	 */

	// INSERT INTO COMMENT (Synch)
	public static void Test_COMMENT_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, 
			String ValComment, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, ValComment);	
		Pstmt.executeUpdate();
	}
	// INSERT INTO COMMENT (UI)
	public static int Test_COMMENT_INSERT(
			int Author,	int TSSPT, String ValComment,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT);
		Pstmt.setString(3, ValComment);
		int res = Pstmt.executeUpdate();
		return res;
	}
	// INSERT INTO COMMENT (TEST)
	public static int Test_COMMENT_INSERT(
			int Author,	int TSSPT, int TSSanteos, String ValComment,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos);	Pstmt.setString(4, ValComment);
		int res = Pstmt.executeUpdate();
		return res;
	}

	// INSERT INTO EPISODE (Synch)
	public static void Test_EPISODE_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, String Nom,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author); 
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, Nom); Pstmt.executeUpdate();
	}
	// INSERT INTO EPISODE (UI)
	public static void Test_EPISODE_INSERT(
			int Author, int TSSPT, int TSSanteos, String Nom,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author); 
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setString(4, Nom); Pstmt.executeUpdate();
	}
	// UPDATE INTO EPISODE (Synch)
	public static int Test_EPISODE_UPDATE(
			int Author, int TSSPT, int TSSanteos, String Nom,
			int IdGlobal,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author); 
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setString(4, Nom); 
		Pstmt.setInt(5, IdGlobal);
		int res = Pstmt.executeUpdate();
		return res;
	}

	// INSERT INTO EVENT (Synch)
	public static void Test_EVENT_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdForm,	
			int IdUser, int IdEpisode, Date DateEvent,
			Date DateFin, int Filtre,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author); 
		Pstmt.setInt(3, TSSPT);	Pstmt.setInt(4, TSSanteos); 
		Pstmt.setInt(5, IdForm); Pstmt.setInt(6, IdUser);
		Pstmt.setInt(7, IdEpisode);	Pstmt.setDate(8, DateEvent); 
		Pstmt.setDate(9, DateFin);Pstmt.setInt(10, Filtre);	 
		Pstmt.executeUpdate();
	}
	// INSERT INTO EVENT (UI)
	public static int Test_EVENT_INSERT(
			int Author, int TSSPT, int IdForm, int IdUser,
			int IdEpisode, Date DateEvent, 
			Date DateFin, int Filtre,
			java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT); 
		Pstmt.setInt(3, IdForm); Pstmt.setInt(4, IdUser); 
		Pstmt.setInt(5, IdEpisode);
		Pstmt.setDate(6, DateEvent); 
		Pstmt.setDate(7, DateFin);
		Pstmt.setInt(8, Filtre);	 
		int res = Pstmt.executeUpdate();
		return res;
	}
	// INSERT INTO EVENT (TEST)
	public static void Test_EVENT_INSERT(
			int Author, int TSSPT, int TSSanteos, int IdForm, int IdUser,
			int IdEpisode, Date DateEvent, 
			Date DateFin, int Filtre,
			java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT); 
		Pstmt.setInt(3, TSSanteos);	Pstmt.setInt(4, IdForm);		
		Pstmt.setInt(5, IdUser); Pstmt.setInt(6, IdEpisode);
		Pstmt.setDate(7, DateEvent); Pstmt.setDate(8, DateFin);
		Pstmt.setInt(9, Filtre);
		Pstmt.executeUpdate();
	}

	// INSERT INTO FORMULAIRE (Synch)
	public static void Test_FORMULAIRE_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, String Nom, 
			int Filtre,	java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, Nom); Pstmt.setInt(6, Filtre);
		Pstmt.executeUpdate();
	}
	// INSERT INTO FORMULAIRE (UI)
	public static void Test_FORMULAIRE_INSERT(
			int Author, int TSSPT, int TSSanteos, String Nom, 
			int Filtre,	java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setString(4, Nom); Pstmt.setInt(5, Filtre);
		Pstmt.executeUpdate();
	}
	// UPDATE INTO FORMULAIRE (Synch)
	public static int Test_FORMULAIRE_UPDATE(
			int Author, int TSSPT, int TSSanteos, String Nom, 
			int Filtre,	
			int IdGlobal, java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setString(4, Nom); Pstmt.setInt(5, Filtre);
		Pstmt.setInt(6, IdGlobal);
		int res = Pstmt.executeUpdate();
		return res;
	}
	
	// INSERT INTO HABILITATION (Synch)
	public static void Test_HABILITATION_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdRole, 
			int IdUser,	java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setInt(5, IdRole); Pstmt.setInt(6, IdUser);
		Pstmt.executeUpdate();
	}
	
	// INSERT INTO HABILITATION (UI)
	public static int Test_HABILITATION_INSERT(
			int Author, int TSSPT, int TSSanteos, int IdRole, 
			int IdUser,	java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setInt(4, IdRole); Pstmt.setInt(5, IdUser);
		int res = Pstmt.executeUpdate();
		return res;
	}

	// UPDATE INTO HABILITATION (Synch)
	public static int Test_HABILITATION_UPDATE(
			int Author, int TSSPT, int TSSanteos, int IdRole, int IdUser,
			int IdGlobal, java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos);
		Pstmt.setInt(4, IdRole);
		Pstmt.setInt(5, IdUser);
		Pstmt.setInt(6, IdGlobal);
		return Pstmt.executeUpdate();
	}

	// INSERT INTO INFO (Synch)
	public static void Test_INFO_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdEvent,
			int IdComment, String ValChar, int ValNum, Date ValDate, int Position,
			int Filtre, int IdConcept, java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setInt(5, IdEvent); Pstmt.setInt(6, IdComment);
		Pstmt.setString(7, ValChar); Pstmt.setInt(8, ValNum); 
		Pstmt.setDate(9, ValDate); Pstmt.setInt(10, Position);
		Pstmt.setInt(11, Filtre); Pstmt.setInt(12, IdConcept);
		Pstmt.executeUpdate();
	}
	// INSERT INTO INFO (UI)
	public static int Test_INFO_INSERT(
			int Author, int TSSPT, int IdEvent,
			int IdComment, String ValChar, int ValNum, Date ValDate, int Position,
			int Filtre, int IdConcept, java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT); 
		Pstmt.setInt(3, IdEvent); Pstmt.setInt(4, IdComment);
		Pstmt.setString(5, ValChar); Pstmt.setInt(6, ValNum); 
		Pstmt.setDate(7, ValDate); Pstmt.setInt(8, Position);
		Pstmt.setInt(9, Filtre); Pstmt.setInt(10, IdConcept);
		int res = Pstmt.executeUpdate();
		return res;
	}
	// INSERT INTO INFO (TEST)
	public static int Test_INFO_INSERT(
			int Author, int TSSPT, int TSSanteos, int IdEvent,
			int IdComment, String ValChar, int ValNum, Date ValDate, int Position,
			int Filtre, int IdConcept, java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setInt(4, IdEvent); Pstmt.setInt(5, IdComment);
		Pstmt.setString(6, ValChar); Pstmt.setInt(7, ValNum); 
		Pstmt.setDate(8, ValDate); Pstmt.setInt(9, Position);
		Pstmt.setInt(10, Filtre); Pstmt.setInt(11, IdConcept);
		int res = Pstmt.executeUpdate();
		return res;
	}

	// INSERT INTO ROLE (Synch)
	public static void Test_ROLE_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, String Nom,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, Nom);
		Pstmt.executeUpdate();
	}
	// INSERT INTO ROLE (UI)
	public static void Test_ROLE_INSERT(
			int Author, int TSSPT, int TSSanteos, String Nom,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT); Pstmt.setInt(3, TSSanteos);
		Pstmt.setString(4, Nom);
		Pstmt.executeUpdate();
	}
	
	// INSERT INTO MATRICE_DMSP (Synch)
	public static void Test_DMSP_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdForm,
			int IdRole, int Autorization, 
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setInt(5, IdForm); Pstmt.setInt(6, IdRole);
		Pstmt.setInt(7, Autorization);
		Pstmt.executeUpdate();
	}
	
	// INSERT INTO USER (Synch)
	public static void Test_USER_INSERT(
			int IdGlobal,int Author,int TSSPT,int TSSanteos, String Nom, 
			int Type, String Responsable, String Identifiant, int Civilite, 
			String Prenom, String Adresse, String Ville, String CodePost, 
			String Tel, String Mobile, String Courriel, int InfoLegale,
			String Certificate,	int IdRole, java.sql.PreparedStatement Pstmt) 
	throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setString(5, Nom); Pstmt.setInt(6, Type);
		Pstmt.setString(7, Responsable); Pstmt.setString(8, Identifiant);
		Pstmt.setInt(9, Civilite); Pstmt.setString(10, Prenom);
		Pstmt.setString(11, Adresse); Pstmt.setString(12, Ville);
		Pstmt.setString(13, CodePost); Pstmt.setString(14, Tel);
		Pstmt.setString(15, Mobile); Pstmt.setString(16, Courriel);
		Pstmt.setInt(17, InfoLegale); Pstmt.setString(18, Certificate);
		Pstmt.setInt(19, IdRole);
		Pstmt.executeUpdate();
	}
	// INSERT INTO USERDMSP (UI)
	public static void Test_USER_INSERT(
			int Author,int TSSPT,int TSSanteos, String Nom, int Type,
			String Responsable, String Identifiant,	int Civilite, String Prenom,
			String Adresse,	String Ville,String CodePost, String Tel, String Mobile,
			String Courriel, int InfoLegale, String Certificate, 
			int IdRole, java.sql.PreparedStatement Pstmt)
	throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT); 
		Pstmt.setInt(3, TSSanteos); Pstmt.setString(4, Nom); 
		Pstmt.setInt(5, Type); Pstmt.setString(6, Responsable); 
		Pstmt.setString(7, Identifiant); Pstmt.setInt(8, Civilite); 
		Pstmt.setString(9, Prenom); Pstmt.setString(10, Adresse); 
		Pstmt.setString(11, Ville); Pstmt.setString(12, CodePost); 
		Pstmt.setString(13, Tel); Pstmt.setString(14, Mobile); 
		Pstmt.setString(15, Courriel); Pstmt.setInt(16, InfoLegale);
		Pstmt.setString(17, Certificate); Pstmt.setInt(17, IdRole);
 		Pstmt.executeUpdate();
	}
	
	/*
	 *  UPDATE QUERIES 
	 */
    
	
	// UPDATE USER (Synch)
	public static int Test_USER_UPDATE(
			int Author, int TSSPT, int TSSanteos, 
			String Nom, int Type,
			String Responsable, String Identifiant,	int Civilite, String Prenom,
			String Adresse,	String Ville,String CodePost, String Tel, String Mobile,
			String Courriel, int InfoLegale, byte[] Certificate, 
			int IdRole, int IdGlobal,
			java.sql.PreparedStatement Pstmt) throws Exception {
		// params: 1=30/TSSP,1=35/DateEvent,3=95/DateFin,4=96/Filtre,5=28/IdG
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT); 
		Pstmt.setInt(3, TSSanteos); Pstmt.setString(4, Nom); 
		Pstmt.setInt(5, Type); Pstmt.setString(6, Responsable); 
		Pstmt.setString(7, Identifiant); Pstmt.setInt(8, Civilite); 
		Pstmt.setString(9, Prenom); Pstmt.setString(10, Adresse); 
		Pstmt.setString(11, Ville); Pstmt.setString(12, CodePost); 
		Pstmt.setString(13, Tel); Pstmt.setString(14, Mobile); 
		Pstmt.setString(15, Courriel); Pstmt.setInt(16, InfoLegale);
		Pstmt.setBytes(17, Certificate); Pstmt.setInt(18, IdRole);
		Pstmt.setInt(19, IdGlobal); 
		int res = Pstmt.executeUpdate();
		return res;
	}
	
	// UPDATE EVENT (UI)
	public static int Test_EVENT_UPDATE(
			int TSSPT, Date DateEvent, Date DateFin, int Filtre,
			int IdGlobal,
			java.sql.PreparedStatement Pstmt) throws Exception {
		// params: 1=30/TSSP, 2=35/DateEvent, 3=95/DateFin, 4=96/Filtre, 5=28/IdG
		Pstmt.setInt(1, TSSPT);	Pstmt.setDate(2, DateEvent); 
		Pstmt.setDate(3, DateFin); Pstmt.setInt(4, Filtre);
		Pstmt.setInt(5, IdGlobal); 
		int res = Pstmt.executeUpdate();
		return res;
	}
	// UPDATE EVENT (Synch)
	public static int Test_EVENT_UPDATE(
			int Author, int TSSPT, int TSSanteos, 
			Date DateEvent, Date DateFin, int Filtre,
			int IdGlobal,
			java.sql.PreparedStatement Pstmt) throws Exception {
		// params: 1= /Author,2=30/TSSP,3= /TSSanteos,4=35/DateEvent,5=95/DateFin,6=96/Filtre,7=28/IdG
		Pstmt.setInt(1, Author);
		Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos);
		Pstmt.setDate(4, DateEvent); 
		Pstmt.setDate(5, DateFin); 
		Pstmt.setInt(6, Filtre);
		Pstmt.setInt(7, IdGlobal); 
		int res = Pstmt.executeUpdate();
		return res;
	}

	// UPDATE INFO (UI)
	public static int Test_INFO_UPDATE(
			int TSSPT, String ValChar, int ValNum, Date ValDate, int filtre, 
			int IdGlobal, java.sql.PreparedStatement Pstmt) throws Exception {
		// params: 1=38(TSSPT), 2=42(ValChar), 3=43(ValNum), 4=44(ValDate), 5=36(IdG)
		Pstmt.setInt(1, TSSPT);	Pstmt.setString(2, ValChar); 
		Pstmt.setInt(3, ValNum); Pstmt.setDate(4, ValDate);
		Pstmt.setInt(5, filtre); Pstmt.setInt(6, IdGlobal); 
		int res = Pstmt.executeUpdate();
		return res;
	}

	// UPDATE COMMENT (UI)
	public static int Test_COMMENT_UPDATE(
			int TSSPT, String ValComment, int IdGlobal, 
			java.sql.PreparedStatement Pstmt) throws Exception {
		// params: params: 1=49(TSSPT), 2=51(ValComment), 3=47(IdGlobal)
		Pstmt.setInt(1, TSSPT);	Pstmt.setString(2, ValComment); 
		Pstmt.setInt(3, IdGlobal); 
		int res = Pstmt.executeUpdate();
		return res;
	}
	
	// INSERT INTO MATRICE_PATIENT (Synch)
	public static void Test_PATIENT_INSERT(
			int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdCategorie,
			int TypeCat, int IdActeur, int TypeActeur, int Autorization,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, Author);
		Pstmt.setInt(3, TSSPT); Pstmt.setInt(4, TSSanteos);
		Pstmt.setInt(5, IdCategorie);
		Pstmt.setInt(6, TypeCat); Pstmt.setInt(7, IdActeur);
		Pstmt.setInt(8, TypeActeur); Pstmt.setInt(9, Autorization);
		Pstmt.executeUpdate();
	}
	
	// INSERT INTO TupleDeleted (test)
	public static void Test_DELETED_INSERT(
			int IdGlobal, int TabId, int Author, int TSSPT, int TSSanteos,
			java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal); Pstmt.setInt(2, TabId); 
		Pstmt.setInt(3, Author); Pstmt.setInt(4, TSSPT); 
		Pstmt.setInt(5, TSSanteos); Pstmt.executeUpdate();
	}
	
	/*
	 *  UPDATE QUERIES 
	 */

	// UPDATE INFO (Synch)
	public static void Test_INFO_UPDATE(
			int Author, int TSSPT, int TSSanteos,
			String ValChar, int ValNum, Date ValDate, int Position, int Filtre, 
			int IdConcept, int IdGlobal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos); Pstmt.setString(4, ValChar);
		Pstmt.setInt(5, ValNum); Pstmt.setDate(6, ValDate);
		Pstmt.setInt(7, Position); Pstmt.setInt(8, Filtre); 
		Pstmt.setInt(9, IdConcept); Pstmt.setInt(10, IdGlobal); 
		Pstmt.executeUpdate();
	}
	
	// UPDATE COMMENT (Synch)
	public static void Test_COMMENT_UPDATE(
			int Author, int TSSPT, int TSSanteos, String ValComment,
			int IdGlobal, java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos); Pstmt.setString(4, ValComment);
		Pstmt.setInt(5, IdGlobal);
		Pstmt.executeUpdate();
	}
	
	// UPDATE ROLE (Synch)
	public static void Test_ROLE_UPDATE(
			int Author, int TSSPT, int TSSanteos, String Nom, int IdGlobal, 
			java.sql.PreparedStatement Pstmt)
			throws Exception {
		Pstmt.setInt(1, Author); Pstmt.setInt(2, TSSPT);
		Pstmt.setInt(3, TSSanteos); Pstmt.setString(4, Nom);
		Pstmt.setInt(5, IdGlobal);
		Pstmt.executeUpdate();
	}
	
	/*
	 *  DELETE QUERIES 
	 */
    
	
	// DELETE HABILITATION (UI)
	public static int Test_HABILITATION_DELETE(
			int IdUser,	java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdUser);
		int res = Pstmt.executeUpdate();
		return res;
	}
	
	// DELETE HABILITATION (Synchro)
	public static int Test_DELETE_BY_ID(
			int IdGlobal,	java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, IdGlobal);
		int res = Pstmt.executeUpdate();
		return res;
	}

}
