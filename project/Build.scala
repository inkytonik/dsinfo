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

import sbt._
import Keys._

object DsNameBuild extends Build {

    lazy val root =
        Project (
            id = "root",
            base = file (".")
        ) aggregate (dsname, egs, tests)

    lazy val dsname =
        Project (
            id = "dsname",
            base = file ("dsname")
        )

    lazy val egs =
        Project (
            id = "egs",
            base = file ("egs")
        ) dependsOn (dsname)

    lazy val tests =
        Project (
            id = "tests",
            base = file ("tests")
        ) dependsOn (egs)

}
