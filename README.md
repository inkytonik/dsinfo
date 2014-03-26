The dsinfo library
==================

The dsinfo library enables you to easily use Scala-side information in
implementations of embedded (internal) domain-specific languages.

dsinfo is implemented using Scala macros which are an experimental feature
of Scala 2.10 and 2.11.

The library is released under the GNU Lesser General Public License. See the
files `COPYING` and `COPYING.LESSER` for details.

Domain-specific entity names
============================

dsinfo enables you to use Scala val and def names as the names of
domain-specific program entities.
When implementing domain-specific internal languages in Scala, it is common
to want to print out error messages or debugging information that refers to
domain-specific entities by name.
Unfortunately, an internal DSL implementation usually doesn't have access
to the names that the DSL user has chosen at the Scala level.
dsinfo enables the implementation to get access to those names without
changing the DSL.

For example, you can use the library to define operations such as `twoargs`
in this example:

    val foobar = twoargs (1, "one")

The idea is that we want `twoargs` to call a method that we specify.
The method should be passed the arguments `1` and `"one"`, but also the
name of the value, in this case `"foobar"`.

dsinfo allows you to replace boilerplate where the user of your API has to
repeat the name of domain-specific entities.
For example, without dsinfo you would have to require the user to
explicitly provide the name as an extra argument, leading to duplication.

    val valtwoargs1 = twoargs ("valtwoargs1", 1, "one")

Downloading the library
=======================

The library is published in the Maven Central repository.
If you are using sbt you should include the following in your library
dependencies:

    "org.bitbucket.inkytonik.dsinfo" %% "dsinfo" % "0.4.0"

Building the library
====================

If you want to build the library, first clone this repository using
Mercurial.

Download and install the [Scala simple build tool](http://www.scala-sbt.org).

Once sbt is installed, invoke it in the dsmain project top level.
Switch to the `dsinfo` sub-project.

    dsinfo 0.4.0> project dsinfo

Then package that project.

    dsinfo 0.4.0> package

sbt will download all the necessary Scala compiler and library jars, build
the library, and package it as a jar file.
If all goes well, you should find the library jar in the `dsinfo/target`
directory under a sub-directory for the Scala version that is being used.
E.g., if the Scala version is 2.10, look in `dsinfo/target/scala_2.10` for
`dsinfo_2.10-VERSION.jar` where `VERSION` is the dsinfo library version.

Version 0.4.0 has been tested with sbt 0.13.1, Scala 2.11.0 and Java
1.7.0_51 running on Mac OS X 10.9.2.

Using the library (entity names)
================================

Suppose that we want to define the `twoargs` example shown above. We want
to construct values of the following type.

    case class TwoArgs (name : String, i : Int, s : String)

First, we need to import the macro feature and macro contexts.

    object TwoArgsMaker {

      import scala.language.experimental.macros
      import scala.reflect.macros.blackbox.Context

We now define the entry point as a `twoargs` method that is implemented by
a macro.
This method takes just the non-name arguments.

      def twoargs (i : Int, s : String) : TwoArgs =
        macro makeTwoArgsWithName

Our macro will replace the user's call with a call to another method.
In this case we call it `mkTwoArgs` and it simply creates a case class
instance and returns it.

      def mkTwoArgs (name : String, i : Int, s : String) : TwoArgs =
          TwoArgs (name, i, s)

There is no requirement that case classes be used.
The method that is called can do anything it likes with the arguments as
long as it returns the correct type.
It is also possible to overload the `towargs` name instead of using a
new name such as `mkTwoArgs`.
The method can also take type parameters.

The macro implementation is provided by `makeTwoArgsWithName`.
Most of the work is done by the dsinfo routine called `makeCallWithName`.

      import org.bitbucket.inkytonik.dsinfo.DSInfo.makeCallWithName

      def makeTwoArgsWithName (c : Context) (i : c.Expr[Int], s : c.Expr[String]) : c.Expr[TwoArgs] =
        makeCallWithName (c, "TwoArgsMaker.mkTwoArgs")

    }

As in all def macro implementations, the first argument list must be for
the macro `Context`.
The second argument list must contain one argument for each of the macro
arguments.
These are expressions that represent the values, not the actual values.
The return type is an expression of the type of the value that we
eventually want to return.

The body of `makeTwoArgsWithName` just has to call `makeCallWithName`.
The macro context is passed as the first argument.
The other arguments is the name of the method we want to call.

What does `makeCallWithName` do?
================================

`makeCallWithName` operates as follows.
It looks for a `val` or `def` whose definition is exactly the macro
invocation.
If such a definition is found, the name of the defined `val` or `def` is
extracted.
Otherwise, the name of the macro is used as the default name.

For example, in the following code

    object Foo {
        val x = twoargs (1, "one")
        def aMethod () {
            val y = twoargs (2, "two")
        }
        val z = Some (twoargs (3, "three"))
    }

The values bound to `x` and `y` will get the name `"x"` and `"y"`,
respectively.
In the definition of `z`, the macro invocation is embedded inside another
value that is then bound to a name.
The name `"z"` will be associated with each embedded invocation as well.
(Note: before version 0.4.0 the behaviour in this case was different.
An embedded value got the name of the macro, not the user-level name.)
These examples show values in an object and in a method.
The library also works for values in classes and traits, plus for `def`
definitions in all of these locations.

Once `makeCallWithName` has a name for an invocation it replaces that
invocation with a call of the method that was specified by its non-Context
arguments.
It passes the name first and then all of the other original arguments to
the macro invocation.
Thus, the code above is compiled as if you had written:

    object Foo {
        val x = TwoArgsMaker.mkTwoArgs ("x", 1, "one")
        def aMethod () {
            val y = TwoArgsMaker.mkTwoArgs ("y", 2, "two")
        }
        val z = Some (TwoArgsMaker.mkTwoArgs ("z", 3, "three"))
    }

Thus, the method `mkTwoArgs` can use the Scala names but the user does not
write anything more than a normal Scala `val` or `def` definition.

Method name variants
====================

The method name argument to `makeCallWithName` can specify the method to
call in a variety of ways.

If the method name argument is omitted, it defaults to the name of the
macro (`twoargs` in the example above.)

As shown in the example above, if the method name is qualified then the
method that is called comes from the specified object and/or package.

If the method name is unqualified then the method must exist in the scope
of the call of the macro.
E.g., using the name `"mkTwoArgs"` assumes that this method is in the
user's scope and generates a call to it.

Finally, as a special case, if the name is qualified and starts with a
`"this"` component then the call that is generated will be a call of a
method on the object on which the original macro call was made.
E.g., if the name is `"this.mkTwoARgs"` and the macro call is
`myObj.twoargs (1, "one")` then the call that is generated is
`myObj.mkTwoArgs ("name", 1, "one")` where `"name"` is the definition name.

In general, the method name argument can contain the sub-string `$macro`.
This sub-string is replaced by the name of the macro.
Thus, the default method name is `"$macro"`.

As a convenience, the entry point `makeThisCallWithName` is short-hand
for making a call using the method name `"this.$macro"`.
It only requires the `Context` argument.
