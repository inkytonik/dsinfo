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
 * along with dsnaem.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.bitbucket.inkytonik.dsname

import org.scalatest.FunSuite

class MakeWithNameTests extends FunSuite {

    import ThingyMaker.thingy

    val val1 = thingy (1, "one")

    test ("a class level Thingy gets the correct name") {
        expectResult ("val1", "class level value name") (val1.name)
    }

    test ("a class level Thingy gets the correct integer field") {
        expectResult (1, "class level value i") (val1.i)
    }

    test ("a class level Thingy gets the correct string field") {
        expectResult ("one", "class level value s") (val1.s)
    }

    def method : Thingy = {
        val val2 = thingy (2, "two")
        val2
    }

    test ("a method level Thingy gets the correct name") {
        expectResult ("val2", "method level value name") (method.name)
    }

    test ("a method level Thingy gets the correct integer field") {
        expectResult (2, "method level value i") (method.i)
    }

    test ("a method level Thingy gets the correct string field") {
        expectResult ("two", "method level value s") (method.s)
    }

    object anObject {
        val val3 = thingy (3, "three")
    }

    test ("an object level Thingy gets the correct name") {
        expectResult ("val3", "object level value name") (anObject.val3.name)
    }

    test ("an object level Thingy gets the correct integer field") {
        expectResult (3, "object level value i") (anObject.val3.i)
    }

    test ("an object level Thingy gets the correct string field") {
        expectResult ("three", "object level value s") (anObject.val3.s)
    }

    trait aTrait {
        val val4 = thingy (4, "four")
    }

    object anotherObject extends aTrait

    test ("a trait level Thingy gets the correct name") {
        expectResult ("val4", "trait level value name") (anotherObject.val4.name)
    }

    test ("a trait level Thingy gets the correct integer field") {
        expectResult (4, "trait level value i") (anotherObject.val4.i)
    }

    test ("a trait level Thingy gets the correct string field") {
        expectResult ("four", "trait level value s") (anotherObject.val4.s)
    }

    val val5 = Some (thingy (5, "five"))

    test ("an embedded Thingy has a default name") {
        expectResult ("thingy", "embedded value name") (val5.get.name)
    }

    test ("an embedded Thingy gets the correct integer field") {
        expectResult (5, "embedded value i") (val5.get.i)
    }

    test ("an embedded Thingy gets the correct string field") {
        expectResult ("five", "embedded value s") (val5.get.s)
    }

}
