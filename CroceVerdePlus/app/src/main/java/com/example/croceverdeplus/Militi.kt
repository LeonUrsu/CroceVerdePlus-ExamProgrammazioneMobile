package com.example.croceverdeplus

data class Militi(
    val username: String? = null,
    val password: String? = null,
    var nome: String? = null,
    var cognome: String? = null,
    var dataDiNascita: String? = null,
    var residenza: String? = null,
    val ruolo: String? = null,
    var grado: List<String>? = listOf("grado118prima", "grado118seconda", "grado118terza",
                                      "gradoh24prima","gradoh24seconda","gradoh24terza")) {


    constructor(nome: String?, cognome: String?,
        dataDiNascita: String?, residenza: String?, grado: String) : this() {
        this.nome = nome
        this.cognome = cognome
        this.dataDiNascita = dataDiNascita
        this.residenza = residenza
        this.grado = listOf(grado)

    }
}