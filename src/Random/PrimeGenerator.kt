package Random

import kotlin.math.sqrt

//Basado en un ejercicio visto en Clean Code.
class GeneradorDePrimos {

    private val numerosTachados: MutableList<Boolean> = mutableListOf<Boolean>(false,false)
    private val resultado:MutableList<Int> = mutableListOf()

    fun generarPrimos(maximoValor:Int):List<Int>{
        if(maximoValor < 2)
            return emptyList()
        else{
            desmarcarEnteros(maximoValor)
            tacharMultiplos(maximoValor)
            moverPrimosAResultado()
            return resultado.toList()
        }
    }

    fun desmarcarEnteros(maximoValor: Int) {
        val longitudDeNumerosTachados = maximoValor - 1
        for(i in 2..longitudDeNumerosTachados)
            numerosTachados.add(i,true)
    }

    private fun obtenerLimitador(maximoValor:Int):Int = sqrt(maximoValor.toDouble()).toInt()

    private fun tacharMultiplos(maximoValor:Int){
        for(i in 2..obtenerLimitador(maximoValor))
            if (numerosTachados[i])
                tacharMultiplosDe(i)
    }

    private fun tacharMultiplosDe(multiplo:Int){
        for(j in (2*multiplo)..numerosTachados.size-1 step multiplo)
            numerosTachados[j] = false
    }

    private fun moverPrimosAResultado(){
        var j = 0
        for (i in 2 until numerosTachados.size)
            if(numerosTachados[i])
                resultado.add(j++,i)
    }

}

fun main() {
    val primes = GeneradorDePrimos()
    val primos = primes.generarPrimos(15)
    println(primos.size)
    primos.forEach{primo:Int -> print(" $primo")}
}