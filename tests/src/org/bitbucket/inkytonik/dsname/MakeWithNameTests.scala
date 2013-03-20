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

    // No argument tests (val)

    val valnoargs1 = noargs ()

    test ("a class level NoArgs val gets the correct name") {
        expectResult ("valnoargs1", "class level val value name") (valnoargs1.name)
    }

    def valNoArgsMethod : NoArgs = {
        val valnoargs2 = noargs ()
        valnoargs2
    }

    test ("a method level NoArgs val gets the correct name") {
        expectResult ("valnoargs2", "method level val value name") (valNoArgsMethod.name)
    }

    object valNoArgsObject {
        val valnoargs3 = noargs ()
    }

    test ("an object level NoArgs val gets the correct name") {
        expectResult ("valnoargs3", "object level val value name") (valNoArgsObject.valnoargs3.name)
    }

    trait valNoArgsTrait {
        val valnoargs4 = noargs ()
    }

    object valAnotherNoArgsObject extends valNoArgsTrait

    test ("a trait level NoArgs val gets the correct name") {
        expectResult ("valnoargs4", "trait level val value name") (valAnotherNoArgsObject.valnoargs4.name)
    }

    val valnoargs5 = Some (noargs ())

    test ("an embedded NoArgs val has a default name") {
        expectResult ("noargs", "embedded value val name") (valnoargs5.get.name)
    }

    // One argument tests

    val valonearg1 = onearg (Arg (1))

    test ("a class level OneArg val gets the correct name") {
        expectResult ("valonearg1", "class level val value name") (valonearg1.name)
    }

    test ("a class level OneArg val gets the correct Arg field") {
        expectResult (Arg (1), "class level val value a") (valonearg1.a)
    }

    def valOneArgMethod : OneArg = {
        val valonearg2 = onearg (Arg (2))
        valonearg2
    }

    test ("a method level OneArg val gets the correct name") {
        expectResult ("valonearg2", "method level value name") (valOneArgMethod.name)
    }

    test ("a method level OneArg val gets the correct Arg field") {
        expectResult (Arg (2), "method level val value a") (valOneArgMethod.a)
    }

    object valOneArgObject {
        val valonearg3 = onearg (Arg (3))
    }

    test ("an object level OneArg val gets the correct name") {
        expectResult ("valonearg3", "object level val value name") (valOneArgObject.valonearg3.name)
    }

    test ("an object level OneArg val gets the correct Arg field") {
        expectResult (Arg (3), "object level val value a") (valOneArgObject.valonearg3.a)
    }

    trait valOneArgTrait {
        val valonearg4 = onearg (Arg (4))
    }

    object valAnotherOneArgObject extends valOneArgTrait

    test ("a trait level OneArg val gets the correct name") {
        expectResult ("valonearg4", "trait level val value name") (valAnotherOneArgObject.valonearg4.name)
    }

    test ("a trait level OneArg val gets the correct Arg field") {
        expectResult (Arg (4), "trait level val value a") (valAnotherOneArgObject.valonearg4.a)
    }

    val valonearg5 = Some (onearg (Arg (5)))

    test ("an embedded OneArg val has a default name") {
        expectResult ("onearg", "embedded value val name") (valonearg5.get.name)
    }

    test ("an embedded OneArg val gets the correct Arg field") {
        expectResult (Arg (5), "embedded value val a") (valonearg5.get.a)
    }

    // Two argument tests

    val valtwoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs val gets the correct name") {
        expectResult ("valtwoargs1", "class level value val name") (valtwoargs1.name)
    }

    test ("a class level TwoArgs val gets the correct integer field") {
        expectResult (1, "class level val value i") (valtwoargs1.i)
    }

    test ("a class level TwoArgs val gets the correct string field") {
        expectResult ("one", "class level val value s") (valtwoargs1.s)
    }

    def valTwoArgsMethod : TwoArgs = {
        val valtwoargs2 = twoargs (2, "two")
        valtwoargs2
    }

    test ("a twoArgsMethod level TwoArgs val gets the correct name") {
        expectResult ("valtwoargs2", "twoArgsMethod level val value name") (valTwoArgsMethod.name)
    }

    test ("a twoArgsMethod level TwoArgs val gets the correct integer field") {
        expectResult (2, "twoArgsMethod level val value i") (valTwoArgsMethod.i)
    }

    test ("a twoArgsMethod level TwoArgs val gets the correct string field") {
        expectResult ("two", "twoArgsMethod level val value s") (valTwoArgsMethod.s)
    }

    object valTwoArgsObject {
        val valtwoargs3 = twoargs (3, "three")
    }

    test ("an object level TwoArgs val gets the correct name") {
        expectResult ("valtwoargs3", "object level val value name") (valTwoArgsObject.valtwoargs3.name)
    }

    test ("an object level TwoArgs val gets the correct integer field") {
        expectResult (3, "object level val value i") (valTwoArgsObject.valtwoargs3.i)
    }

    test ("an object level TwoArgs val gets the correct string field") {
        expectResult ("three", "object level val value s") (valTwoArgsObject.valtwoargs3.s)
    }

    trait valTwoArgsTrait {
        val valtwoargs4 = twoargs (4, "four")
    }

    object valAnotherTwoArgsObject extends valTwoArgsTrait

    test ("a trait level TwoArgs val gets the correct name") {
        expectResult ("valtwoargs4", "trait level val value name") (valAnotherTwoArgsObject.valtwoargs4.name)
    }

    test ("a trait level TwoArgs val gets the correct integer field") {
        expectResult (4, "trait level val value i") (valAnotherTwoArgsObject.valtwoargs4.i)
    }

    test ("a trait level TwoArgs val gets the correct string field") {
        expectResult ("four", "trait level val value s") (valAnotherTwoArgsObject.valtwoargs4.s)
    }

    val valtwoargs5 = Some (twoargs (5, "five"))

    test ("an embedded TwoArgs val has a default name") {
        expectResult ("twoargs", "embedded val value name") (valtwoargs5.get.name)
    }

    test ("an embedded TwoArgs val gets the correct integer field") {
        expectResult (5, "embedded val value i") (valtwoargs5.get.i)
    }

    test ("an embedded TwoArgs val gets the correct string field") {
        expectResult ("five", "embedded val value s") (valtwoargs5.get.s)
    }

}
