package basics
private val name:String = "Jorge"
private var greeting:String? = null

fun main(){
    greeting = "Hello"
    println("If: ")
    if(greeting != null){
        println(greeting)
    }else{
        println("Hi")
    }
    println(name)
    println("When (Java Switch): ")
    when(greeting){
        null -> print("Hi,")
        else -> print("$greeting,")
    }
    println(name)
    println("Ternary Operator: ")
    val greetingToPrint = if(greeting != null) greeting else "Hi,"
    print(greetingToPrint)
    println(" " + name)
}
