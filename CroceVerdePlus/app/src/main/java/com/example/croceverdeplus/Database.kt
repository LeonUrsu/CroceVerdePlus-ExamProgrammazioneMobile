package com.example.croceverdeplus

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class Database {

    private val db = Firebase.firestore

    fun addUser(
        nome: String, cognome: String, dataDiNascita: String, residenza: String
    ) {

        val centralinisti = Centralinisti(nome, cognome, dataDiNascita, residenza)

        val username = nome + "." + cognome

        val password = (1..5).map {
                when (Random.nextInt(3)) {
                    0 -> Random.nextInt(48, 58).toChar() // Numeri da '0' a '9'
                    1 -> Random.nextInt(65, 91).toChar() // Lettere maiuscole da 'A' a 'Z'
                    else -> Random.nextInt(97, 123).toChar() // Lettere minuscole da 'a' a 'z'
                }
            }.joinToString("")

        val ruolo = "Centralinista"

        db.collection("centralinisti").add(centralinisti)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG, "DocumentSnapshot added with ID: ${documentReference.id}"
                )

                db.collection("centralinisti").document(documentReference.id)
                    .update("username", username)
                db.collection("centralinisti").document(documentReference.id)
                    .update("password", password)
                db.collection("centralinisti").document(documentReference.id).update("ruolo", ruolo)

            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun deleteUser(
        nome: String, cognome: String, dataDiNascita: String, residenza: String
    ) {

        db.collection("centralinisti").whereEqualTo("nome", nome).whereEqualTo("cognome", cognome)
            .whereEqualTo("dataDiNascita", dataDiNascita).whereEqualTo("residenza", residenza).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.data} => ${document.id}")
                    db.collection("centralinisti").document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(
                                TAG, "DocumentSnapshot successfully deleted!"
                            )
                        }.addOnFailureListener { e ->
                            Log.w(
                                TAG, "Error deleting document", e
                            )
                        }
                }
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

    /*
    Metodo per recuperare i militidal db
     */
    fun get_users(funzione_passata: (militi : MutableList<String>) -> Unit){
        db.collection("militi")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                var militi: MutableList<String> = mutableListOf<String>()
                //document.toObject<Tabella118>()
                for (document in result){
                    var nome_temp = document.getString("cognomeNomeSpinner")
                    if (nome_temp != null)
                        militi.add(nome_temp)
                }
                funzione_passata(militi) // TODO (queta funzione deve settare il valore segli spinner in centralinista)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun addUserM(
        nome: String, cognome: String, dataDiNascita: String, residenza: String,
        grado118prima: Boolean?, grado118seconda: Boolean?,
        grado118terza: Boolean?, gradoh24prima: Boolean?,
        gradoh24seconda: Boolean?, gradoh24terza: Boolean?
    ) {
        val milite = Milite(nome, cognome, dataDiNascita, residenza,
                            grado118prima, grado118seconda,
                            grado118terza, gradoh24prima,
                            gradoh24seconda, gradoh24terza)

        val username = nome + "." + cognome

        val password = (1..5).map {
                when (Random.nextInt(3)) {
                    0 -> Random.nextInt(48, 58).toChar() // Numeri da '0' a '9'
                    1 -> Random.nextInt(65, 91).toChar() // Lettere maiuscole da 'A' a 'Z'
                    else -> Random.nextInt(97, 123).toChar() // Lettere minuscole da 'a' a 'z'
                }
            }.joinToString("")

        val cognomenomespinner= nome + "" + cognome

        val ruolo = "Milite"

        db.collection("militi").add(milite).addOnSuccessListener { documentReference ->
                Log.d(
                    TAG, "DocumentSnapshot added with ID: ${documentReference.id}"
                )

                db.collection("militi").document(documentReference.id).update("username", username)
                db.collection("militi").document(documentReference.id).update("password", password)
                db.collection("militi").document(documentReference.id).update("ruolo", ruolo)
                db.collection("militi").document(documentReference.id).update("cognomeNomeSpinner", cognomenomespinner)

            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun deleteUserM(
        nome: String, cognome: String, dataDiNascita: String, residenza: String
    ) {

        db.collection("militi").whereEqualTo("nome", nome).whereEqualTo("cognome", cognome)
            .whereEqualTo("dataDiNascita", dataDiNascita).whereEqualTo("residenza", residenza).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.data} => ${document.id}")
                    db.collection("militi").document(document.id).delete().addOnSuccessListener {
                            Log.d(
                                TAG, "DocumentSnapshot successfully deleted!"
                            )
                        }.addOnFailureListener { e ->
                            Log.w(
                                TAG, "Error deleting document", e
                            )
                        }
                }
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

    /*
    Metodo per ricevere un array di tutti i militi presenti nel DB, i militi del
     */
    fun ricevi_array_militi(): ArrayList<Milite> {
        var array_militi = ArrayList<Milite>()
        return array_militi
    }

    /*
    fun trova_tabelle_e_date(): Boolean {
        var documento1 = ricevi_tabella_118_h24()
        var documento2 = ricevi_tabella_118_h24()
        if (documento1 == null || documento2 == null) return false
        var time1 = documento1.data_lunedi
        var time2 = documento2.data_lunedi
        if (time1 == null || time2 == null) return false
        var comparato = time1.compareTo(time2)
        if (comparato <= 0) {
            settimana1 = documento2
            settimana2 = documento1
            data_lunedi_settimana1 = Timestamp(time1.seconds * 1000)
            data_lunedi_settimana2 = Timestamp(time2.seconds * 1000)
        } else {
            settimana1 = documento2
            settimana2 = documento1
            data_lunedi_settimana1 = Timestamp(time2.seconds * 1000)
            data_lunedi_settimana2 = Timestamp(time1.seconds * 1000)
        }
        return true
    }
     */

    /*
    Metodo per ricevere i dati delle due tabelle, deve restituire le due tabelle in un ArrayOf<Tabella>
    in ordine cronologico
    */
    fun ricevi_tabella_118_h24() {
        val docRef = db.collection("tabelle").document("tabella_118_h24")
        docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    document.toObject<Tabella118>() //TODO (fare qualcosa con questo oggetto)
                } else {
                    Log.d(TAG, "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    /*
    Metodo per ricevere i dati delle due tabelle, deve restituire le due tabelle in un ArrayOf<Tabella>
    in ordine cronologico
    */
    fun ricevi_tabella_118() {
        val docRef = db.collection("tabelle").document("tabella_118")
        docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    document.toObject<Tabella118>() //TODO (fare qualcosa con questo oggetto)
                } else {
                    Log.d(TAG, "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }


    /*
    Metodo per segnare un milite nel turno passato tramite id come String, ogni casella ha un suo
    univoco id_casella, return "true" l'operazione è andata a buon fine, return "false" significa cheè già
    presente qualcuno nel turno
     */
    fun segna_milite_nel_turno(id_casella: String, nomeCognome_val: String): Boolean {
        return true // per ora ho impostato un valore fisso di ritorno true
    }

    /*
    Metodo per cancellare un milite nel turno passato tramite id come String, ogni casella ha un suo
    univoco id_casella, return "true" l'operazione è andata a buon fine, return "false" significa che
    non è presente nessun milite nel turno e quindi si è verificato un errore
     */
    fun cancella_milite_nel_turno(id_casella: String, nomeCognome_val: String): Boolean {
        return true // per ora ho impostato un valore fisso di ritorno true
    }
}
