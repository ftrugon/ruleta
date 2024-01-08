import kotlin.random.Random
val jugadores = mapOf("jug1" to "jugador 1","jug2" to "jugador 2")

/**
 * Imprime aleatoriamente una historia para introducirnos a la ruleta rusa
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

/**
 * Una funcion que pide un numero de balas, si metes una opcion incorrecta se contara como 1
 * @return el numero de balas
 */
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

/**
 * @param numebala: Es el numero de balas que habra en el tambor del revolver
 * @return una lista con el revolver cargado aleatoriamente
 */
fun cagartambor(numebala: Int) : MutableList<Int>{

    // De primeras el tambor siempre esta vacio, 0 = vacio y 1 = bala adentro
    val tambor  = mutableListOf(0,0,0,0,0,0)
    val posilibre = mutableListOf(0,1,2,3,4,5)

    // En cada iteracion comprueba si una posicion aleatoria esta libre y la elimina de la lista
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

/**
 * el jugador tendra que introducir cara o cruz y empezara primero o segundo
 * @return una lista de (jug1 , jug2) o (jug2 , jug1) dependiendo la aleatoriedad
 */
fun caraocruz():List<String>{

    //Un while para que el usuario ponga cara o cruz, no otra cosa

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

    // Aleatoriamente genera una lista u otra

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

/**
 * Un menu de opciones
 * @param jugador: Es el jugador que esta realizando la accion
 */
fun opciones(jugador:String){
    println("Que quieres hacer ${jugadores[jugador]}?")
    println("1. Dispararte?")
    println("2. Disparar al oponente y luego a ti?")
}



/**
 * Simplemente sirve para elegir entre 1 o 2
 * @return 1 o 2
 */

fun elegiropcion(): Int {
    while (true) {
        print("Que quieres hacer?: ")
        val opcion = try {
            readln().toInt()
        }catch (e:Exception){
            0
        }
        if (opcion < 1 || opcion > 2){
            println("Opcion no valida")
        }
        else{
            return opcion
        }
    }
}

/**
 * Compureba la posicion actual
 * @param tambor: es el cargador del revolver
 * @param i: es la posicion actual del revolver
 * @return true o false dependiendo si hay una bala o no
 */

fun disparo(tambor:MutableList<Int>, i:Int): Boolean{
    if (tambor[i] == 1){
        return true
    }else{
        return false
    }
}

/**
 * Jugar es la principal funcion de la ruleta
 * @param tambor : es el cargador del revolver, sobre el tambor se decide como va el juego y las cosas
 */
fun jugar(tambor:MutableList<Int>){
    //El jugador 1 elige entre cara o cruz y una funcion decide si empieza primero o segundo
    print("Cara o cruz jugador 1?: ")
    val empiezaprimero = caraocruz()

    //Como un cargador de revolver solo tiene 6 de capacidadd con un for de 6 es suficente
    for (i in 1..6){
        println()

        // Jugador es una variable para println, no sirve para mas
        val jugador = if (i %2 == 0) empiezaprimero[1] else empiezaprimero[0]
        println("Turno ${i},${jugadores[jugador]}")

        //le enseña un menu al jugador de lo que puede hacer
        opciones(jugador)

        // Una funcion para hacer al jugador elegir entre 1 o 2
        val opcion = elegiropcion()

        // si la opcion es 1 te disparas a ti
        // si la opcion es 2 le disparas al oponente y luego a ti
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

/**
 * Simplemente es una comprobacion de s o n
 * @return si o no
 */
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
/**
 * ClS es simplemente para limpliar la pantalla, no es python, este simplemente hace un println 40 veces
 */
fun cls(){
    for (i in 1..40) println()
}

/**
 * Main es la funcion donde se ejecuta todas las partes del codigo y cuando se acaba se pregunta si se quiere jugar de
 * nuevo
 */
fun main() {
    var estado = false
    do {
        //Imprime la historia
        historiaIntro()

        //En estas4 lineas se juega la partida
        print("Cuantas balas quereis en el tambor: ")
        val balas = pedirnumbalas()
        val tamborcargado = cagartambor(balas)
        jugar(tamborcargado)

        //Sirve para jugar otra partida o acabar
        print("Quiereis jugar de nuevo: ")
        val respuesta = comprobarrespuesta()
        if (respuesta == "n"){
            estado = true
        }else{
            cls()
        }
    }while (!estado)
}
