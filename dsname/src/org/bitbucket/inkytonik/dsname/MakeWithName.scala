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

object MakeWithName {

    import scala.reflect.macros.Context

    def makeWithName[T] (c : Context) (macroName : String,
                                       objectName : String,
                                       funcName : String,
                                       args : c.Expr[_]*) : c.Expr[T] = {

        import c.{universe => u}
        import u._

        /**
         * A list of the trees of the expressions that were supplied as the
         * macro arguments.
         */
        val macroArgs = (args map (_.tree)).toList

        /**
         * If the given tree is a definition that has this macro application
         * on the right-hand side, return its name, otherwise be undefined.
         */
        val isThisDef : PartialFunction[c.Tree,String] = {

            case d @ ValDef (_, name, _, rhs) if rhs.pos == c.enclosingPosition =>
                // println (s"d = ${u.showRaw (d)}")
                name.decoded

            // FIXME other cases, DefDef, ...

        }

        /**
         * Try to find this def in a list of trees. If found, return its name,
         * otherwise return the macro name.
         */
        def findDefNameIn (body : List[c.Tree]) : String =
            body.collectFirst (isThisDef) match {
                case Some (name) => name
                case None        => macroName
            }

        /**
         * Find the name of the definition for which this macro application is
         * the right-hand side, or `None` if we can't find one.
         * FIXME: other case: top-level of template
         */
        def enclosingDefName : String =
            c.enclosingMethod match {

                case DefDef (_, _, _, _, _, Block (body, _)) =>
                    findDefNameIn (body)

                case _ =>

                    c.enclosingClass match {

                        case ClassDef (_, _, _, Template (_, _, body)) =>
                            findDefNameIn (body)

                        case ModuleDef (_, _, Template (_, _, body)) =>
                            findDefNameIn (body)

                        case tree =>
                            c.error (c.enclosingPosition,
                                     s"unexpected context for def: ${u.showRaw (tree)}")
                            "dummy"

                    }

            }

        // println (s"i = ${u.showRaw (i)}")
        // println
        // println (s"c.prefix.tree = ${u.showRaw (c.prefix.tree)}")
        // println
        // println (s"c.macroApplication = ${u.showRaw (c.macroApplication)}")
        // println
        // println (s"c.enclosingClass = ${u.showRaw (c.enclosingClass)}")
        // println
        // println (s"c.enclosingMethod = ${u.showRaw (c.enclosingMethod)}")
        // println
        // println (s"enclosingDef = $enclosingDef")
        // println

        val t = Apply (Select (Ident (newTermName (objectName)),
                               newTermName (funcName)),
                       Literal (Constant (enclosingDefName)) :: macroArgs)
        c.Expr[T] (t)

    }

}
