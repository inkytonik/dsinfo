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

case class Thingy (name : String, i : Int, s : String)

object ThingyMaker {

    import scala.language.experimental.macros
    import scala.reflect.macros.Context
    import MakeWithName.makeWithName

    def thingy (i : Int, s : String) : Thingy =
        macro makeThingyWithName

    def makeThingyWithName (c : Context) (i : c.Expr[Int], s : c.Expr[String]) : c.Expr[Thingy] =
        makeWithName (c) ("thingy", "ThingyMaker", "mkThingy", i, s)

    def mkThingy (name : String, i : Int, s : String) : Thingy =
        Thingy (name, i, s)

}
