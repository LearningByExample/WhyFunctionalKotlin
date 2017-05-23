package org.learning.by.example

import org.junit.Test

//Small class and a function equals what was in Java
class TestSuite1 {

    class Wrapper<out Type : Any>(val value: Type) {
        fun <OtherType : Any> pipe(compose: (Type) -> OtherType) = Wrapper(compose.invoke(this.value))
    }
    fun <OtherType : Any> From(value: OtherType): Wrapper<OtherType> = Wrapper(value)

    fun <Type : Any> print(value: Type) = println(value)
    fun <Type : Any> text(value: Type) = value.toString()
    fun <Type : Any> reverse(value: Type) = value.toString().reversed()
    fun square(value: Int) = value.times(value)

    @Test
    fun test() {
        println("running ${this::class.java} test")

        From(12345)
            .pipe(this::reverse)
            .pipe(this::print)

        From("dlrow olleh")
            .pipe(this::reverse)
            .pipe(this::print)

        From(12545)
            .pipe(this::square)
            .pipe(this::text)
            .pipe(this::reverse)
            .pipe(this::print)

    }
}

//who need a wrapper class? just a pipe function attached to any type
class TestSuite2 {

    fun <Type : Any, OtherType : Any> Type.pipe(compose: (Type) -> OtherType) = compose.invoke(this)

    fun <Type : Any> print(value: Type) = println(value)
    fun <Type : Any> text(value: Type) = value.toString()
    fun <Type : Any> reverse(value: Type) = value.toString().reversed()
    fun square(value: Int) = value.times(value)

    @Test
    fun test() {
        println("running ${this::class.java} test")

        12545
            .pipe(this::reverse)
            .pipe(this::print)

        "dlrow olleh"
            .pipe(this::reverse)
            .pipe(this::print)

        12545
            .pipe(this::square)
            .pipe(this::text)
            .pipe(this::reverse)
            .pipe(this::print)

    }
}

//let's make our pipe an operator, infix it!
class TestSuite3 {

    infix fun <Type : Any, OtherType : Any> Type.pipe(compose: (Type) -> OtherType) = compose.invoke(this)

    fun <Type : Any> print(value: Type) = println(value)
    fun <Type : Any> text(value: Type) = value.toString()
    fun <Type : Any> reverse(value: Type) = value.toString().reversed()
    fun square(value: Int) = value.times(value)

    @Test
    fun test() {
        println("running ${this::class.java} test")

        12545 pipe this::reverse pipe this::print

        "dlrow olleh" pipe this::reverse pipe this::print

        12545 pipe this::square pipe this::text pipe this::reverse pipe this::print

    }
}

//if we create a companion object and create another operator we will have nicer syntax
class TestSuite4 {

    infix fun <Type : Any, OtherType : Any> Type.pipe(compose: (Type) -> OtherType) = compose.invoke(this)
    infix fun <Type : Any, OtherType : Any> Type.and(compose: (Type) -> OtherType) = this pipe compose

    companion object Do {
        fun <Type : Any> print(value: Type) = println(value)
        fun <Type : Any> text(value: Type) = value.toString()
        fun <Type : Any> reverse(value: Type) = value.toString().reversed()
        fun square(value: Int) = value.times(value)
    }

    @Test
    fun test() {
        println("running ${this::class.java} test")

        12545 pipe Do::reverse and Do::print

        "dlrow olleh" pipe Do::reverse and Do::print

        12545 pipe Do::square and Do::text and Do::reverse and Do::print

    }
}



//who need pipes? just connect things with normal Kotlin
class TestSuite5 {

    fun <Type : Any> Type.print() = println(this)
    fun Int.square() = this.times(this)
    fun <Type : Any> Type.text() = this.toString()
    fun <Type : Any> Type.reverse() = this.toString().reversed()

    @Test
    fun test() {
        println("running ${this::class.java} test")

        12545.reverse().print()

        "dlrow olleh".reverse().print()

        12545.square().text().reverse().print()

    }
}

