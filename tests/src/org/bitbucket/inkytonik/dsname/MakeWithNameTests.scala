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

    // No argument tests

    val noargs1 = noargs ()

    test ("a class level NoArgs gets the correct name") {
        expectResult ("noargs1", "class level value name") (noargs1.name)
    }

    def noArgsMethod : NoArgs = {
        val noargs2 = noargs ()
        noargs2
    }

    test ("a method level NoArgs gets the correct name") {
        expectResult ("noargs2", "noArgsMethod level value name") (noArgsMethod.name)
    }

    object noArgsObject {
        val noargs3 = noargs ()
    }

    test ("an object level NoArgs gets the correct name") {
        expectResult ("noargs3", "object level value name") (noArgsObject.noargs3.name)
    }

    trait noArgsTrait {
        val noargs4 = noargs ()
    }

    object anotherNoArgsObject extends noArgsTrait

    test ("a trait level NoArgs gets the correct name") {
        expectResult ("noargs4", "trait level value name") (anotherNoArgsObject.noargs4.name)
    }

    val noargs5 = Some (noargs ())

    test ("an embedded NoArgs has a default name") {
        expectResult ("noargs", "embedded value name") (noargs5.get.name)
    }

    // One argument tests

    val onearg1 = onearg (Arg (1))

    test ("a class level OneArgs gets the correct name") {
        expectResult ("onearg1", "class level value name") (onearg1.name)
    }

    test ("a class level OneArgs gets the correct Arg field") {
        expectResult (Arg (1), "class level value a") (onearg1.a)
    }

    def oneArgMethod : OneArg = {
        val onearg2 = onearg (Arg (2))
        onearg2
    }

    test ("a twoArgsMethod level OneArgs gets the correct name") {
        expectResult ("onearg2", "twoArgsMethod level value name") (oneArgMethod.name)
    }

    test ("a twoArgsMethod level OneArgs gets the correct Arg field") {
        expectResult (Arg (2), "twoArgsMethod level value a") (oneArgMethod.a)
    }

    object oneArgObject {
        val onearg3 = onearg (Arg (3))
    }

    test ("an object level OneArgs gets the correct name") {
        expectResult ("onearg3", "object level value name") (oneArgObject.onearg3.name)
    }

    test ("an object level OneArgs gets the correct Arg field") {
        expectResult (Arg (3), "object level value a") (oneArgObject.onearg3.a)
    }

    trait oneArgTrait {
        val onearg4 = onearg (Arg (4))
    }

    object anotherOneArgsObject extends oneArgTrait

    test ("a trait level OneArgs gets the correct name") {
        expectResult ("onearg4", "trait level value name") (anotherOneArgsObject.onearg4.name)
    }

    test ("a trait level OneArgs gets the correct Arg field") {
        expectResult (Arg (4), "trait level value a") (anotherOneArgsObject.onearg4.a)
    }

    val onearg5 = Some (onearg (Arg (5)))

    test ("an embedded OneArgs has a default name") {
        expectResult ("onearg", "embedded value name") (onearg5.get.name)
    }

    test ("an embedded OneArgs gets the correct Arg field") {
        expectResult (Arg (5), "embedded value a") (onearg5.get.a)
    }

    // Two argument tests

    val twoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs gets the correct name") {
        expectResult ("twoargs1", "class level value name") (twoargs1.name)
    }

    test ("a class level TwoArgs gets the correct integer field") {
        expectResult (1, "class level value i") (twoargs1.i)
    }

    test ("a class level TwoArgs gets the correct string field") {
        expectResult ("one", "class level value s") (twoargs1.s)
    }

    def twoArgsMethod : TwoArgs = {
        val twoargs2 = twoargs (2, "two")
        twoargs2
    }

    test ("a twoArgsMethod level TwoArgs gets the correct name") {
        expectResult ("twoargs2", "twoArgsMethod level value name") (twoArgsMethod.name)
    }

    test ("a twoArgsMethod level TwoArgs gets the correct integer field") {
        expectResult (2, "twoArgsMethod level value i") (twoArgsMethod.i)
    }

    test ("a twoArgsMethod level TwoArgs gets the correct string field") {
        expectResult ("two", "twoArgsMethod level value s") (twoArgsMethod.s)
    }

    object twoArgsObject {
        val twoargs3 = twoargs (3, "three")
    }

    test ("an object level TwoArgs gets the correct name") {
        expectResult ("twoargs3", "object level value name") (twoArgsObject.twoargs3.name)
    }

    test ("an object level TwoArgs gets the correct integer field") {
        expectResult (3, "object level value i") (twoArgsObject.twoargs3.i)
    }

    test ("an object level TwoArgs gets the correct string field") {
        expectResult ("three", "object level value s") (twoArgsObject.twoargs3.s)
    }

    trait twoArgsTrait {
        val twoargs4 = twoargs (4, "four")
    }

    object anotherTwoArgsObject extends twoArgsTrait

    test ("a trait level TwoArgs gets the correct name") {
        expectResult ("twoargs4", "trait level value name") (anotherTwoArgsObject.twoargs4.name)
    }

    test ("a trait level TwoArgs gets the correct integer field") {
        expectResult (4, "trait level value i") (anotherTwoArgsObject.twoargs4.i)
    }

    test ("a trait level TwoArgs gets the correct string field") {
        expectResult ("four", "trait level value s") (anotherTwoArgsObject.twoargs4.s)
    }

    val twoargs5 = Some (twoargs (5, "five"))

    test ("an embedded TwoArgs has a default name") {
        expectResult ("twoargs", "embedded value name") (twoargs5.get.name)
    }

    test ("an embedded TwoArgs gets the correct integer field") {
        expectResult (5, "embedded value i") (twoargs5.get.i)
    }

    test ("an embedded TwoArgs gets the correct string field") {
        expectResult ("five", "embedded value s") (twoargs5.get.s)
    }

}
