import kotlin.random.Random
val jugadores = mapOf("jug1" to "jugador 1","jug2" to "jugador 2")

/**
 * Hacer una ruleta rusa de 2 jugadores
 * TODO:
 * //- Mensajes de cosas
 * //- Hcaer la ruleta
 * //- Elegir cuantas balas hay en el cargador
 * //- Hcer que las balas se pongan en posiciones aleatorias
 * // - cara o cruz para ver quien empieza
 * //- Elegir si quiere dispararse o disparar a otro
 * //- Compribar si alguna bala se ha disparado
 * - En cada tiro un chuleta para calcular los chances
 */

fun historiaIntro() {
    val listahistoria = listOf(
    "Te despiertas en una habitación oscura, con solo una silla y una mesa frente a ti.",
    "No recuerdas cómo llegaste aquí, pero algo te dice que estás atrapado.",
    "En la mesa, ves una pistola cargada con una sola bala y una ruleta rusa.",
    "Una nota en la mesa dice: 'Para ganar tu libertad, debes jugar. No hay otra salida'.",
    "Te das cuenta de que estás en un juego mortal de ruleta rusa.",
    "Puedes escuchar un ruido siniestro proveniente de las paredes. El tiempo corre en tu contra.",
    "Las luces parpadean, y una voz mecánica resuena en la habitación: 'El juego está por comenzar'.",
    "Es momento de tomar una decisión: arriesgarte a jugar o esperar un destino incierto...",
    "Una pantalla gigante se enciende, mostrando un cronómetro que empieza a contar regresivamente.",
    "El sudor recorre tu frente mientras contemplas la decisión que tienes que tomar.",
    "¿Te arriesgarás a jugar o esperarás un destino incierto? El tiempo es limitado.",
    "Cada segundo que pasa aumenta la tensión y el peligro."
    )
    val aleatorio = Random.nextInt(0, 12)
    println(listahistoria[aleatorio])
}

fun pedirnumbalas():Int{
    var numebala = try {
        readln().toInt()
    }catch (e:Exception){
        println("Opcion no valida, solo habra 1 bala en el tambor")
        1
    }
    if (numebala < 1 || numebala >6){
        println("Opcion no valida, solo habra 1 bala en el tambor")
        numebala = 1
    }
    return numebala
}

fun cagartambor(numebala: Int) : MutableList<Int>{
    val tambor  = mutableListOf(0,0,0,0,0,0)
    val posilibre = mutableListOf(0,1,2,3,4,5)

    for (i in 1..numebala) {
        val aleatorio = posilibre.random()
        if (tambor[aleatorio] != 1){
            tambor[aleatorio] = 1
            posilibre.remove(aleatorio)
        }
    }
    println("Se ha cargado el tambor")
    println()
    return tambor
}
fun caraocruz():List<String>{
    var estado = false
    var eleccion: String
    do {
        eleccion = readln()
        if (eleccion.lowercase() == "cara"|| eleccion.lowercase() =="cruz"){
            estado = true
        }else{
            print("Opcion no valida: ")
        }
    }while (!estado)


    val aleatorio = Random.nextInt(1, 3)
    val suerte = if (aleatorio % 2 == 0) "cara" else "cruz"

    return if (suerte == eleccion) {
        println("Empiezas el primero")
        listOf("jug1","jug2")
    }else{
        println("Dirias que es mala suerte empezar segundo jugador 1?")
        listOf("jug2","jug1")
    }

}

fun opciones(jugador:String){
    println("Que quieres hacer ${jugadores[jugador]}?")
    println("1. Dispararte?")
    println("2. Disparar al oponente y luego a ti?")
}

fun elegiropcion(): Int {

    do {
        print("Que quieres hacer?: ")
        val opcion = try {
            readln().toInt()
        }catch (e:Exception){
            0
        }
        if (opcion < 1 || opcion > 2){
            println("Opcion no valida")
        }else{
            return opcion
        }
    }while (true)
}

fun disparo(tambor:MutableList<Int>, i:Int): Boolean{
    if (tambor[i] == 1){
        return true
    }else{
        return false
    }
}

fun jugar(tambor:MutableList<Int>){
    print("Cara o cruz jugador 1?: ")
    val empiezaprimero = caraocruz()
    for (i in 1..6){
        println()
        val jugador = if (i %2 == 0) empiezaprimero[1] else empiezaprimero[0]
        println("Turno ${i},${jugadores[jugador]}")
        opciones(jugador)
        val opcion = elegiropcion()
        if (opcion == 1){
            val seacaboyo = disparo(tambor,i-1)
            if (seacaboyo){
                println("Aun sigues dudando pero finalmente te decides, aprietas el gatillo")
                println("Y lo ultimo que recuerdas es un gran BOOM al lado de tu oreja")
                break
            }else{
                println("Fue algo arriesgado pero sigues en pie")
            }
        }else if (opcion == 2){
            val seacaboel = disparo(tambor,i-1)
            if (seacaboel){
                println("El pulso te tiembla mientras apuntas hacia tu oponente... ")
                println("BOOOOMMM ")
                println("parece que fue la opcion correcta")
                break
            }else{
                println("Parece que sigue en pie tu oponente, quizas no fue la mejor idea...")
                println("Ademas ahora te toca a ti")
            }
            val seacaboyo = disparo(tambor,i-1)
            if (seacaboyo){
                println("Aun sigues dudando pero finalmente te decides, aprietas el gatillo")
                println("Y lo ultimo que recuerdas es un gran BOOM al lado de tu oreja")
                break
            }else{
                println("Fue algo arriesgado pero sigues en pie")
            }
        }
    }
}

fun comprobarrespuesta():String{
    var estado2 = false
    var respuesta = readln()
    do {
        if (respuesta.lowercase() == "s"||respuesta.lowercase() == "n"){
            estado2 = true
        }else{
            print("Respuesta no valida(s,n): ")
            respuesta = readln()
        }
    }while (!estado2)
    return respuesta
}
fun cls(){
    for (i in 1..40) println()
}
fun main() {
    var estado = false
    do {
        historiaIntro()
        print("Cuantas balas quereis en el tambor: ")
        val balas = pedirnumbalas()
        val tamborcargado = cagartambor(balas)
        jugar(tamborcargado)
        print("Quiereis jugar de nuevo: ")
        val respuesta = comprobarrespuesta()
        if (respuesta == "n"){
            estado = true
        }else{
            cls()
        }
    }while (!estado)

}