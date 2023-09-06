package com.example.croceverdeplus

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs


class GestioneCentralinista : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gestione_centralinista, container, false)

        val args: GestioneCentralinistaArgs by navArgs()

        val immProfilo: ImageView = root.findViewById(R.id.imageView4)
        immProfilo.setImageResource(R.drawable.accountimage)
        var nome: TextView = root.findViewById(R.id.nome_text_centralinista)
        var cognome: TextView = root.findViewById(R.id.cognome_text_centralinista)
        var dataDiNascita: TextView = root.findViewById(R.id.data_di_nascita_text_centralinista)
        var residenza: TextView = root.findViewById(R.id.indirizzo_milite_text)
        val button: Button = root.findViewById(R.id.cancella_centralinista_btn)

        val selectedUser = args.selectedUser

        val userParts = selectedUser.split("\n")
        nome.text = userParts[0]
        cognome.text = userParts[1]
        dataDiNascita.text = userParts[2]
        residenza.text = userParts[3]

        val data = Database()

        button.setOnClickListener{
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Conferma eliminazione")
            builder.setMessage("Sei sicuro di voler eliminare questo utente?")

            //aggiunge l'opzione "Sì" che eliminerà l'utente
            builder.setPositiveButton("Sì") { dialog, which ->

                data.deleteUser(
                    nome.text.toString(), cognome.text.toString(),
                    dataDiNascita.text.toString(), residenza.text.toString())

                Toast.makeText(requireActivity(), "Centralinista eliminato", Toast.LENGTH_SHORT)
                    .show()
            }
            //aggiunge l'opzione "No" che chiude il popup
            builder.setNegativeButton("No") { dialog, which ->
            }

            //mostra il popup di conferma
            val dialog = builder.create()
            dialog.show()
            val negativeButton: Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setTextColor(Color.parseColor("#2e7d19"))
            val positiveButton: Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setTextColor(Color.parseColor("#2e7d19"))
        }

        return root
    }


}