This test updates Traitement (Formulaire 1013) as if it was modified via GUI.
It then dumps content of tables Event and Info and runs synchronisation
SELECTs to dump the data to be synchronized.

The same thing is done in com.gemalto.dmsp.synchro.SynchroPatServlet
when accessing to https://localhost:8443/DMSP/synchro/test

Before you access that URL, run empty database and both SPTpat and SPTpro webapps, then init database with THE SAME initial delta as this test, connect as MEDECIN to SPTpat, then run the URL above.
When disconnecting, delta will be produced and is available to save from SPTpro.

The decrypted delta which SPT produces for synchronization is in file 73.xml here.
