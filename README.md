The dsname library
==================

The dsname library enables you to easily use Scala val and def names as the
names of domain-specific program entities.

Specifically, you can use the library to define operations such as
`twoargs` in this example:

    val foobar = twoargs (1, "one")

The idea is that we want `twoargs` to call a method that we specify.
The method should be passed the arguments `1` and `"one"`, but also the
name of the value, in this case `"foobar"`.
In other words, the method is given access to the Scala name of the value.

The `dsname` library allows you to replace boilerplate where the user of
your API has to repeat the name of domain-specific entities.
For example, without `dsname` you would have to require the user to
explicitly provide the name as an extra argument, leading to duplication.

    val valtwoargs1 = twoargs ("valtwoargs1", 1, "one")

`dsname` is implemented using Scala macros which are an experimental feature
of Scala 2.10.

The library is released under the GNU Lesser General Public License. See the
files `COPYING` and `COPYING.LESSER` for details.

Downloading the library
=======================

We expect to publish the library in the Maven Central repository at some
point.

In the meantime, you will need to clone the repository, then build and
publish locally.

Building the library
====================

If you want to build the library, first clone this repository using
Mercurial.

Download and install the Scala simple build tool:

    http://www.scala-sbt.org

Run `sbt package` in the top-level of the project. sbt will download all the
necessary Scala compiler and library jars, build the library, and package it
as a jar file.

If all goes well, you should find the dsname library jar in the `target`
directory under a sub-directory for the Scala version that is being used.
E.g., if the Scala version is 2.10, look in `target/scala_2.10` for
`dsname.10-VERSION.jar` where `VERSION` is the dsname library version.

Version 0.1 has been tested with sbt 0.12.2, Scala 2.10.0 and Java
1.7.0_17 running on Mac OS X 10.8.3.

Using the library
=================

Suppose that we want to define the `twoargs` example shown above. We want
to construct values of the following type.

    case class TwoArgs (name : String, i : Int, s : String)

First, we need to import the macro feature and macro contexts.

    object TwoArgsMaker {

      import scala.language.experimental.macros
      import scala.reflect.macros.Context

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

The macro implementation is provided by `makeTwoArgsWithName`.
Most of the work is done by the `dsname` routine called `makeWithName`.

      import org.bitbucket.inkytonik.dsname.DSName.makeWithName

      def makeTwoArgsWithName (c : Context) (i : c.Expr[Int], s : c.Expr[String]) : c.Expr[TwoArgs] =
        makeWithName (c) ("TwoArgsMaker", "mkTwoArgs")

    }

As in all def macro implementations, the first argument list must be for
the macro `Context`.
The second argument list must contain one argument for each of the macro
arguments.
These are expressions that represent the values, not the actual values.
The return type is an expression of the type of the value that we
eventually want to return.

The body of `makeTwoArgsWithName` just has to call `makeWithName`.
The macro context must be passed in the first argument list.
The second argument list must contain the following items:

* the name of the object from which the method we want to call comes, and

* the name of the method we want to call.

`makeWithName` works as follows.
It looks for a `val` or `def` whose definition is exactly macro invocation.
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
In the definition of `z`, the macro invocation does not provide the whole
value, so the default name `"twoargs"` will be used.
These examples show values in an object and in a method.
The library also works for values in classes and traits, plus for `def`
definitions in all of these locations.

Once `makeWithName` has a name for an invocation it replaces that
invocation with a call of the method that was specified by its third
and fourth arguments.
It passes the name first and then all of the other original arguments
to the macro invocation.
Thus, the code above is compiled as if you had written:

    object Foo {
        val x = TwoArgsMaker.mkTwoArgs ("x", 1, "one")
        def aMethod () {
            val y = TwoArgsMaker.mkTwoArgs ("y", 2, "two")
        }
        val z = Some (TwoArgsMaker.mkTwoArgs ("twoargs", 3, "three"))
    }

Thus, the method `mkTwoArgs` can use the Scala names but the user does not
write anything more than a normal Scala `val` or `def` definition.
