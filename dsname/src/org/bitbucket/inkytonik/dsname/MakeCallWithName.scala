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

object DSName {

    import scala.reflect.macros.Context

    /**
     * Make an AST fragment that calls the specified unqualified method with
     * the name of the enclosing `val` or `def` and the original arguments
     * to the macro call given by `c`.
     */
    def makeCallWithName[T] (c : Context, methodName : String) : c.Expr[T] =
        makeCallWithName (c, "", methodName)

    /**
     * Make an AST fragment that calls the specified method with the name of
     * the enclosing `val` or `def` and the original arguments to the macro
     * call given by `c`. If `objectName` is empty just called the unqualified
     * method, otherwise qualify it with `objectName`.
     */
    def makeCallWithName[T] (c : Context, objectName : String, methodName : String) : c.Expr[T] = {

        import c.{universe => u}
        import u._

        /**
         * The name and args of the macro.
         */
        val Apply (Select (_, macroName), macroArgs) = c.macroApplication

        /**
         * The string of the macro name.
         */
        val macroNameStr = macroName.decoded

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
            optFindValNameIn (body).getOrElse (macroNameStr)

        /**
         * Find the name of the entity for which this macro application is
         * the right-hand side, or the macro name if one can't be found.
         */
        def nameOfEnclosing : String =
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
                            macroNameStr
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
                                     s"makeCallWithName: unexpected context ${u.showRaw (tree)}")
                            "dummy"

                    }

            }

        // Make the method we want to call
        val method =
            if (objectName.isEmpty)
                Ident (newTermName (methodName))
            else
                Select (Ident (newTermName (objectName)), newTermName (methodName))

        // Return call to specified method passing the name and the original args
        c.Expr[T] (Apply (method, Literal (Constant (nameOfEnclosing)) :: macroArgs))

    }

}
