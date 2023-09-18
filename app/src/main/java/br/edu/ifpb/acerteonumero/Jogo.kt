package br.edu.ifpb.acerteonumero

import kotlin.random.Random

class Jogo {

    private var numeroSorteado:Int = Random.nextInt(1, 101)
    //constructor() {}

    fun sortear(): Int {
        return numeroSorteado
    }

}