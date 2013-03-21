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
         * Is this tree this macro invocation?
         */
        def isThisInvocation (tree : c.Tree) : Boolean =
            tree.pos == c.enclosingPosition

        /**
         * If the given tree is a value definition that has this macro
         * application on the right-hand side, return its name, otherwise be
         * undefined.
         */
        val isThisVal : PartialFunction[c.Tree,String] = {

            case d @ ValDef (_, name, _, rhs) if isThisInvocation (rhs) =>
                name.decoded

        }

        /**
         * Try to find this invocation in a `val` in a list of trees. If found,
         * return `Some (name)` where `name` is the name of the `val`, otherwise
         * return `None`.
         */
        def optFindValNameIn (body : List[c.Tree]) : Option[String] =
            body.collectFirst (isThisVal)

        /**
         * Try to find this invocation in a `val` in a list of trees. If found,
         * return the name of the `val`, otherwise return the macro name.
         */
        def getValNameIn (body : List[c.Tree]) : String =
            optFindValNameIn (body).getOrElse (macroName)

        /**
         * Find the name of the definition for which this macro application is
         * the right-hand side, or `None` if we can't find one.
         * FIXME: other case: top-level of template
         */
        def enclosingDefName : String =
            c.enclosingMethod match {

                // Body of def is the macro invocation
                case d @ DefDef (_, defname, _, _, _, body) if isThisInvocation (body) =>
                    defname.decoded

                // def has a block body
                case d @ DefDef (_, defname, _, _, _, Block (body, expr)) =>
                    optFindValNameIn (body) match {
                        // It's a val inside the def
                        case Some (name) =>
                            name
                        // It's the value of the def's body
                        case None if isThisInvocation (expr) =>
                            defname.decoded
                        case None =>
                            macroName
                    }

                // Not a def, look for a val in enclosing template bodies
                case _ =>

                    c.enclosingClass match {

                        case ClassDef (_, _, _, Template (_, _, body)) =>
                            getValNameIn (body)

                        case ModuleDef (_, _, Template (_, _, body)) =>
                            getValNameIn (body)

                        case tree =>
                            c.error (c.enclosingPosition,
                                     s"makeWithName: unexpected context ${u.showRaw (tree)}")
                            "dummy"

                    }

            }

        // Return call to specified method passing the name and the original args
        c.Expr[T] (Apply (Select (Ident (newTermName (objectName)),
                                  newTermName (funcName)),
                          Literal (Constant (enclosingDefName)) :: macroArgs))

    }

}
