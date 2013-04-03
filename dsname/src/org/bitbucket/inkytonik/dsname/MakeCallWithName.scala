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
     * Make an AST fragment that calls the specified method with the name of
     * the enclosing `val` or `def` and the original arguments to the macro
     * call given by `c`. The name can unqualified or qualified with object
     * or package names, or both. If the first component of the name is
     * `this` then the generated call will be on the same object as the
     * macro call. Otherwise, the name refers to a specific method either
     * in scope at the macro application (unqualified) or selected from
     * another module or package (qualified).
     */
    def makeCallWithName[T] (c : Context, methodName : String) : c.Expr[T] = {

        import c.{universe => u}
        import u._

        import scala.reflect.NameTransformer.encode

        /**
         * Helper function to do the real work once the macro name and arguments
         * have been determined. `obj` is the object to which the macro was
         * applied. `macroArgs` is a list of the argument lists that were supplied
         * to the macro call. `args` is the first argument list; the name is
         * pre-pended to this list. `argsn` is the other argument lists. They are
         * passed unchanged.
         */
        def constructCall[T] (obj : c.Tree, macroName : Name, args1 : List[c.Tree],
                              argsn : List[List[c.Tree]]) : c.Expr[T] = {

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

                    // Macro invocation lies inside a definition of a lazy val of the form
                    //   lazy def foo : ... = {
                    //      foo$lzy = <macro invocation>
                    //      foo$lzy
                    //   }
                    case d @ DefDef (_, defname, _, _, _, Block (List (Assign (_, exp)), _)) if isThisInvocation (exp) =>
                        defname.decoded

                    // Body of def is the macro invocation (non lazy)
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
                                // println (s"c.enclosingMethod = ${c.enclosingMethod}")
                                // println (s"c.enclosingMethod = ${u.showRaw (c.enclosingMethod)}")
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

            /**
             * Make the call, gvien a tree for the method.
             */
            def makeCall[T] (method : c.Tree) : c.Expr[T] = {

                // The base expression: the method applied to the first argument list
                // with the name pre-pended
                val base = Apply (method, Literal (Constant (nameOfEnclosing)) :: args1)

                // Wrap the base in as many applications as are needed to pass the
                // other argument lists (if any)
                c.Expr[T] (argsn.foldLeft (base) {
                               case (t, a) => Apply (t, a)
                           })

            }

            // Build the method call
            methodName.split ('.') match {

                case Array () =>
                    c.error (c.enclosingPosition,
                             s"makeCallWithName: illegal call with empty method name")
                    null

                case components =>

                    // Make the AST for the method reference
                    val head : c.Tree =
                        if (components.head == "this")
                            obj
                        else
                            Ident (newTermName (encode (components.head)))
                    val method =
                        components.tail.foldLeft (head) {
                            case (t, s) => Select (t, newTermName (encode (s)))
                        }

                    // println (s"method = $method")
                    // println (s"method = ${u.showRaw (method)}")

                    makeCall (method)

            }

        }

        // println (s"c.macroApplication = $c.macroApplication")
        // println (s"c.macroApplication = ${u.showRaw (c.macroApplication)}")

        // Extract macro name and arguments from the application and then
        // construct the call

        c.macroApplication match {

            // No arguments
            case Select (obj, macroName) =>
                constructCall (obj, macroName, Nil, Nil)

            // One argument list
            case Apply (Select (obj, macroName), args) =>
                constructCall (obj, macroName, args, Nil)

            // Two argument lists
            case Apply (Apply (Select (obj, macroName), args1), args2) =>
                constructCall (obj, macroName, args1, List (args2))

            // One argument list + type application
            case Apply (TypeApply (Select (obj, macroName), _), args1) =>
                constructCall (obj, macroName, args1, Nil)

            // Two arguments list + type application
            case Apply (Apply (TypeApply (Select (obj, macroName), _), args1), args2) =>
                constructCall (obj, macroName, args1, List (args2))

            case t =>
                c.error (c.enclosingPosition,
                         s"makeCallWithName: unexpected macro application structure ${u.showRaw (t)}")
                null

        }

    }

}
