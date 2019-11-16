package basics

//using type declaration.
private val name:String = "Jorge"
private var greeting:String? = null

//no type declaration
val name2 = "Jordi"
var greeting2 = "Hi"

fun main(){
    greeting = "Hello"
    print(greeting + ",")
    println(name)
}
