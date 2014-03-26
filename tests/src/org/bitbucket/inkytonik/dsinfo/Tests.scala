/**
 * This file is part of dsinfo.
 *
 * Copyright (C) 2013-2014 Anthony M Sloane, Macquarie University.
 * Copyright (C) 2013-2014 Matthew Roberts, Macquarie University.
 *
 * dsname is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * dsname is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with dsinfo.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.bitbucket.inkytonik.dsinfo

import org.scalatest.FunSuite

class Tests extends FunSuite {

    import NoArgsMaker.noargs
    import OneArgMaker.onearg
    import TwoArgsMaker.twoargs
    import ThreeArgsMaker.threeargs

    /**
     * Builder for `NoArgs`. It's here to test building from a local method
     * using the version from this class.
     */
    def mkNoArgs (name : String) : NoArgs =
        NoArgs (name)

    // No argument tests (val)

    val valnoargs1 = noargs ()

    test ("a class level NoArgs val is correctly built") {
        assertResult ("valnoargs1", "class level val value name") (valnoargs1.name)
    }

    def valNoArgsMethod : NoArgs = {
        val valnoargs2 = noargs ()
        valnoargs2
    }

    test ("a method level NoArgs val is correctly built") {
        assertResult ("valnoargs2", "method level val value name") (valNoArgsMethod.name)
    }

    object valNoArgsObject {
        val valnoargs3 = noargs ()
    }

    test ("an object level NoArgs val is correctly built") {
        assertResult ("valnoargs3", "object level val value name") (valNoArgsObject.valnoargs3.name)
    }

    trait valNoArgsTrait {
        val valnoargs4 = noargs ()
    }

    object valAnotherNoArgsObject extends valNoArgsTrait

    test ("a trait level NoArgs val is correctly built") {
        assertResult ("valnoargs4", "trait level val value name") (valAnotherNoArgsObject.valnoargs4.name)
    }

    val valnoargs5 = Some (noargs ())

    test ("an embedded NoArgs val is correctly built") {
        assertResult ("valnoargs5", "embedded val value name") (valnoargs5.get.name)
    }

    test ("a closure-local NoArgs val is correctly built") {
        val valnoargs6 = noargs ()
        assertResult ("valnoargs6", "closure-local val value name") (valnoargs6.name)
    }

    // No argument tests (lazy val)

    lazy val lazyvalnoargs1 = noargs ()

    test ("a class level NoArgs lazy val is correctly built") {
        assertResult ("lazyvalnoargs1", "class level lazy val vallazyue name") (lazyvalnoargs1.name)
    }

    def lazyvalNoArgsMethod : NoArgs = {
        lazy val lazyvalnoargs2 = noargs ()
        lazyvalnoargs2
    }

    test ("a method level NoArgs lazy val is correctly built") {
        assertResult ("lazyvalnoargs2", "method level lazy val value name") (lazyvalNoArgsMethod.name)
    }

    object lazyvalNoArgsObject {
        lazy val lazyvalnoargs3 = noargs ()
    }

    test ("an object level NoArgs lazy val is correctly built") {
        assertResult ("lazyvalnoargs3", "object level lazy val value name") (lazyvalNoArgsObject.lazyvalnoargs3.name)
    }

    trait lazyvalNoArgsTrait {
        lazy val lazyvalnoargs4 = noargs ()
    }

    object lazyvalAnotherNoArgsObject extends lazyvalNoArgsTrait

    test ("a trait level NoArgs lazy val is correctly built") {
        assertResult ("lazyvalnoargs4", "trait level lazy val value name") (lazyvalAnotherNoArgsObject.lazyvalnoargs4.name)
    }

    lazy val lazyvalnoargs5 = Some (noargs ())

    test ("an embedded NoArgs lazy val is correctly built") {
        assertResult ("lazyvalnoargs5", "embedded lazy val value name") (lazyvalnoargs5.get.name)
    }

    test ("a closure-local NoArgs lazy val is correctly built") {
        lazy val lazyvalnoargs6 = noargs ()
        assertResult ("lazyvalnoargs6", "closure-local lazy val value name") (lazyvalnoargs6.name)
    }

    // No argument tests (def)

    def defnoargs1 = noargs ()

    test ("a class level NoArgs def (apply) is correctly built") {
        assertResult ("defnoargs1", "class level def (apply) value name") (defnoargs1.name)
    }

    def defnoargs2 = {
        val i = 0
        noargs ()
    }

    test ("a class level NoArgs def (body) is correctly built") {
        assertResult ("defnoargs2", "class level def (body) value name") (defnoargs2.name)
    }

    def defNoArgsMethod : NoArgs = {
        def defnoargs3 = noargs ()
        defnoargs3
    }

    test ("a method level NoArgs def is correctly built") {
        assertResult ("defnoargs3", "method level def value name") (defNoArgsMethod.name)
    }

    object defNoArgsObject {
        def defnoargs4 = noargs ()
    }

    test ("an object level NoArgs def is correctly built") {
        assertResult ("defnoargs4", "object level def value name") (defNoArgsObject.defnoargs4.name)
    }

    trait defNoArgsTrait {
        def defnoargs5 = noargs ()
    }

    object defAnotherNoArgsObject extends defNoArgsTrait

    test ("a trait level NoArgs def is correctly built") {
        assertResult ("defnoargs5", "trait level def value name") (defAnotherNoArgsObject.defnoargs5.name)
    }

    def defnoargs6 = Some (noargs ())

    test ("an embedded NoArgs def is correctly built") {
        assertResult ("defnoargs6", "embedded value def name") (defnoargs6.get.name)
    }

    test ("a closure-local NoArgs def is correctly built") {
        def defnoargs7 = noargs ()
        assertResult ("defnoargs7", "closure-local def value name") (defnoargs7.name)
    }

    // One argument tests (val)

    val valonearg1 = onearg (Arg (1))

    test ("a class level OneArg val is correctly built") {
        assertResult ("valonearg1", "class level val value name") (valonearg1.name)
        assertResult (Arg (1), "class level val value a") (valonearg1.a)
    }

    def valOneArgMethod : OneArg = {
        val valonearg2 = onearg (Arg (2))
        valonearg2
    }

    test ("a method level OneArg val is correctly built") {
        assertResult ("valonearg2", "method level val value name") (valOneArgMethod.name)
        assertResult (Arg (2), "method level val value a") (valOneArgMethod.a)
    }

    object valOneArgObject {
        val valonearg3 = onearg (Arg (3))
    }

    test ("an object level OneArg val is correctly built") {
        assertResult ("valonearg3", "object level val value name") (valOneArgObject.valonearg3.name)
        assertResult (Arg (3), "object level val value a") (valOneArgObject.valonearg3.a)
    }

    trait valOneArgTrait {
        val valonearg4 = onearg (Arg (4))
    }

    object valAnotherOneArgObject extends valOneArgTrait

    test ("a trait level OneArg val is correctly built") {
        assertResult ("valonearg4", "trait level val value name") (valAnotherOneArgObject.valonearg4.name)
        assertResult (Arg (4), "trait level val value a") (valAnotherOneArgObject.valonearg4.a)
    }

    val valonearg5 = Some (onearg (Arg (5)))

    test ("an embedded OneArg val is correctly built") {
        assertResult ("valonearg5", "embedded value val name") (valonearg5.get.name)
        assertResult (Arg (5), "embedded value val a") (valonearg5.get.a)
    }

    test ("a closure-local OneArg val is correctly built") {
        val valonearg6 = onearg (Arg (6))
        assertResult ("valonearg6", "closure-local val value name") (valonearg6.name)
        assertResult (Arg (6), "closure-local val value a") (valonearg6.a)
    }

    // One argument tests (lazy val)

    lazy val lazyvalonearg1 = onearg (Arg (1))

    test ("a class level OneArg lazy val is correctly built") {
        assertResult ("lazyvalonearg1", "class level lazy val value name") (lazyvalonearg1.name)
        assertResult (Arg (1), "class level lazy val value a") (lazyvalonearg1.a)
    }

    def lazyvalOneArgMethod : OneArg = {
        lazy val lazyvalonearg2 = onearg (Arg (2))
        lazyvalonearg2
    }

    test ("a method level OneArg lazy val is correctly built") {
        assertResult ("lazyvalonearg2", "method level lazy val value name") (lazyvalOneArgMethod.name)
        assertResult (Arg (2), "method level val value a") (lazyvalOneArgMethod.a)
    }

    object lazyvalOneArgObject {
        lazy val lazyvalonearg3 = onearg (Arg (3))
    }

    test ("an object level OneArg lazy val is correctly built") {
        assertResult ("lazyvalonearg3", "object level lazy val value name") (lazyvalOneArgObject.lazyvalonearg3.name)
        assertResult (Arg (3), "object level lazy val value a") (lazyvalOneArgObject.lazyvalonearg3.a)
    }

    trait lazyvalOneArgTrait {
        val lazyvalonearg4 = onearg (Arg (4))
    }

    object lazyvalAnotherOneArgObject extends lazyvalOneArgTrait

    test ("a trait level OneArg lazy val is correctly built") {
        assertResult ("lazyvalonearg4", "trait level lazy val value name") (lazyvalAnotherOneArgObject.lazyvalonearg4.name)
        assertResult (Arg (4), "trait level lazy val value a") (lazyvalAnotherOneArgObject.lazyvalonearg4.a)
    }

    val lazyvalonearg5 = Some (onearg (Arg (5)))

    test ("an embedded OneArg lazy val is correctly built") {
        assertResult ("lazyvalonearg5", "embedded value lazy val name") (lazyvalonearg5.get.name)
        assertResult (Arg (5), "embedded value lazy val a") (lazyvalonearg5.get.a)
    }

    test ("a closure-local OneArg lazy val is correctly built") {
        lazy val lazyvalonearg6 = onearg (Arg (6))
        assertResult ("lazyvalonearg6", "closure-local lazy val value name") (lazyvalonearg6.name)
        assertResult (Arg (6), "closure-local lazy val a") (lazyvalonearg6.a)
    }

    // One argument tests (def)

    def defonearg1 = onearg (Arg (1))

    test ("a class level OneArg def (apply) is correctly built") {
        assertResult ("defonearg1", "class level def (apply) value name") (defonearg1.name)
        assertResult (Arg (1), "class level def (apply) value a") (defonearg1.a)
    }

    def defonearg2 = {
        val i = 0
        onearg (Arg (2))
    }

    test ("a class level OneArg def (body) is correctly built") {
        assertResult ("defonearg2", "class level def (body) value name") (defonearg2.name)
        assertResult (Arg (2), "class level def (body) value a") (defonearg2.a)
    }

    def defOneArgMethod : OneArg = {
        def defonearg3 = onearg (Arg (3))
        defonearg3
    }

    test ("a method level OneArg def is correctly built") {
        assertResult ("defonearg3", "method level def value name") (defOneArgMethod.name)
        assertResult (Arg (3), "method level def value a") (defOneArgMethod.a)
    }

    object defOneArgObject {
        def defonearg4 = onearg (Arg (4))
    }

    test ("an object level OneArg def is correctly built") {
        assertResult ("defonearg4", "object level def value name") (defOneArgObject.defonearg4.name)
        assertResult (Arg (4), "object level def value a") (defOneArgObject.defonearg4.a)
    }

    trait defOneArgTrait {
        def defonearg5 = onearg (Arg (5))
    }

    object defAnotherOneArgObject extends defOneArgTrait

    test ("a trait level OneArg def is correctly built") {
        assertResult ("defonearg5", "trait level def value name") (defAnotherOneArgObject.defonearg5.name)
        assertResult (Arg (5), "trait level def value a") (defAnotherOneArgObject.defonearg5.a)
    }

    def defonearg6 = Some (onearg (Arg (6)))

    test ("an embedded OneArg def is correctly built") {
        assertResult ("defonearg6", "embedded def value name") (defonearg6.get.name)
        assertResult (Arg (6), "embedded def value a") (defonearg6.get.a)
    }

    test ("a closure-local OneArg def is correctly built") {
        def defnoargs7 = onearg (Arg (7))
        assertResult ("defnoargs7", "closure-local def value name") (defnoargs7.name)
        assertResult (Arg (7), "closure-local def value a") (defnoargs7.a)
    }

    // Two argument tests (val)

    val valtwoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs val is correctly built") {
        assertResult ("valtwoargs1", "class level val value name") (valtwoargs1.name)
        assertResult (1, "class level val value i") (valtwoargs1.i)
        assertResult ("one", "class level val value s") (valtwoargs1.s)
    }

    def valTwoArgsMethod : TwoArgs = {
        val valtwoargs2 = twoargs (2, "two")
        valtwoargs2
    }

    test ("a twoArgsMethod level TwoArgs val is correctly built") {
        assertResult ("valtwoargs2", "twoArgsMethod level val value name") (valTwoArgsMethod.name)
        assertResult (2, "twoArgsMethod level val value i") (valTwoArgsMethod.i)
        assertResult ("two", "twoArgsMethod level val value s") (valTwoArgsMethod.s)
    }

    object valTwoArgsObject {
        val valtwoargs3 = twoargs (3, "three")
    }

    test ("an object level TwoArgs val is correctly built") {
        assertResult ("valtwoargs3", "object level val value name") (valTwoArgsObject.valtwoargs3.name)
        assertResult (3, "object level val value i") (valTwoArgsObject.valtwoargs3.i)
        assertResult ("three", "object level val value s") (valTwoArgsObject.valtwoargs3.s)
    }

    trait valTwoArgsTrait {
        val valtwoargs4 = twoargs (4, "four")
    }

    object valAnotherTwoArgsObject extends valTwoArgsTrait

    test ("a trait level TwoArgs val is correctly built") {
        assertResult ("valtwoargs4", "trait level val value name") (valAnotherTwoArgsObject.valtwoargs4.name)
        assertResult (4, "trait level val value i") (valAnotherTwoArgsObject.valtwoargs4.i)
        assertResult ("four", "trait level val value s") (valAnotherTwoArgsObject.valtwoargs4.s)
    }

    val valtwoargs5 = Some (twoargs (5, "five"))

    test ("an embedded TwoArgs val is correctly built") {
        assertResult ("valtwoargs5", "embedded val value name") (valtwoargs5.get.name)
        assertResult (5, "embedded val value i") (valtwoargs5.get.i)
        assertResult ("five", "embedded val value s") (valtwoargs5.get.s)
    }

    test ("a closure-local TwoArgs val is correctly built") {
        val valtwoargs6 = twoargs (6, "six")
        assertResult ("valtwoargs6", "closure-local val value name") (valtwoargs6.name)
        assertResult (6, "closure-local val value i") (valtwoargs6.i)
        assertResult ("six", "closure-local val value s") (valtwoargs6.s)
    }

    // Two argument tests (lazy val)

    lazy val lazyvaltwoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs lazy val is correctly built") {
        assertResult ("lazyvaltwoargs1", "class level lazy val value name") (lazyvaltwoargs1.name)
        assertResult (1, "class level lazy val value i") (lazyvaltwoargs1.i)
        assertResult ("one", "class level lazy val value s") (lazyvaltwoargs1.s)
    }

    def lazyvalTwoArgsMethod : TwoArgs = {
        lazy val lazyvaltwoargs2 = twoargs (2, "two")
        lazyvaltwoargs2
    }

    test ("a twoArgsMethod level TwoArgs lazy val is correctly built") {
        assertResult ("lazyvaltwoargs2", "twoArgsMethod level lazy val value name") (lazyvalTwoArgsMethod.name)
        assertResult (2, "twoArgsMethod level lazy val value i") (lazyvalTwoArgsMethod.i)
        assertResult ("two", "twoArgsMethod level lazy val value s") (lazyvalTwoArgsMethod.s)
    }

    object lazyvalTwoArgsObject {
        lazy val lazyvaltwoargs3 = twoargs (3, "three")
    }

    test ("an object level TwoArgs lazy val is correctly built") {
        assertResult ("lazyvaltwoargs3", "object level lazy val value name") (lazyvalTwoArgsObject.lazyvaltwoargs3.name)
        assertResult (3, "object level lazy val value i") (lazyvalTwoArgsObject.lazyvaltwoargs3.i)
        assertResult ("three", "object level lazy val value s") (lazyvalTwoArgsObject.lazyvaltwoargs3.s)
    }

    trait lazyvalTwoArgsTrait {
        lazy val lazyvaltwoargs4 = twoargs (4, "four")
    }

    object lazyvalAnotherTwoArgsObject extends lazyvalTwoArgsTrait

    test ("a trait level TwoArgs lazy val is correctly built") {
        assertResult ("lazyvaltwoargs4", "trait level lazy val value name") (lazyvalAnotherTwoArgsObject.lazyvaltwoargs4.name)
        assertResult (4, "trait level lazy val value i") (lazyvalAnotherTwoArgsObject.lazyvaltwoargs4.i)
        assertResult ("four", "trait level lazy val value s") (lazyvalAnotherTwoArgsObject.lazyvaltwoargs4.s)
    }

    lazy val lazyvaltwoargs5 = Some (twoargs (5, "five"))

    test ("an embedded TwoArgs lazy val is correctly built") {
        assertResult ("lazyvaltwoargs5", "embedded lazy val value name") (lazyvaltwoargs5.get.name)
        assertResult (5, "embedded lazy val value i") (lazyvaltwoargs5.get.i)
        assertResult ("five", "embedded lazy val value s") (lazyvaltwoargs5.get.s)
    }

    test ("a closure-local TwoArgs lazy val is correctly built") {
        val lazyvaltwoargs6 = twoargs (6, "six")
        assertResult ("lazyvaltwoargs6", "closure-local lazy val value name") (lazyvaltwoargs6.name)
        assertResult (6, "closure-local lazy val value i") (lazyvaltwoargs6.i)
        assertResult ("six", "closure-local lazy val value s") (lazyvaltwoargs6.s)
    }

    // Two argument tests (def)

    def deftwoargs1 = twoargs (1, "one")

    test ("a class level TwoArgs def (apply) is correctly built") {
        assertResult ("deftwoargs1", "class level def (apply) value name") (deftwoargs1.name)
        assertResult (1, "class level def (apply) value i") (deftwoargs1.i)
        assertResult ("one", "class level def (apply) value s") (deftwoargs1.s)
    }

    def deftwoargs2 = {
        val i = 0
        twoargs (2, "two")
    }

    test ("a class level TwoArgs def (body) is correctly built") {
        assertResult ("deftwoargs2", "class level def (body) value name") (deftwoargs2.name)
        assertResult (2, "class level def (body) value i") (deftwoargs2.i)
        assertResult ("two", "class level def (body) value s") (deftwoargs2.s)
    }

    def defTwoArgsMethod : TwoArgs = {
        def deftwoargs3 = twoargs (3, "three")
        deftwoargs3
    }

    test ("a method level TwoArgs def is correctly built") {
        assertResult ("deftwoargs3", "method level def value name") (defTwoArgsMethod.name)
        assertResult (3, "method level def value i") (defTwoArgsMethod.i)
        assertResult ("three", "method level def value s") (defTwoArgsMethod.s)
    }

    object defTwoArgsObject {
        def deftwoargs4 = twoargs (4, "four")
    }

    test ("an object level TwoArgs def is correctly built") {
        assertResult ("deftwoargs4", "object level def value name") (defTwoArgsObject.deftwoargs4.name)
        assertResult (4, "object level def value i") (defTwoArgsObject.deftwoargs4.i)
        assertResult ("four", "object level def value s") (defTwoArgsObject.deftwoargs4.s)
    }

    trait defTwoArgsTrait {
        def deftwoargs5 = twoargs (5, "five")
    }

    object defAnotherTwoArgsObject extends defTwoArgsTrait

    test ("a trait level TwoArgs def is correctly built") {
        assertResult ("deftwoargs5", "trait level def value name") (defAnotherTwoArgsObject.deftwoargs5.name)
        assertResult (5, "trait level def value i") (defAnotherTwoArgsObject.deftwoargs5.i)
        assertResult ("five", "trait level def value s") (defAnotherTwoArgsObject.deftwoargs5.s)
    }

    def deftwoargs6 = Some (twoargs (6, "six"))

    test ("an embedded TwoArgs def is correctly built") {
        assertResult ("deftwoargs6", "embedded def value name") (deftwoargs6.get.name)
        assertResult (6, "embedded def value i") (deftwoargs6.get.i)
        assertResult ("six", "embedded def value s") (deftwoargs6.get.s)
    }

    test ("a closure-local TwoArgs def is correctly built") {
        def deftwoargs7 = twoargs (7, "seven")
        assertResult ("deftwoargs7", "closure-local def value name") (deftwoargs7.name)
        assertResult (7, "closure-local def value i") (deftwoargs7.i)
        assertResult ("seven", "closure-local def value s") (deftwoargs7.s)
    }

    // Three argument tests (val)

    val valthreeargs1 = threeargs (1) (true, "one")

    test ("a class level ThreeArgs val is correctly built") {
        assertResult ("valthreeargs1", "class level val value name") (valthreeargs1.name)
        assertResult (1, "class level val value i") (valthreeargs1.i)
        assertResult (true, "class level val value b") (valthreeargs1.b)
        assertResult ("one", "class level val value s") (valthreeargs1.s)
    }

    def valThreeArgsMethod : ThreeArgs[Int] = {
        val valthreeargs2 = threeargs[Int] (2) (false, "two")
        valthreeargs2
    }

    test ("a threeArgsMethod level ThreeArgs val is correctly built") {
        assertResult ("valthreeargs2", "threeArgsMethod level val value name") (valThreeArgsMethod.name)
        assertResult (2, "threeArgsMethod level val value i") (valThreeArgsMethod.i)
        assertResult (false, "threeArgsMethod level val value b") (valThreeArgsMethod.b)
        assertResult ("two", "threeArgsMethod level val value s") (valThreeArgsMethod.s)
    }

    object valThreeArgsObject {
        val valthreeargs3 = threeargs (3) (true, "three")
    }

    test ("an object level ThreeArgs val is correctly built") {
        assertResult ("valthreeargs3", "object level val value name") (valThreeArgsObject.valthreeargs3.name)
        assertResult (3, "object level val value i") (valThreeArgsObject.valthreeargs3.i)
        assertResult (true, "object level val value b") (valThreeArgsObject.valthreeargs3.b)
        assertResult ("three", "object level val value s") (valThreeArgsObject.valthreeargs3.s)
    }

    trait valThreeArgsTrait {
        val valthreeargs4 = threeargs (4) (false, "four")
    }

    object valAnotherThreeArgsObject extends valThreeArgsTrait

    test ("a trait level ThreeArgs val is correctly built") {
        assertResult ("valthreeargs4", "trait level val value name") (valAnotherThreeArgsObject.valthreeargs4.name)
        assertResult (4, "trait level val value i") (valAnotherThreeArgsObject.valthreeargs4.i)
        assertResult (false, "trait level val value b") (valAnotherThreeArgsObject.valthreeargs4.b)
        assertResult ("four", "trait level val value s") (valAnotherThreeArgsObject.valthreeargs4.s)
    }

    val valthreeargs5 = Some (threeargs (5) (true, "five"))

    test ("an embedded ThreeArgs val is correctly built") {
        assertResult ("valthreeargs5", "embedded val value name") (valthreeargs5.get.name)
        assertResult (5, "embedded val value i") (valthreeargs5.get.i)
        assertResult (true, "embedded val value b") (valthreeargs5.get.b)
        assertResult ("five", "embedded val value s") (valthreeargs5.get.s)
    }

    test ("a closure-local ThreeArgs val is correctly built") {
        val valthreeargs6 = threeargs (6) (false, "six")
        assertResult ("valthreeargs6", "closure-local val value name") (valthreeargs6.name)
        assertResult (6, "closure-local val value i") (valthreeargs6.i)
        assertResult (false, "closure-local val value b") (valthreeargs6.b)
        assertResult ("six", "closure-local val value s") (valthreeargs6.s)
    }

    // Three argument tests (lazy val)

    lazy val lazyvalthreeargs1 = threeargs (1) (true, "one")

    test ("a class level ThreeArgs lazy val is correctly built") {
        assertResult ("lazyvalthreeargs1", "class level val value name") (lazyvalthreeargs1.name)
        assertResult (1, "class level lazy val value i") (lazyvalthreeargs1.i)
        assertResult (true, "class level lazy val value b") (lazyvalthreeargs1.b)
        assertResult ("one", "class level lazy val value s") (lazyvalthreeargs1.s)
    }

    def lazyvalThreeArgsMethod : ThreeArgs[Char] = {
        lazy val lazyvalthreeargs2 = threeargs[Char] ('a') (false, "two")
        lazyvalthreeargs2
    }

    test ("a threeArgsMethod level ThreeArgs lazy val is correctly built") {
        assertResult ("lazyvalthreeargs2", "threeArgsMethod level lazy val value name") (lazyvalThreeArgsMethod.name)
        assertResult ('a', "threeArgsMethod level lazy val value i") (lazyvalThreeArgsMethod.i)
        assertResult (false, "threeArgsMethod level lazy val value b") (lazyvalThreeArgsMethod.b)
        assertResult ("two", "threeArgsMethod level lazy val value s") (lazyvalThreeArgsMethod.s)
    }

    object lazyvalThreeArgsObject {
        lazy val lazyvalthreeargs3 = threeargs (3) (true, "three")
    }

    test ("an object level ThreeArgs lazy val is correctly built") {
        assertResult ("lazyvalthreeargs3", "object level lazy val value name") (lazyvalThreeArgsObject.lazyvalthreeargs3.name)
        assertResult (3, "object level lazy val value i") (lazyvalThreeArgsObject.lazyvalthreeargs3.i)
        assertResult (true, "object level lazy val value b") (lazyvalThreeArgsObject.lazyvalthreeargs3.b)
        assertResult ("three", "object level lazy val value s") (lazyvalThreeArgsObject.lazyvalthreeargs3.s)
    }

    trait lazyvalThreeArgsTrait {
        lazy val lazyvalthreeargs4 = threeargs (4) (false, "four")
    }

    object lazyvalAnotherThreeArgsObject extends lazyvalThreeArgsTrait

    test ("a trait level ThreeArgs lazy val is correctly built") {
        assertResult ("lazyvalthreeargs4", "trait level lazy val value name") (lazyvalAnotherThreeArgsObject.lazyvalthreeargs4.name)
        assertResult (4, "trait level lazy val value i") (lazyvalAnotherThreeArgsObject.lazyvalthreeargs4.i)
        assertResult (false, "trait level lazy val value b") (lazyvalAnotherThreeArgsObject.lazyvalthreeargs4.b)
        assertResult ("four", "trait level lazy val value s") (lazyvalAnotherThreeArgsObject.lazyvalthreeargs4.s)
    }

    lazy val lazyvalthreeargs5 = Some (threeargs (5) (true, "five"))

    test ("an embedded ThreeArgs lazy val is correctly built") {
        assertResult ("lazyvalthreeargs5", "embedded lazy val value name") (lazyvalthreeargs5.get.name)
        assertResult (5, "embedded lazy val value i") (lazyvalthreeargs5.get.i)
        assertResult (true, "embedded lazy val value b") (lazyvalthreeargs5.get.b)
        assertResult ("five", "embedded lazy val value s") (lazyvalthreeargs5.get.s)
    }

    test ("a closure-local ThreeArgs lazy val is correctly built") {
        lazy val lazyvalthreeargs6 = threeargs (6) (false, "six")
        assertResult ("lazyvalthreeargs6", "closure-local lazy val value name") (lazyvalthreeargs6.name)
        assertResult (6, "closure-local lazy val value i") (lazyvalthreeargs6.i)
        assertResult (false, "closure-local lazy val value b") (lazyvalthreeargs6.b)
        assertResult ("six", "closure-local lazy val value s") (lazyvalthreeargs6.s)
    }

    // Three argument tests (def)

    def defthreeargs1 = threeargs (1) (false, "one")

    test ("a class level ThreeArgs def (apply) is correctly built") {
        assertResult ("defthreeargs1", "class level def (apply) value name") (defthreeargs1.name)
        assertResult (1, "class level def (apply) value i") (defthreeargs1.i)
        assertResult (false, "class level def (apply) value b") (defthreeargs1.b)
        assertResult ("one", "class level def (apply) value s") (defthreeargs1.s)
    }

    def defthreeargs2 = {
        val i = 0
        threeargs (2) (true, "two")
    }

    test ("a class level ThreeArgs def (body) is correctly built") {
        assertResult ("defthreeargs2", "class level def (body) value name") (defthreeargs2.name)
        assertResult (2, "class level def (body) value i") (defthreeargs2.i)
        assertResult (true, "class level def (body) value b") (defthreeargs2.b)
        assertResult ("two", "class level def (body) value s") (defthreeargs2.s)
    }

    def defThreeArgsMethod : ThreeArgs[Double] = {
        def defthreeargs3 = threeargs[Double] (3.0) (false, "three")
        defthreeargs3
    }

    test ("a method level ThreeArgs def is correctly built") {
        assertResult ("defthreeargs3", "method level def value name") (defThreeArgsMethod.name)
        assertResult (3, "method level def value i") (defThreeArgsMethod.i)
        assertResult (false, "method level def value b") (defThreeArgsMethod.b)
        assertResult ("three", "method level def value s") (defThreeArgsMethod.s)
    }

    object defThreeArgsObject {
        def defthreeargs4 = threeargs (4) (true, "four")
    }

    test ("an object level ThreeArgs def is correctly built") {
        assertResult ("defthreeargs4", "object level def value name") (defThreeArgsObject.defthreeargs4.name)
        assertResult (4, "object level def value i") (defThreeArgsObject.defthreeargs4.i)
        assertResult (true, "object level def value b") (defThreeArgsObject.defthreeargs4.b)
        assertResult ("four", "object level def value s") (defThreeArgsObject.defthreeargs4.s)
    }

    trait defThreeArgsTrait {
        def defthreeargs5 = threeargs (5) (false, "five")
    }

    object defAnotherThreeArgsObject extends defThreeArgsTrait

    test ("a trait level ThreeArgs def is correctly built") {
        assertResult ("defthreeargs5", "trait level def value name") (defAnotherThreeArgsObject.defthreeargs5.name)
        assertResult (5, "trait level def value i") (defAnotherThreeArgsObject.defthreeargs5.i)
        assertResult (false, "trait level def value b") (defAnotherThreeArgsObject.defthreeargs5.b)
        assertResult ("five", "trait level def value s") (defAnotherThreeArgsObject.defthreeargs5.s)
    }

    def defthreeargs6 = Some (threeargs (6) (true, "six"))

    test ("an embedded ThreeArgs def is correctly built") {
        assertResult ("defthreeargs6", "embedded def value name") (defthreeargs6.get.name)
        assertResult (6, "embedded def value i") (defthreeargs6.get.i)
        assertResult (true, "embedded def value b") (defthreeargs6.get.b)
        assertResult ("six", "embedded def value s") (defthreeargs6.get.s)
    }

    test ("a closure-local ThreeArgs def is correctly built") {
        def defthreeargs7 = threeargs (7) (true, "seven")
        assertResult ("defthreeargs7", "closure-local def value name") (defthreeargs7.name)
        assertResult (7, "closure-local def value i") (defthreeargs7.i)
        assertResult (true, "closure-local def value b") (defthreeargs7.b)
        assertResult ("seven", "closure-local def value s") (defthreeargs7.s)
    }

    // Test of methods as macros

    val namedInt1 = NamedInt ("one", 1)

    test ("normal NamedInt is correctly built") {
        assertResult (1, "NamedInt i") (namedInt1.i)
        assertResult ("one", "NamedInt name") (namedInt1.name)
    }

    val namedInt2 = NamedInt ("two", 2).increment (4)

    test ("incremented NamedInt is correctly built") {
        assertResult (6, "NamedInt i") (namedInt2.i)
        assertResult ("namedInt2", "NamedInt name") (namedInt2.name)
    }

    val namedInt3 = NamedInt ("three", 3).increment (4).increment (5)

    test ("a doubly incremented NamedInt is correctly built") {
        assertResult (12, "NamedInt i") (namedInt3.i)
        assertResult ("namedInt3", "NamedInt name") (namedInt3.name)
    }

    val namedInt4 = NamedInt ("six", 6).decrement (7)

    test ("decremented NamedInt is correctly built") {
        assertResult (-1, "NamedInt i") (namedInt4.i)
        assertResult ("namedInt4", "NamedInt name") (namedInt4.name)
    }

    val namedInt5 = NamedInt ("eight", 8).decrement (9).decrement (10)

    test ("a doubly decremented NamedInt is correctly built") {
        assertResult (-11, "NamedInt i") (namedInt5.i)
        assertResult ("namedInt5", "NamedInt name") (namedInt5.name)
    }

}
