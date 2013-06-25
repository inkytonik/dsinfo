/**
 * This file is part of dsinfo.
 *
 * Copyright (C) 2013 Anthony M Sloane, Macquarie University.
 * Copyright (C) 2013 Matthew Roberts, Macquarie University.
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

import sbt._
import Keys._

object DSInfoBuild extends Build {

    lazy val root =
        Project (
            id = "root",
            base = file (".")
        ) aggregate (dsinfo, egs, tests)

    lazy val dsinfo =
        Project (
            id = "dsinfo",
            base = file ("dsinfo")
        )

    lazy val egs =
        Project (
            id = "egs",
            base = file ("egs")
        ) dependsOn (dsinfo)

    lazy val tests =
        Project (
            id = "tests",
            base = file ("tests")
        ) dependsOn (egs)

}
