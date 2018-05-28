package org.inria.dmsp.tools;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.Calendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Connection;
/*
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
*/
import test.jdbc.Macro;

public class DeltaLoader
{
	private PrintWriter out;
	public int perf;

	public DeltaLoader( PrintWriter out )
	{
		super();
		this.out = out;
		this.perf = 0;
	}

	public DeltaLoader( PrintWriter out, int perf )
	{
		super();
		this.out = out;
		this.perf = perf;
	}
/* TODO: too much copy/pase, refactoring is needed
	public void LoadXmlDelta( InputStream fr, Connection db ) throws Exception
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware( true );
		Document doc = dbf.newDocumentBuilder().parse( fr );
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = null;
		NodeList tuples = null;
		PreparedStatement ps = null;	// the prepared statement used for all inserts

		if ( perf == 0 ) { out.println("Loading Delta."); }

		// INSERT INTO USER
		//
		// int		IdGlobal
		// int		Author
		// int		TSSPT
		// int		TSSanteos
		// String	Nom
		// int		Type
		// String	Responsable
		// String	Identifiant
		// int		Civilite
		// String	Prenom
		// String	Adresse
		// String	Ville
		// String	CodePost
		// String	Tel
		// String	Mobile
		// String	Courriel
		// int		InfoLegale
		// byte[]	Certificate
		// int		IdRole
		if ( perf == 0 ) { out.println("// Insertion dans la table USERDMSP "); }
		ps = db.prepareStatement( EP_Synchro.EP_USER_INSERT );
		expr = xpath.compile( "//Table[name='UserDMSP']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 19 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			// catch up next 15 values...
			NodeList values = tuples.item(idx).getChildNodes();
			for ( int n=0; n<values.getLength(); ++n )
			{	// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
				params[ 4 + n%15 ] = values.item(n).getNodeValue();
				if ( n%15 == 15-1 )
				{	// completed gathering values for insert => do it now...
					if ( params[17].length() > 20 )	// if the certificate is longer than 20 bytes then
						params[17] = params[17].substring( 0, 20 ); // cut extra bytes...
					// insert the tuple...
					Tools_dmsp.Test_USER_INSERT(
						((params[ 0]==null || params[ 0].equals("[]"))) ?	-1 : (Integer.parseInt(params[ 0])),
						((params[ 1]==null || params[ 1].equals("[]"))) ?	-1 : (Integer.parseInt(params[ 1])),
											 (params[ 2].equals("[]") ) ?	-1 : (Integer.parseInt(params[ 2])),
						((params[ 3]==null || params[ 3].equals("[]"))) ?	-1 : (Integer.parseInt(params[ 3])),
						((params[ 4]==null || params[ 4].equals("[]"))) ? null :				   params[ 4],
						((params[ 5]==null || params[ 5].equals("[]"))) ?	-1 : (Integer.parseInt(params[ 5])),
						((params[ 6]==null || params[ 6].equals("[]"))) ? null :				   params[ 6],
						((params[ 7]==null || params[ 7].equals("[]"))) ? null :				   params[ 7],
						((params[ 8]==null || params[ 8].equals("[]"))) ?	-1 : (Integer.parseInt(params[ 8])),
						((params[ 9]==null || params[ 9].equals("[]"))) ? null :				   params[ 9],
						((params[10]==null || params[10].equals("[]"))) ? null :				   params[10],
						((params[11]==null || params[11].equals("[]"))) ? null :				   params[11],
						((params[12]==null || params[12].equals("[]"))) ? null :				   params[12],
						((params[13]==null || params[13].equals("[]"))) ? null :				   params[13],
						((params[14]==null || params[14].equals("[]"))) ? null :				   params[14],
						((params[15]==null || params[15].equals("[]"))) ? null :				   params[15],
						((params[16]==null || params[16].equals("[]"))) ?	-1 : (Integer.parseInt(params[16])),
						((params[17]==null || params[17].equals("[]"))) ? null :				   params[17],
						((params[18]==null || params[18].equals("[]"))) ?	-1 : (Integer.parseInt(params[18])),
						ps);
				}
			}
		}
		// INSERT INTO ROLE
		//
		// int		IdGlobal
		// int		Author
		// int		TSSPT
		// int		TSSanteos
		// String	Role
		if ( perf == 0 ) { out.println("// Insertion dans la table ROLE "); }
		ps = db.prepareStatement( EP_Synchro.EP_ROLE_INSERT );
		expr = xpath.compile( "//Table[name='Role']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 5 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			NodeList values = tuples.item(idx).getChildNodes();
			if ( values.getLength() == 0 )
				continue;
			// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
			params[ 4 ] = values.item(0).getNodeValue();
			Tools_dmsp.Test_ROLE_INSERT(
					((params[0]==null || params[0].equals("[]"))) ?   -1 : (Integer.parseInt(params[0])),
					((params[1]==null || params[1].equals("[]"))) ?   -1 : (Integer.parseInt(params[1])),
										(params[2].equals("[]") ) ?   -1 : (Integer.parseInt(params[2])),
					((params[3]==null || params[3].equals("[]"))) ?   -1 : (Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[]"))) ? null :					 params[4],
					ps);
		}
		//
		//  INSERT INTO HABILITATION
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table HABILITATION "); }
		ps = db.prepareStatement(EP_Synchro.EP_HABILITATION_INSERT);
		expr = xpath.compile( "//Table[name='Habilitation']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 6 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			// catch up next 6-4=2 values...
			NodeList values = tuples.item(idx).getChildNodes();
			for ( int n=0; n<values.getLength(); ++n )
			{	// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
				params[ 4 + n%2 ] = values.item(n).getNodeValue();
				if ( n%2 == 2-1 )
				{	// completed gathering values for insert => do it now...
					Tools_dmsp.Test_HABILITATION_INSERT(
						((params[0]==null || params[0].equals("[]"))) ? -1 : (Integer.parseInt(params[0])),
						((params[1]==null || params[1].equals("[]"))) ? -1 : (Integer.parseInt(params[1])),
											(params[2].equals("[]") ) ? -1 : (Integer.parseInt(params[2])),
						((params[3]==null || params[3].equals("[]"))) ? -1 : (Integer.parseInt(params[3])),
						((params[4]==null || params[4].equals("[]"))) ? -1 : (Integer.parseInt(params[4])),
						((params[5]==null || params[5].equals("[]"))) ? -1 : (Integer.parseInt(params[5])),
						ps);
				}
			}
		}
		//
		//  INSERT INTO Formulaire
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table FORMULAIRE "); }
		ps = db.prepareStatement( EP_Synchro.EP_FORMULAIRE_INSERT );
		expr = xpath.compile( "//Table[name='Formulaire']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 6 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			// catch up next 6-4=2 values...
			NodeList values = tuples.item(idx).getChildNodes();
			for ( int n=0; n<values.getLength(); ++n )
			{	// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
				params[ 4 + n%2 ] = values.item(n).getNodeValue();
				if ( n%2 == 2-1 )
				{	// completed gathering values for insert => do it now...
					Tools_dmsp.Test_FORMULAIRE_INSERT(
						((params[0]==null || params[0].equals("[]"))) ?   -1 : (Integer.parseInt(params[0])),
 						((params[1]==null || params[1].equals("[]"))) ?   -1 : (Integer.parseInt(params[1])),
 											(params[2].equals("[]") ) ?   -1 : (Integer.parseInt(params[2])),
 						((params[3]==null || params[3].equals("[]"))) ?   -1 : (Integer.parseInt(params[3])),
 						((params[4]==null || params[4].equals("[]"))) ? null :					 params[4],
 						((params[5]==null || params[5].equals("[]"))) ?   -1 : (Integer.parseInt(params[5])),
 						ps);
				}
			}
		}
		//
		//  INSERT INTO Episode
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table EPISODE "); }
		ps = db.prepareStatement( EP_Synchro.EP_EPISODE_INSERT );
		expr = xpath.compile( "//Table[name='Episode']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 5 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			NodeList values = tuples.item(idx).getChildNodes();
			if ( values.getLength() == 0 )
				continue;
			// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
			params[ 4 ] = values.item(0).getNodeValue();
			Tools_dmsp.Test_EPISODE_INSERT(
					((params[0]==null || params[0].equals("[]"))) ?   -1 : (Integer.parseInt(params[0])),
					((params[1]==null || params[1].equals("[]"))) ?   -1 : (Integer.parseInt(params[1])),
										(params[2].equals("[]") ) ?   -1 : (Integer.parseInt(params[2])),
					((params[3]==null || params[3].equals("[]"))) ?   -1 : (Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[]"))) ? null :					 params[4],
					ps);
		}
		//
		//  INSERT INTO EVENT
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table EVENT "); }
		ps = db.prepareStatement( EP_Synchro.EP_EVENT_INSERT );
		expr = xpath.compile( "//Table[name='Event']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 10 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			// catch up next 10-4=6 values...
			NodeList values = tuples.item(idx).getChildNodes();
			for ( int n=0; n<values.getLength(); ++n )
			{	// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
				params[ 4 + n%6 ] = values.item(n).getNodeValue();
				if ( n%6 == 6-1 )
				{	// completed gathering values for insert => do it now...
					Date param7 = null, param8 = null;
					if ( params[7] != null && !params[7].equals("[]") )
					{
						String[] a = strSplit( params[7], "/" );
						// if the separator of the date is not "/", try "[-]"
						Calendar cal = Calendar.getInstance();
						if ( a.length == 1 )
						{	// YYYY-MM-DD
							a = strSplit( a[0], "-" );
							cal.set( Calendar.YEAR        , Integer.parseInt(a[0]) );
							cal.set( Calendar.MONTH       , Integer.parseInt(a[1]) - 1 );
							cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]) );
						}
						else
						{	// DD/MM/YYYY
							cal.set( Calendar.YEAR        , Integer.parseInt(a[2]) );
							cal.set( Calendar.MONTH       , Integer.parseInt(a[1]) - 1 );
							cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]) );
						}
						param7 = new java.sql.Date( cal.getTime().getTime() );
					}

					if ( params[8] != null && !params[8].equals("[]") )
					{
						String[] a = strSplit( params[8], "/" );
						Calendar cal = Calendar.getInstance();
						if ( a.length == 1 )
						{	// YYYY-MM-DD
							a = strSplit( a[0], "-" );
							cal.set( Calendar.YEAR        , Integer.parseInt(a[0]) );
							cal.set( Calendar.MONTH       , Integer.parseInt(a[1]) - 1 );
							cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]) );
						}
						else
						{	// DD/MM/YYYY
							cal.set( Calendar.YEAR        , Integer.parseInt(a[2]) );
							cal.set( Calendar.MONTH       , Integer.parseInt(a[1]) - 1 );
							cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]) );
						}
						param8 = new java.sql.Date( cal.getTime().getTime() );
					}

					Tools_dmsp.Test_EVENT_INSERT(
						((params[0]==null || params[0].equals("[]"))) ? -1 : (Integer.parseInt(params[0])),
						((params[1]==null || params[1].equals("[]"))) ? -1 : (Integer.parseInt(params[1])),
											(params[2].equals("[]") ) ? -1 : (Integer.parseInt(params[2])),
						((params[3]==null || params[3].equals("[]"))) ? -1 : (Integer.parseInt(params[3])),
						((params[4]==null || params[4].equals("[]"))) ? -1 : (Integer.parseInt(params[4])),
						((params[5]==null || params[5].equals("[]"))) ? -1 : (Integer.parseInt(params[5])),
						((params[6]==null || params[6].equals("[]"))) ? -1 : (Integer.parseInt(params[6])),
						param7,
						param8,
						((params[9]==null || params[9].equals("[]"))) ? -1 : (Integer.parseInt(params[9])),
						ps);
				}
			}
		}
		//
		//  INSERT INTO COMMENT
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table COMMENT "); }
		ps = db.prepareStatement( EP_Synchro.EP_COMMENT_INSERT );
		expr = xpath.compile( "//Table[name='Comment']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 5 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			NodeList values = tuples.item(idx).getChildNodes();
			if ( values.getLength() == 0 )
				continue;
			// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
			params[ 4 ] = values.item(0).getNodeValue();
			Tools_dmsp.Test_COMMENT_INSERT(
				((params[0]==null || params[0].equals("[]"))) ?   -1 : (Integer.parseInt(params[0])),
				((params[1]==null || params[1].equals("[]"))) ?   -1 : (Integer.parseInt(params[1])),
									(params[2].equals("[]") ) ?   -1 : (Integer.parseInt(params[2])),
				((params[3]==null || params[3].equals("[]"))) ?   -1 : (Integer.parseInt(params[3])),
				((params[4]==null || params[4].equals("[]"))) ? null : params[4],
				ps);
		}
		//
		//  INSERT INTO INFO
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table INFO "); }
		ps = db.prepareStatement( EP_Synchro.EP_INFO_INSERT );
		expr = xpath.compile( "//Table[name='Info']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 12 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			// catch up next 12-4=8 values...
			NodeList values = tuples.item(idx).getChildNodes();
			for ( int n=0; n<values.getLength(); ++n )
			{	// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
				params[ 4 + n%8 ] = values.item(n).getNodeValue();
				if ( n%8 == 8-1 )
				{	// completed gathering values for insert => do it now...
					Date param8 = null;
					if ( params[8] != null || params[8].equals("[]") )
					{
						String[] a = strSplit( params[8], "/" );
						Calendar cal = Calendar.getInstance();
						if ( a.length == 1 )
						{	// YYYY-MM-DD
							a = strSplit( a[0], "-" );
							cal.set( Calendar.YEAR        , Integer.parseInt(a[0]) );
							cal.set( Calendar.MONTH       , Integer.parseInt(a[1]) - 1 );
							cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]) );
						}
						else
						{	// DD/MM/YYYY
							cal.set( Calendar.YEAR        , Integer.parseInt(a[2]) );
							cal.set( Calendar.MONTH       , Integer.parseInt(a[1]) - 1 );
							cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]) );
						}
						param8 = new java.sql.Date( cal.getTime().getTime() );
					}
					Tools_dmsp.Test_INFO_INSERT(
						((params[ 0]==null || params[ 0].equals("[]"))) ?   -1 : (Integer.parseInt(params[0])),
						((params[ 1]==null || params[ 1].equals("[]"))) ?   -1 : (Integer.parseInt(params[1])),
											 (params[ 2].equals("[]") ) ?   -1 : (Integer.parseInt(params[2])),
						((params[ 3]==null || params[ 3].equals("[]"))) ?   -1 : (Integer.parseInt(params[3])),
						((params[ 4]==null || params[ 4].equals("[]"))) ?   -1 : (Integer.parseInt(params[4])),
						((params[ 5]==null || params[ 5].equals("[]"))) ?   -1 : (Integer.parseInt(params[5])),
						((params[ 6]==null || params[ 6].equals("[]"))) ? null :				   params[6],
						((params[ 7]==null || params[ 7].equals("[]"))) ?   -1 : (Integer.parseInt(params[7])),
						param8,
						((params[ 9]==null || params[ 9].equals("[]"))) ?   -1 : (Integer.parseInt(params[9])),
						((params[10]==null || params[10].equals("[]"))) ?   -1 : (Integer.parseInt(params[10])),
						((params[11]==null || params[11].equals("[]"))) ?   -1 : (Integer.parseInt(params[11])),
						ps);
				}
			}
		}
		if ( perf == 0 ) { out.println("// Skip la table MATRICE_DMSP "); }
		//
		// INSERT INTO Matrice_Patient (Synch)
		//
		if ( perf == 0 ) { out.println("// Insertion dans la table MATRICE_PATIENT "); }
		expr = xpath.compile( "//Table[name='Matrice_Patient']/T" );
		tuples = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
		for ( int idx = 0; idx < tuples.getLength(); ++idx )
		{	// read the tuples from the file and insert them...
			NamedNodeMap attrs = tuples.item(idx).getAttributes();
			String [] params = new String[ 9 ];
			params[ 0 ] = attrs.getNamedItem("i").getNodeValue();
			params[ 1 ] = attrs.getNamedItem("a").getNodeValue();
			params[ 2 ] = attrs.getNamedItem("p").getNodeValue();
			params[ 3 ] = attrs.getNamedItem("s").getNodeValue();
			// catch up next 9-4=5 values...
			NodeList values = tuples.item(idx).getChildNodes();
			for ( int n=0; n<values.getLength(); ++n )
			{	// as in csv file, here we have a complete "UserDMSP;i;a;p;s;value" line...
				params[ 4 + n%5 ] = values.item(n).getNodeValue();
				if ( n%5 == 5-1 )
				{	// completed gathering values for insert => do it now...

					// get the correct EP according to the value of the parameters
					int IdCategorie = (params[4]==null || params[4].equals("[]")) ? 0 : Integer.parseInt(params[4]),
						TypeCat     = (params[5]==null || params[5].equals("[]")) ? 0 : Integer.parseInt(params[5]),
						TypeActeur  = (params[7]==null || params[7].equals("[]")) ? 0 : Integer.parseInt(params[7]);
					if ( TypeActeur == Macro.ACTEUR_USERDMSP )
					{
						if ( IdCategorie == 0 )
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_AUTRE_USER );
						switch( TypeCat )
						{
						case Macro.CATEGORIE_EPISODE:
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_EPISODE_USER );		break;
						case Macro.CATEGORIE_FORMULAIRE:
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_FORMULAIRE_USER );	break;
						case Macro.CATEGORIE_USERDMSP:
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_USER_USER );			break;
						}
					}
					else if ( TypeActeur == Macro.ACTEUR_ROLE )
					{
						if ( IdCategorie == 0 )
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_AUTRE_ROLE );
						switch( TypeCat )
						{
						case Macro.CATEGORIE_EPISODE:
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_EPISODE_ROLE );		break;
						case Macro.CATEGORIE_FORMULAIRE:
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_FORMULAIRE_ROLE );	break;
						case Macro.CATEGORIE_USERDMSP:
							ps = db.prepareStatement( EP_Synchro.EP_PATIENT_INSERT_USER_ROLE );			break;
						}
					}
					Tools_dmsp.Test_PATIENT_INSERT(
						((params[0]==null || params[0].equals("[]"))) ? -1 : (Integer.parseInt(params[0])),
						((params[1]==null || params[1].equals("[]"))) ? -1 : (Integer.parseInt(params[1])),
											(params[2].equals("[]") ) ? -1 : (Integer.parseInt(params[2])),
						((params[3]==null || params[3].equals("[]"))) ? -1 : (Integer.parseInt(params[3])),
						((params[4]==null || params[4].equals("[]"))) ?  0 : IdCategorie,
						((params[5]==null || params[5].equals("[]"))) ? -1 : TypeCat,
						((params[6]==null || params[6].equals("[]"))) ? -1 : (Integer.parseInt(params[6])),
						((params[7]==null || params[7].equals("[]"))) ? -1 : TypeActeur,
						((params[8]==null || params[8].equals("[]"))) ? -1 : (Integer.parseInt(params[8])),
						ps);
				}
			}
		}
	}
*/
	///
	/// Returns an array of substrings of given string s separated with delim.
	/// If no delimeters found, then the entire input string is placed
	/// in the output array's first element
	/// Note that the input string s has to be not null
	///
	protected String[] strSplit( String s, String delim ) throws Exception
	{
		// count number of delim occured...
		java.util.Stack<String> st = new java.util.Stack<String>();
		int ntok = 1,
			pp = 0,
			p = s.indexOf(delim);
		while ( p >= 0 )
		{
			++ntok;
			st.push( s.substring(pp, p) );	// put token to the stack and
			pp = p + delim.length();		// move further...
			p = s.indexOf(delim, pp);
		}
		st.push( s.substring(pp) );
		// prepare resulting array...
		String[] retval = new String[ ntok ];
		for (p=ntok-1; p>=0; --p)
			retval[ p ] = st.pop();
		st = null;
		return retval;
	}
	/*
	 * compatibility layer not to break existing tests
	 */
	//XXX
	public void LoadDelta( Reader fr, Connection db ) throws Exception {
		LoadDelta( fr, false /* no CI */, db );
	}
	/*
	 * index: if true, use INSERT EP with climbing indexes
	 */
	public void LoadDelta( Reader fr, boolean index, Connection db ) throws Exception {
		int j,k; // for loops
		String[] params; // parameters for the insert functions
		String[] values; // values in each line
		// read the first line of the file, to get the number of tuples for each table
		// UserDMSP;
		// Role;
		// Habilitation;
		// Formulaire;
		// Episode;
		// Event;
		// Comment;
		// Info;
		// Matrice_DMSP;
		// Matrice_Patient;
		BufferedReader Delta = new BufferedReader(fr);
		// load the first line
		String Delta_line = Delta.readLine();
		// get the values in this line
		values = strSplit( Delta_line, ";" );

		// the prepared statement used for all inserts:
		PreparedStatement ps ;
		if(perf==0){out.println("Loading Delta.");}
		/*
		 *  INSERT INTO USER
		 */
		/* int IdGlobal,int Author,int TSSPT,int TSSanteos, String Nom,
		int Type, String Responsable, String Identifiant, int Civilite,
		String Prenom, String Adresse, String Ville, String CodePost,
		String Tel, String Mobile, String Courriel, int InfoLegale,
		byte[] Certificate,	int IdRole, */
		if(perf==0){out.println("// Insertion dans la table USERDMSP ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_INSERT);
		if(perf==0){out.println("// Insertion dans la table USERDMSP ");}
		// read the tuples from the file and insert them
		while(values[0].equals("UserDMSP")){
			// if there is no data, read the next line
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[19];
			// read a new tuple
			for(j=4;j<19;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}
			// if the certificate is longer than 20 bytes, cut it to be 22 bytes
			if(params[17].length()>20){
				params[17] = params[17].substring(0,20);
			}
			// insert the tuple
			Tools_dmsp.Test_USER_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[NULL]")))?null:params[4],
					((params[5]==null || params[5].equals("[NULL]")))?-1:(Integer.parseInt(params[5])),
					((params[6]==null || params[6].equals("[NULL]")))?null:params[6],
					((params[7]==null || params[7].equals("[NULL]")))?null:params[7],
					((params[8]==null || params[8].equals("[NULL]")))?-1:(Integer.parseInt(params[8])),
					((params[9]==null || params[9].equals("[NULL]")))?null:params[9],
					((params[10]==null || params[10].equals("[NULL]")))?null:params[10],
					((params[11]==null || params[11].equals("[NULL]")))?null:params[11],
					((params[12]==null || params[12].equals("[NULL]")))?null:params[12],
					((params[13]==null || params[13].equals("[NULL]")))?null:params[13],
					((params[14]==null || params[14].equals("[NULL]")))?null:params[14],
					((params[15]==null || params[15].equals("[NULL]")))?null:params[15],
					((params[16]==null || params[16].equals("[NULL]")))?-1:(Integer.parseInt(params[16])),
					((params[17]==null || params[17].equals("[NULL]")))?null:params[17],
					((params[18]==null || params[18].equals("[NULL]")))?-1:(Integer.parseInt(params[18])),
					ps);
		}
		/*
		 *  INSERT INTO ROLE
		 */
		if(perf==0){out.println("// Insertion dans la table ROLE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_INSERT);
		while(values[0].equals("Role")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[5];
			// read a new tuple
			for(j=4;j<5;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}
			Tools_dmsp.Test_ROLE_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
 					((params[4]==null || params[4].equals("[NULL]")))?null:params[4],
					ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_ROLE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/

/*
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_USER_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO HABILITATION
		 */
		if(perf==0){out.println("// Insertion dans la table HABILITATION ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_INSERT);
		while(values[0].equals("Habilitation")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[6];
			// read a new tuple
			for(j=4;j<6;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			Tools_dmsp.Test_HABILITATION_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[NULL]")))?-1:(Integer.parseInt(params[4])),
					((params[5]==null || params[5].equals("[NULL]")))?-1:(Integer.parseInt(params[5])),
 					ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_HABILITATION_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO Formulaire
		 */
		if(perf==0){out.println("// Insertion dans la table FORMULAIRE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_INSERT);
		while(values[0].equals("Formulaire")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[6];
			// read a new tuple
			for(j=4;j<6;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			Tools_dmsp.Test_FORMULAIRE_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
 					((params[4]==null || params[4].equals("[NULL]")))?null:params[4],
 					((params[5]==null || params[5].equals("[NULL]")))?-1:(Integer.parseInt(params[5])),
 					ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO Episode
		 */
		if(perf==0){out.println("// Insertion dans la table EPISODE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_INSERT);
		while(values[0].equals("Episode")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[5];
			// read a new tuple
			for(j=4;j<5;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			Tools_dmsp.Test_EPISODE_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
 					((params[4]==null || params[4].equals("[NULL]")))?null:params[4],
 					ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EPISODE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EVENT
		 */
		if(perf==0){out.println("// Insertion dans la table EVENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(index?DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT_SIGMOD:DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT);
		while(values[0].equals("Event")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[10];
			// read a new tuple
			for(j=4;j<10;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			// event date
			Date d1 = null, d2 = null;
			if(!(params[7]==null || params[7].equals("[NULL]"))){
//				String[] a = params[7].split("/");
				String[] a = strSplit( params[7], "/" );
				// if the separator of the date is not "/", try "[-]"
				Calendar cal = Calendar.getInstance();
				if(a.length == 1){
//					a = a[0].split("[-]");
					a = strSplit( a[0], "-" );
					cal.set(Calendar.YEAR, Integer.parseInt(a[0]));
					cal.set(Calendar.MONTH, (Integer.parseInt(a[1])-1));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]));
				}
				else{
					cal.set(Calendar.YEAR, Integer.parseInt(a[2]));
					cal.set(Calendar.MONTH, (Integer.parseInt(a[1])-1));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]));
				}
				d1 = new java.sql.Date(cal.getTime().getTime());
			}

			if(!(params[8]==null || params[8].equals("[NULL]"))){
//				String[] a = params[8].split("/");
				String[] a = strSplit( params[8], "/" );
				Calendar cal = Calendar.getInstance();
				if(a.length == 1){
//					a = a[0].split("[-]");
					a = strSplit( a[0], "-" );
					cal.set(Calendar.YEAR, Integer.parseInt(a[0]));
					cal.set(Calendar.MONTH, (Integer.parseInt(a[1])-1));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]));
				}
				else{
					cal.set(Calendar.YEAR, Integer.parseInt(a[2]));
					cal.set(Calendar.MONTH, (Integer.parseInt(a[1])-1));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]));
				}
				d2 = new java.sql.Date(cal.getTime().getTime());
			}

			Tools_dmsp.Test_EVENT_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[NULL]")))?-1:(Integer.parseInt(params[4])),
 					((params[5]==null || params[5].equals("[NULL]")))?-1:(Integer.parseInt(params[5])),
 					((params[6]==null || params[6].equals("[NULL]")))?-1:(Integer.parseInt(params[6])),
 					d1,d2,
 				 	((params[9]==null || params[9].equals("[NULL]")))?-1:(Integer.parseInt(params[9])),
 					ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EVENT_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO COMMENT
		 */
		if(perf==0){out.println("// Insertion dans la table COMMENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_INSERT);
		while(values[0].equals("Comment")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[5];
			// read a new tuple
			for(j=4;j<5;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			Tools_dmsp.Test_COMMENT_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
 					((params[4]==null || params[4].equals("[NULL]")))?null:params[4],
 					ps);
		}
		/*
		 *  INSERT INTO INFO
		 */
		if(perf==0){out.println("// Insertion dans la table INFO ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(index?DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT_SIGMOD:DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT);
		while(values[0].equals("Info")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[12];
			// read a new tuple
			for(j=4;j<12;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			// info.valdate
			Date d3 = null;
			if(!(params[8]==null || params[8].equals("[NULL]"))){
//				String[] a = params[8].split("/");
				String[] a = strSplit( params[8], "/" );
				Calendar cal = Calendar.getInstance();
				if(a.length == 1){
//					a = a[0].split("[-]");
					a = strSplit( a[0], "-" );
					cal.set(Calendar.YEAR, Integer.parseInt(a[0]));
					cal.set(Calendar.MONTH, (Integer.parseInt(a[1])-1));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]));
				}
				else{
					cal.set(Calendar.YEAR, Integer.parseInt(a[2]));
					cal.set(Calendar.MONTH, (Integer.parseInt(a[1])-1));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]));
				}
				d3 = new java.sql.Date(cal.getTime().getTime());
			}

			Tools_dmsp.Test_INFO_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[NULL]")))?-1:(Integer.parseInt(params[4])),
 					((params[5]==null || params[5].equals("[NULL]")))?-1:(Integer.parseInt(params[5])),
 					((params[6]==null || params[6].equals("[NULL]")))?null:params[6],
 					((params[7]==null || params[7].equals("[NULL]")))?-1:(Integer.parseInt(params[7])),
 					 d3,
					((params[9]==null || params[9].equals("[NULL]")))?-1:(Integer.parseInt(params[9])),
 					((params[10]==null || params[10].equals("[NULL]")))?-1:(Integer.parseInt(params[10])),
 					((params[11]==null || params[11].equals("[NULL]")))?-1:(Integer.parseInt(params[11])),
 					ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_INFO_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		// INSERT INTO MATRICE_DMSP (Synch)
		/*public static void Test_DMSP_INSERT(
				int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdForm,
				int IdRole, int Autorization,
				java.sql.PreparedStatement Pstmt) */
/*		if(perf==0){out.println("// Insertion dans la table MATRICE_DMSP ");}
		ps = db.prepareStatement(EP_Synchro.EP_DMSP_INSERT);
		while(values[0].equals("Matrice_DMSP")){
			if(values[1].equals("0")){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[7];
			// read a new tuple
			for(j=4;j<7;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
			}

			Tools_dmsp.Test_DMSP_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[NULL]")))?-1:(Integer.parseInt(params[4])),
 					((params[5]==null || params[5].equals("[NULL]")))?-1:(Integer.parseInt(params[5])),
					((params[6]==null || params[6].equals("[NULL]")))?-1:(Integer.parseInt(params[6])),
					ps);
		}*/
		if(perf==0){out.println("// Skip la table MATRICE_DMSP ");}
		while(values[0].equals("Matrice_DMSP")){
			// read a new line
			Delta_line = Delta.readLine();
			// get the attribute values
			values = strSplit( Delta_line, ";" );
		}

		// INSERT INTO Matrice_Patient (Synch)
		/*public static void Test_PATIENT_INSERT(
				int IdGlobal, int Author, int TSSPT, int TSSanteos, int IdCategorie,
				int TypeCat, int IdActeur, int TypeActeur, int Autorization,
				java.sql.PreparedStatement Pstmt)*/
		if(perf==0){out.println("// Insertion dans la table MATRICE_PATIENT ");}
		while(values[0].equals("Matrice_Patient")){
			if(values[1]==null){
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				values = strSplit( Delta_line, ";" );
				continue;
			}
			// read a new tuple
			params = new String[9];
			// read a new tuple
			for(j=4;j<9;j++){
				// get the attribute values
				for(k=1;k<6;k++){
					if(k<5)
						params[k-1] = values[k];
					else
						params[j] = values[k];
				}
				// read a new line
				Delta_line = Delta.readLine();
				// get the attribute values
				if(Delta_line == null){
					values[0] = "EOF"; // end of file
				}
				else {
					values = strSplit( Delta_line, ";" );
				}
			}

			// get the correct EP according to the value of the parameters
			int IdCategorie = (params[4]==null || params[4].equals("[NULL]"))?0:Integer.parseInt(params[4]);
			int TypeActeur = (params[7]==null || params[7].equals("[NULL]"))?0:Integer.parseInt(params[7]);
			int TypeCat = (params[5]==null || params[5].equals("[NULL]"))?0:Integer.parseInt(params[5]);
			if (IdCategorie == 0
					&& TypeActeur == Macro.ACTEUR_USERDMSP) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_AUTRE_USER);
			} else if (IdCategorie == 0
					&& TypeActeur == Macro.ACTEUR_ROLE) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_AUTRE_ROLE);
			}

			else if (TypeCat == Macro.CATEGORIE_EPISODE
					&& TypeActeur == Macro.ACTEUR_USERDMSP) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_EPISODE_USER);
			} else if (TypeCat == Macro.CATEGORIE_EPISODE
					&& TypeActeur == Macro.ACTEUR_ROLE) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_EPISODE_ROLE);
			}

			else if (TypeCat == Macro.CATEGORIE_FORMULAIRE
					&& TypeActeur == Macro.ACTEUR_USERDMSP) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_FORMULAIRE_USER);
			} else if (TypeCat == Macro.CATEGORIE_FORMULAIRE
					&& TypeActeur == Macro.ACTEUR_ROLE) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_FORMULAIRE_ROLE);
			}

			else if (TypeCat == Macro.CATEGORIE_USERDMSP
					&& TypeActeur == Macro.ACTEUR_USERDMSP) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_USER_USER);
			} else if (TypeCat == Macro.CATEGORIE_USERDMSP
					&& TypeActeur == Macro.ACTEUR_ROLE) {
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.
						EP_Synchro.EP_PATIENT_INSERT_USER_ROLE);
			}

			Tools_dmsp.Test_PATIENT_INSERT(
					((params[0]==null || params[0].equals("[NULL]")))?-1:(Integer.parseInt(params[0])),
 					((params[1]==null || params[1].equals("[NULL]")))?-1:(Integer.parseInt(params[1])),
 					(params[2].equals("[NULL]"))?-1:(Integer.parseInt(params[2])),
 					((params[3]==null || params[3].equals("[NULL]")))?-1:(Integer.parseInt(params[3])),
					((params[4]==null || params[4].equals("[NULL]")))?0:IdCategorie,
 					((params[5]==null || params[5].equals("[NULL]")))?-1:TypeCat,
					((params[6]==null || params[6].equals("[NULL]")))?-1:(Integer.parseInt(params[6])),
					((params[7]==null || params[7].equals("[NULL]")))?-1:TypeActeur,
					((params[8]==null || params[8].equals("[NULL]")))?-1:(Integer.parseInt(params[8])),
					ps);
		}
		Delta.close();
		return;
	}
}
