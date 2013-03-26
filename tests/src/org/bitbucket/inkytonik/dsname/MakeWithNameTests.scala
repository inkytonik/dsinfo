/**
 * This file is part of dsname.
 *
 * Copyright (C) 2013 Anthony M Sloane, Macquarie University.
 * Copyright (C) 2013 Matthew Roberts, Macquarie University.
 *
 * dsnaem is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * dsnaem is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with dsname.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.bitbucket.inkytonik.dsname

import org.scalatest.FunSuite

class MakeWithNameTests extends FunSuite {

    import NoArgsMaker.noargs
    import OneArgMaker.onearg
    import TwoArgsMaker.twoargs

    /**
     * Builder for `NoArgs`. It's here to test building from a local method
     * using the version from this class.
     */
    def mkNoArgs (name : String) : NoArgs =
        NoArgs (name)

    // No argument tests (val)

    val valnoargs1 = noargs ()

    test ("a class level NoArgs val is correctly built") {
        expectResult ("valnoargs1", "class level val value name") (valnoargs1.name)
    }

    def valNoArgsMethod : NoArgs = {
        val valnoargs2 = noargs ()
        valnoargs2
    }

    test ("a method level NoArgs val is correctly built") {
        expectResult ("valnoargs2", "method level val value name") (valNoArgsMethod.name)
    }

    object valNoArgsObject {
        val valnoargs3 = noargs ()
    }

    test ("an object level NoArgs val is correctly built") {
        expectResult ("valnoargs3", "object level val value name") (valNoArgsObject.valnoargs3.name)
    }

    trait valNoArgsTrait {
        val valnoargs4 = noargs ()
    }

    object valAnotherNoArgsObject extends valNoArgsTrait

    test ("a trait level NoArgs val is correctly built") {
        expectResult ("valnoargs4", "trait level val value name") (valAnotherNoArgsObject.valnoargs4.name)
    }

    val valnoargs5 = Some (noargs ())

    test ("an embedded NoArgs val is correctly built") {
        expectResult ("noargs", "embedded val value name") (valnoargs5.get.name)
    }

    // No argument tests (def)

    def defnoargs1 = noargs ()

    test ("a class level NoArgs def (apply) is correctly built") {
        expectResult ("defnoargs1", "class level def (apply) value name") (defnoargs1.name)
    }

    def defnoargs2 = {
        val i = 0
        noargs ()
    }

    test ("a class level NoArgs def (body) is correctly built") {
        expectResult ("defnoargs2", "class level def (body) value name") (defnoargs2.name)
    }

    def defNoArgsMethod : NoArgs = {
        def defnoargs3 = noargs ()
        defnoargs3
    }

    test ("a method level NoArgs def is correctly built") {
        expectResult ("defnoargs3", "method level def value name") (defNoArgsMethod.name)
    }

    object defNoArgsObject {
        def defnoargs4 = noargs ()
    }

    test ("an object level NoArgs def is correctly built") {
        expectResult ("defnoargs4", "object level def value name") (defNoArgsObject.defnoargs4.name)
    }

    trait defNoArgsTrait {
        def defnoargs5 = noargs ()
    }

    object defAnotherNoArgsObject extends defNoArgsTrait

    test ("a trait level NoArgs def is correctly built") {
        expectResult ("defnoargs5", "trait level def value name") (defAnotherNoArgsObject.defnoargs5.name)
    }

    def defnoargs6 = Some (noargs ())

    test ("an embedded NoArgs def is correctly built") {
        expectResult ("noargs", "embedded value def name") (defnoargs6.get.name)
    }

    // One argument tests (val)

    val valonearg1 = onearg (Arg (1))

    test ("a class level OneArg val is correctly built") {
        expectResult ("valonearg1", "class level val value name") (valonearg1.name)
        expectResult (Arg (1), "class level val value a") (valonearg1.a)
    }

    def valOneArgMethod : OneArg = {
        val valonearg2 = onearg (Arg (2))
        valonearg2
    }

    test ("a method level OneArg val is correctly built") {
        expectResult ("valonearg2", "method level value name") (valOneArgMethod.name)
        expectResult (Arg (2), "method level val value a") (valOneArgMethod.a)
    }

    object valOneArgObject {
        val valonearg3 = onearg (Arg (3))
    }

    test ("an object level OneArg val is correctly built") {
        expectResult ("valonearg3", "object level val value name") (valOneArgObject.valonearg3.name)
        expectResult (Arg (3), "object level val value a") (valOneArgObject.valonearg3.a)
    }

    trait valOneArgTrait {
        val valonearg4 = onearg (Arg (4))
    }

    object valAnotherOneArgObject extends valOneArgTrait

    test ("a trait level OneArg val is correctly built") {
        expectResult ("valonearg4", "trait level val value name") (valAnotherOneArgObject.valonearg4.name)
        expectResult (Arg (4), "trait level val value a") (valAnotherOneArgObject.valonearg4.a)
    }

    val valonearg5 = Some (onearg (Arg (5)))

    test ("an embedded OneArg val is correctly built") {
        expectResult ("onearg", "embedded value val name") (valonearg5.get.name)
        expectResult (Arg (5), "embedded value val a") (valonearg5.get.a)
    }

    // One argument tests (val)

    def defonearg1 = onearg (Arg (1))

    test ("a class level OneArg def (apply) is correctly built") {
        expectResult ("defonearg1", "class level def (apply) value name") (defonearg1.name)
        expectResult (Arg (1), "class level def (apply) value a") (defonearg1.a)
    }

    def defonearg2 = {
        val i = 0
        onearg (Arg (2))
    }

    test ("a class level OneArg def (body) is correctly built") {
        expectResult ("defonearg2", "class level def (body) value name") (defonearg2.name)
        expectResult (Arg (2), "class level def (body) value a") (defonearg2.a)
    }

    def defOneArgMethod : OneArg = {
        def defonearg3 = onearg (Arg (3))
        defonearg3
    }

    test ("a method level OneArg def is correctly built") {
        expectResult ("defonearg3", "method level def value name") (defOneArgMethod.name)
        expectResult (Arg (3), "method level def value a") (defOneArgMethod.a)
    }

    object defOneArgObject {
        def defonearg4 = onearg (Arg (4))
    }

    test ("an object level OneArg def is correctly built") {
        expectResult ("defonearg4", "object level def value name") (defOneArgObject.defonearg4.name)
        expectResult (Arg (4), "object level def value a") (defOneArgObject.defonearg4.a)
    }

    trait defOneArgTrait {
        def defonearg5 = onearg (Arg (5))
    }

    object defAnotherOneArgObject extends defOneArgTrait

    test ("a trait level OneArg def is correctly built") {
        expectResult ("defonearg5", "trait level def value name") (defAnotherOneArgObject.defonearg5.name)
        expectResult (Arg (5), "trait level def value a") (defAnotherOneArgObject.defonearg5.a)
    }

    def defonearg6 = Some (onearg (Arg (6)))

    test ("an embedded OneArg def is correctly built") {
        expectResult ("onearg", "embedded def value name") (defonearg6.get.name)
        expectResult (Arg (6), "embedded def value a") (defonearg6.get.a)
    }

    // Two argument tests (val)

    val valtwoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs val is correctly built") {
        expectResult ("valtwoargs1", "class level value val name") (valtwoargs1.name)
        expectResult (1, "class level val value i") (valtwoargs1.i)
        expectResult ("one", "class level val value s") (valtwoargs1.s)
    }

    def valTwoArgsMethod : TwoArgs = {
        val valtwoargs2 = twoargs (2, "two")
        valtwoargs2
    }

    test ("a twoArgsMethod level TwoArgs val is correctly built") {
        expectResult ("valtwoargs2", "twoArgsMethod level val value name") (valTwoArgsMethod.name)
        expectResult (2, "twoArgsMethod level val value i") (valTwoArgsMethod.i)
        expectResult ("two", "twoArgsMethod level val value s") (valTwoArgsMethod.s)
    }

    object valTwoArgsObject {
        val valtwoargs3 = twoargs (3, "three")
    }

    test ("an object level TwoArgs val is correctly built") {
        expectResult ("valtwoargs3", "object level val value name") (valTwoArgsObject.valtwoargs3.name)
        expectResult (3, "object level val value i") (valTwoArgsObject.valtwoargs3.i)
        expectResult ("three", "object level val value s") (valTwoArgsObject.valtwoargs3.s)
    }

    trait valTwoArgsTrait {
        val valtwoargs4 = twoargs (4, "four")
    }

    object valAnotherTwoArgsObject extends valTwoArgsTrait

    test ("a trait level TwoArgs val is correctly built") {
        expectResult ("valtwoargs4", "trait level val value name") (valAnotherTwoArgsObject.valtwoargs4.name)
        expectResult (4, "trait level val value i") (valAnotherTwoArgsObject.valtwoargs4.i)
        expectResult ("four", "trait level val value s") (valAnotherTwoArgsObject.valtwoargs4.s)
    }

    val valtwoargs5 = Some (twoargs (5, "five"))

    test ("an embedded TwoArgs val is correctly built") {
        expectResult ("twoargs", "embedded val value name") (valtwoargs5.get.name)
        expectResult (5, "embedded val value i") (valtwoargs5.get.i)
        expectResult ("five", "embedded val value s") (valtwoargs5.get.s)
    }

    // Two argument tests (def)

    def deftwoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs def (apply) is correctly built") {
        expectResult ("deftwoargs1", "class level def (apply) value name") (deftwoargs1.name)
        expectResult (1, "class level def (apply) value i") (deftwoargs1.i)
        expectResult ("one", "class level def (apply) value s") (deftwoargs1.s)
    }

    def deftwoargs2 = {
        val i = 0
        twoargs (2, "two")
    }

    test ("a class level TwoArgs def (body) is correctly built") {
        expectResult ("deftwoargs2", "class level def (body) value name") (deftwoargs2.name)
        expectResult (2, "class level def (body) value i") (deftwoargs2.i)
        expectResult ("two", "class level def (body) value s") (deftwoargs2.s)
    }

    def defTwoArgsMethod : TwoArgs = {
        def deftwoargs3 = twoargs (3, "three")
        deftwoargs3
    }

    test ("a method level TwoArgs def is correctly built") {
        expectResult ("deftwoargs3", "method level def value name") (defTwoArgsMethod.name)
        expectResult (3, "method level def value i") (defTwoArgsMethod.i)
        expectResult ("three", "method level def value s") (defTwoArgsMethod.s)
    }

    object defTwoArgsObject {
        def deftwoargs4 = twoargs (4, "four")
    }

    test ("an object level TwoArgs def is correctly built") {
        expectResult ("deftwoargs4", "object level def value name") (defTwoArgsObject.deftwoargs4.name)
        expectResult (4, "object level def value i") (defTwoArgsObject.deftwoargs4.i)
        expectResult ("four", "object level def value s") (defTwoArgsObject.deftwoargs4.s)
    }

    trait defTwoArgsTrait {
        def deftwoargs5 = twoargs (5, "five")
    }

    object defAnotherTwoArgsObject extends defTwoArgsTrait

    test ("a trait level TwoArgs def is correctly built") {
        expectResult ("deftwoargs5", "trait level def value name") (defAnotherTwoArgsObject.deftwoargs5.name)
        expectResult (5, "trait level def value i") (defAnotherTwoArgsObject.deftwoargs5.i)
        expectResult ("five", "trait level def value s") (defAnotherTwoArgsObject.deftwoargs5.s)
    }

    def deftwoargs6 = Some (twoargs (6, "six"))

    test ("an embedded TwoArgs def is correctly built") {
        expectResult ("twoargs", "embedded def value name") (deftwoargs6.get.name)
        expectResult (6, "embedded def value i") (deftwoargs6.get.i)
        expectResult ("six", "embedded def value s") (deftwoargs6.get.s)
    }

}
