package com.example.croceverdeplus

data class Milite(
    var username: String? = null,
    val password: String? = null,
    var nome: String? = null,
    var cognome: String? = null,
    var dataDiNascita: String? = null,
    var residenza: String? = null,
    var volontario: Boolean? = null,
    var dipendente: Boolean? = null,
    var cognomeNomeSpinner: String? = null,
    var grado118prima: Boolean? = null,
    var grado118seconda: Boolean? = null,
    var grado118terza: Boolean? = null,
    var gradoh24prima: Boolean? = null,
    var gradoh24seconda: Boolean? = null,
    var gradoh24terza: Boolean? = null,
    var oreTurno118prima: Int? = 0,
    var oreTurno118seconda: Int? = 0,
    var oreTurno118terza: Int? = 0,
    var oreTurnoh24prima: Int? = 0,
    var oreTurnoh24seconda: Int? = 0,
    var oreTurnoh24terza: Int? = 0) {


    constructor(nome: String?, cognome: String?,
        dataDiNascita: String?, residenza: String?,
                grado118prima: Boolean?, grado118seconda: Boolean?,
                grado118terza: Boolean?, gradoh24prima: Boolean?,
                gradoh24seconda: Boolean?, gradoh24terza: Boolean?,
                volontario: Boolean?, dipendente: Boolean?) : this() {
        this.nome = nome
        this.cognome = cognome
        this.dataDiNascita = dataDiNascita
        this.residenza = residenza
        this.grado118prima = grado118prima
        this.grado118seconda = grado118seconda
        this.grado118terza = grado118terza
        this.gradoh24prima = gradoh24prima
        this.gradoh24seconda = gradoh24seconda
        this.gradoh24terza = gradoh24terza
        this.volontario = volontario
        this.dipendente = dipendente

    }
}