import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'milite.dart';

class ModificaMilite extends StatefulWidget {
  @override
  _ModificaMilite createState() => _ModificaMilite();
}

class _ModificaMilite extends State<ModificaMilite> {

  bool valoreSwitch1 = false;
  bool valoreSwitch2 = false;
  bool valoreSwitch3 = false;
  bool valoreSwitch4 = false;
  bool valoreSwitch5 = false;
  bool valoreSwitch6 = false;

  TextEditingController _nomeController = TextEditingController();
  TextEditingController _cognomeController = TextEditingController();
  TextEditingController _datadinascitaController = TextEditingController();
  TextEditingController _residenzaController = TextEditingController();

  @override
  Widget build(BuildContext context) {

    final Map<String, dynamic> args = ModalRoute.of(context)!.settings.arguments as Map<String, dynamic>;
    final String nomeArgs = args['nome'];
    final String cognomeArgs = args['cognome'];
    final String dataDiNascitaArgs = args['dataDiNascita'];
    final String residenzaArgs = args['residenza'];

    return Scaffold(
      appBar: AppBar(
        title: Text('Modifica Milite'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: EdgeInsets.fromLTRB(16, 85, 16, 50), // Distanza sinistra, sopra, destra, sotto
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextField(
                  controller: _nomeController,
                  decoration: InputDecoration(
                    hintText: nomeArgs,
                    labelText: 'Nome',
                    border: OutlineInputBorder(),
                  ),
                ),
                SizedBox(height: 16),
                TextField(
                  controller: _cognomeController,
                  decoration: InputDecoration(
                    hintText: cognomeArgs,
                    labelText: 'Cognome',
                    border: OutlineInputBorder(),
                  ),
                ),
                SizedBox(height: 16),
                TextField(
                  controller: _datadinascitaController,
                  decoration: InputDecoration(
                    hintText: dataDiNascitaArgs,
                    labelText: 'Data di nascita',
                    border: OutlineInputBorder(),
                  ),
                ),
                SizedBox(height: 16),
                TextField(
                  controller: _residenzaController,
                  decoration: InputDecoration(
                    hintText: residenzaArgs,
                    labelText: 'Residenza',
                    border: OutlineInputBorder(),
                  ),
                ),
                SizedBox(height: 30),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        Text('Grado 118 prima'),
                        Switch(
                          value: valoreSwitch1,
                          onChanged: (value) {
                            setState(() {
                              valoreSwitch1 = value;
                            });
                          },
                        ),
                        SizedBox(height: 5),
                        Text('Grado 118 seconda'),
                        Switch(
                          value: valoreSwitch2,
                          onChanged: (value) {
                            setState(() {
                              valoreSwitch2 = value;
                            });
                          },
                        ),
                        SizedBox(height: 5),
                        Text('Grado 118 terza'),
                        Switch(
                          value: valoreSwitch3,
                          onChanged: (value) {
                            setState(() {
                              valoreSwitch3 = value;
                            });
                          },
                        ),
                      ],
                    ),
                    Column(
                      children: [
                        Text('Grado H24 prima'),
                        Switch(
                          value: valoreSwitch4,
                          onChanged: (value) {
                            setState(() {
                              valoreSwitch4 = value;
                            });
                          },
                        ),
                        SizedBox(height: 5),
                        Text('Grado H24 seconda'),
                        Switch(
                          value: valoreSwitch5,
                          onChanged: (value) {
                            setState(() {
                              valoreSwitch5 = value;
                            });
                          },
                        ),
                        SizedBox(height: 5),
                        Text('Grado H24 terza'),
                        Switch(
                          value: valoreSwitch6,
                          onChanged: (value) {
                            setState(() {
                              valoreSwitch6 = value;
                            });
                          },
                        ),
                      ],
                    ),
                  ],
                ),
                SizedBox(height: 30),
                FilledButton(
                  onPressed: () async {
                    String nome = _nomeController.text;
                    String cognome = _cognomeController.text;
                    String dataDiNascita = _datadinascitaController.text;
                    String residenza = _residenzaController.text;

                    if (nome.isNotEmpty && cognome.isNotEmpty &&
                        dataDiNascita.isNotEmpty && residenza.isNotEmpty) {
                      Milite modificaMilite = Milite(
                        nome: nome,
                        cognome: cognome,
                        dataDiNascita: dataDiNascita,
                        residenza: residenza,
                        cognomeNomeSpinner: '$cognome $nome',
                        username: '$nome.$cognome',
                        grado118prima: valoreSwitch1,
                        grado118seconda: valoreSwitch2,
                        grado118terza: valoreSwitch3,
                        gradoh24prima: valoreSwitch4,
                        gradoh24seconda: valoreSwitch5,
                        gradoh24terza: valoreSwitch6,
                      );
                      Map<String, dynamic> militeMap = modificaMilite.toMap();//permette interazioni con il DB mappando il milite

                      QuerySnapshot querySnapshot = await FirebaseFirestore.instance
                          .collection('militi')
                          .where('nome', isEqualTo: nomeArgs)
                          .where('cognome', isEqualTo: cognomeArgs)
                          .where('dataDiNascita', isEqualTo: dataDiNascitaArgs)
                          .where('residenza', isEqualTo: residenzaArgs)
                          .get();
                      if (querySnapshot.docs.isNotEmpty) {
                        DocumentSnapshot documentSnapshot = querySnapshot.docs.first;
                        DocumentReference militeRef = documentSnapshot.reference;

                        //ottengo il valore di password, volontario e dipendente
                        String? currentPassword = documentSnapshot['password'];
                        bool? volontario = documentSnapshot['volontario'];
                        bool? dipendente = documentSnapshot['dipendente'];
                        //assegno il valore di password, volontario e dipendente alla mappa
                        militeMap['password'] = currentPassword;
                        militeMap['volontario'] = volontario;
                        militeMap['dipendente'] = dipendente;


                        //aggiornamento dei campi del documento
                        await militeRef.update(militeMap);
                      }

                      Fluttertoast.showToast(
                        msg: 'Milite Modificato',
                        toastLength: Toast.LENGTH_SHORT,
                        gravity: ToastGravity.BOTTOM,
                        timeInSecForIosWeb: 1,
                        backgroundColor: Colors.grey,
                        textColor: Colors.white,
                        fontSize: 16.0,
                      );
                    } else {
                      Fluttertoast.showToast(
                        msg: 'Tutti i campi devono essere compilati',
                        toastLength: Toast.LENGTH_SHORT,
                        gravity: ToastGravity.BOTTOM,
                        timeInSecForIosWeb: 1,
                        backgroundColor: Colors.grey,
                        textColor: Colors.white,
                        fontSize: 16.0,
                      );
                    }
                  },
                  child: Text('Modifica'),
                  style: ButtonStyle(
                    fixedSize: MaterialStateProperty.all<Size>(
                      Size(110, 43),
                    ),
                  ),
                ),
                //SizedBox(height: 50),
                //CircularProgressIndicator()
              ],
            ),
          ),
        ),
      ),
    );
  }
}