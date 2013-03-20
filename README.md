The dsname library
==================

FIXME

The dsprofile library provides general facilities to implement domain-specific
profiling in Scala and Java programs.

The design of the library and an application to attribute grammar profiling
are described in "Profile-based Abstraction and Analysis of Attribute
Grammars" by Anthony M. Sloane, Proceedings of the International Conference on
Software Language Engineering, 2012.

The construction of this library was supported by Macquarie University and
Eindhoven University of Technology.

The library is released under the GNU Lesser General Public License. See the
files `COPYING` and `COPYING.LESSER` for details.

Downloading the library
=======================

Pre-built jar files for the library are available via the Download section of
this site.

We expect to publish the library in the Maven Central repository at some
point.

Building the library
====================

Using a pre-built jar file should be sufficient, but if you want to build
the library, first clone this repository using Mercurial.

Download and install the Scala simple build tool:

    https://github.com/harrah/xsbt/wiki/


Run `sbt package` in the top-level of the project. sbt will download all the
necessary Scala compiler and library jars, build the library, and package it
as a jar file.

If all goes well, you should find the dsprofile library jar in the `target`
directory under a sub-directory for the Scala version that is being used.
E.g., if the Scala version is 2.10, look in `target/scala_2.10` for
`dsprofile_2.10-VERSION.jar` where `VERSION` is the dsprofile library version.

Version 0.2.0 has been tested with sbt 0.12.2, Scala 2.10.0 and Java
1.7.0_11 running on Mac OS X 10.8.2.

Using the library from Scala
============================

The dsprofile library operates in terms of abstract events that represent
various occurrences as a program executes. For example, suppose that we are
interested in profiling the evaluation of attributes in a running attribute
grammar evaluator. The occurrences in this case are evaluations of
attributes.

We need a way to pass information from the evaluator execution and a way to
trigger profiling reports to be printed.

Information is passed to the profiler by calling the `start` and `finish`
methods of the `Events` module. The parameters to the first of these messages are Scala
tuples that describe the event that has occurred. For example, to indicate
that an attribute evaluation has started, your code might call:

    val ident = start ("event" -> "AttrEval", "subject" -> s, "attribute" -> a,
                       "parameter" -> None)

Each of the strings `"type"`, `"subject"` and so on is a _dimension_ and
together they identify the particular event that has occurred. The second
component of each tuple is an arbitrary value for the corresponding dimension
in this particular occurrence. For example, the value `s` is assumed in the
example to be a reference to the subject node of the evaluation; i.e., the
syntax tree node whose attribute is being evaluated. Similarly, `a` is a value
that refers to the attribute that is being evaluated.

The `start` method will return a unique identifier for this event, which you
need to provide to the associated `finish` method.

Both the dimensions and their values are arbitrary as far as the library is
concerned. The dimensions are just strings and it is up to the user to
establish a convention for their meaning. Similarly, the dimension values are
arbitrary objects as far as the profiling library is concerned. However, it is
best to make sure that they have a sensible `toString` implementation, since
that method is used by the library to print the values (see below).

At the point when an interesting execution region has completed, the program
code should call the `Events.finish` method. As for the `start` method, the
`finish` method takes as its parameters some dimensions of the event that has
just finished. The finish call may have whatever dimensions it likes, which
are usually used to pass information that is only known once the evaluation
has finished, or could be used to record the changes in dimensions during
the event being captured.

For example, in the attribute evaluation case, we might call `finish`
as follows.

    finish (ident, "value" -> v, "cached" -> false)

We can see that the `ident` we were given earlier is passed in as the first argument and
the remaining arguments are dimension tuples.  In this example,
the new `"value"` and `"cached"` dimensions are
given values here, because we only know what they are once the attribute
occurrence has been fully evaluated. As before, the value `v` is an arbitrary
object that is the attribute value. The `cached` dimension is a Boolean that
indicates whether the value of the attribute was obtained from its cache or
not.

The `wrap` method can be used to do a `start`, run some code and then do a
`finish` all in one go.
For example,

    wrap ("foo" -> 1, "bar" -> 2) {
        ... some code ...
    }

will run the code in the block argument wrapped in `start` with the
provided dimensions and dimension values, and a `finish` with no
dimensions.

profile
=======

The other main entry point for the library is the `Profiler.profile` method.
It should be called with the first argument being the computation that you
want to profile. This argument is passed by name to the `profile` method so it
will not be evaluated until necessary. The computation should call the `start`
and `finish` methods as described above, but it can do any other computation
as well. The second argument to `profile` is a list of the dimensions that you
want to see in the profile report. For example, we might call

    profile (c, "attribute", "cached")

to profile the evaluation of `c` and print a report along two dimensions
(first the attribute that was evaluated and then whether it was cached or
not).

The first part of a report produced by this call is:

       716 ms total time
        95 ms profiled time (13.3%)
      2543 profile records

    By attribute:

     Total Total  Self  Self  Desc  Desc Count Count
        ms     %    ms     %    ms     %           %
        56  59.4    18  19.7    37  39.7   621  24.4  entity
        43  45.4    28  29.4    15  16.0   691  27.2  env.in
        30  32.4     7   7.5    23  24.9   203   8.0  tipe
        27  29.2    15  15.8    12  13.4   293  11.5  env.out
        25  26.7     7   8.4    17  18.4    94   3.7  idntype
        23  24.8     3   4.2    19  20.6    97   3.8  basetype
         5   5.3     4   5.0     0   0.3    81   3.2  exptype
         3   3.7     3   3.7     0   0.0    81   3.2  rootconstexp
         3   3.5     3   3.5     0   0.0   234   9.2  typebasetype
         1   2.1     1   1.9     0   0.2    96   3.8  deftype
         0   1.0     0   1.0     0   0.0    52   2.0  level

    By cached for entity:

     Total Total  Self  Self  Desc  Desc Count Count
        ms     %    ms     %    ms     %           %
        14  14.7    12  12.8     1   1.9   158   6.2  false
         6   6.8     6   6.8     0   0.0   463  18.2  true

    By cached for env.in:

     Total Total  Self  Self  Desc  Desc Count Count
        ms     %    ms     %    ms     %           %
        27  28.9    25  27.1     1   1.8   536  21.1  false
         2   2.2     2   2.2     0   0.0   155   6.1  true

The profile report first gives a summary of the evaluation containing the
total time that the execution took, how much of it was accounted for by
profiled evaluations and how many profile records were created. In this case,
since we are just profiling attribute evaluations, the number of profile
records is equal to the number of attribute occurrences that were evaluated.

Following the summary, we get a table showing the profile records bucketed by
the first dimension. The columns show the total time taken to evaluate each
attribute, the time accounted for by the  attribute itself, the time accounted
for by evaluation of other attributes needed by the attribute being
summarised, and the count of how many evaluations were performed.

After the first dimension table, we get one table for each row  in the first
dimension table summarising the second dimension.

In theory the number of dimensions in a report is not limited, but it gets
quite hard to understand after about three or four dimensions.

The last string printed on each line of a table is the `toString` string of
the relevant dimension value. The library will try to detect when this string
will not fit on the line. In those cases, it will print a footnote number
instead and will print the value as a footnote after all of the tables have
been printed. Thus, the dimension could be something such as a tree node which
might be printed in full or pretty-printed.

If `profile` is called without any dimensions, the library will enter an
interactive shell after the computation has finished. You can generate
a report in the shell by entering dimension names separated by commas.
Type `:q` to exit.

Lower-level usage
=================

Alternately, `profileStart` can be called to begin profiling and `profileStop`
called to complete the profile.  `profileStop` can be passed dimensions for the
profile report in the same way as `profile` (but they must be passed in a sequence
like a list).  For example

    profileStart ()
    val ident = Events.start ("type" -> "the_type")
    Events.finish (ident)
    profileStop (Seq ("type"))

It is also possible to defer the generation of the report when using `profileStart`
and `profileStop` by not passing in the dimensions to profile.  In this case
`profileStop` will return a closure that will print a report upon you giving it
the dimensions to profile.  This allows you to print the profile at a later time
or to print it multiple times for different dimension sets, for example.

    profileStart ()
    val i = Events.start ("type" -> "the_type")
    val j = Events.start ("style" -> "much")
    Events.finish (j)
    Events.finish (i)
    val reporter = profileStop ()
    reporter (Seq ("type"))
    reporter (Seq ("style"))

Tracing
=======

`Profiler.trace` can be used to obtain a simple trace of the events. It takes
a single parameter that is a predicate on events. If the parameter is omitted
it defaults to a predicate that is always true. For example,

    trace ()

might print a trace like this

    1: Start    AttrEval (attribute,decl) (parameter,None) (subject,Use(int))
    2: Start    AttrEval (attribute,lookup) (parameter,Some(int)) (subject,Use(int))
    3: Start    AttrEval (attribute,lookup) (parameter,Some(int)) (subject,VarDecl(Use(int),y))
    4: Start    AttrEval (attribute,declarationOf) (parameter,Some(int)) (subject,VarDecl(Use(int),y))
    4: Finish            (cached,false) (value,null)
    ...

If you just want to see the `Start` events you can use a predicate as follows:

    trace (_.kind == Events.Start)

Adding your own dimensions
==========================

The dimensions used above such as `attribute` and `cached` are called
_intrinsic dimensions_ because they are part of the profiling records.
The library also supports _derived dimensions_ which are calculated from
the record.
Derived dimensions allow you to tailor the profiles to show domain-specific
information, or perhaps even problem-specific information if you are using
profiles to track down a problem.

To define your own derived dimensions, override the `dimValue` method of
the profiler as shown in this example.

    override def dimValue (record : Record, dim : Dimension) : Value =
        dim match {

            // `type` dimension is the node type of the record's subject.
            case "type" =>
                checkFor (record, dim, "", "subject") {
                    case p : Product => p.productPrefix
                    case _           => "unknown type"
                }

            // Otherwise, dispatch to the value to handle it as an intrinsic
            // dimension
            case _ =>
                super.dimValue (record, dim)

        }

The arguments are a profile record and the dimension name.
`dimValue` must examine the dimension name and the record to return a value
(which is just a string).
If the dimension is not supported, call the superclass implementation which
will provide default behaviour.

In the example, we define a `type` dimension that is the prefix of the
`Product` node that is the value of an intrinsic `subject` dimension.
The `checkFor` method allows you to easily check for the presence of needed
intrinsic dimensions. `type` is provided by the default `Profiler`.

Using the library from Java
===========================

The dsprofile library can also be used from Java code. To make this more
convenient, some bridge types and methods are used to communicate with
the library implementation in Scala.

Note: unfortunately, we do not currently have resources to maintain the
Java API to be in sync with the Scala one.
If a Scala API feature is something you wish to use from Java and can't,
please let us know and we will see what we can do.

The following code shows a simple Java program and how the profiler can be
used from it using these bridges.

    import org.bitbucket.inkytonik.dsprofile.Action;
    import org.bitbucket.inkytonik.dsprofile.JavaProfiler;

    public class Program extends JavaProfiler {

        public static void main (String[] args) {

            Action action =
                new Action () {
                    public void execute () {
                        doSomething (10);
                    }
                };

            profile (action, "foo", "bar");

        }

        static void doSomething (int num) {
            start (tuple ("event", "something"),
                   tuple ("foo", num),
                   tuple ("bar", num * 2));
            int x = 0;
            for (int i = 0; i < 100000; i++)
                x = x + i;
            System.out.println ("x = " + x);
            if (num > 1)
                doSomething (num - 1);
            finish (tuple ("event", "something"),
                    tuple ("foo", num),
                    tuple ("bar", num * 2),
                    tuple ("ble", num + 1));
        }

    }

The `Action` type is needed since evaluation of  the first parameter to
`profile` must be delayed rather than occurring before the call. THe
`tuple` calls are used to create Scala tuples for the pairs of dimension
name and value.

The beginning part of the output produced by this program is as follows:

    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704
    x = 704982704

        36 ms total time
        20 ms profiled time (57.1%)
        10 profile records

    By foo:

     Total Total  Self  Self  Desc  Desc Count Count
        ms     %    ms     %    ms     %           %
        20 100.0     3  15.2    17  84.8     1  10.0  10
        17  84.8     3  17.9    13  66.9     1  10.0  9
        13  66.9     3  18.0    10  48.8     1  10.0  8
        10  48.8     2  13.3     7  35.5     1  10.0  7
         7  35.5     3  15.2     4  20.3     1  10.0  6
         4  20.3     0   0.9     4  19.5     1  10.0  5
         4  19.5     0   0.7     3  18.8     1  10.0  4
         3  18.8     0   0.7     3  18.1     1  10.0  3
         3  18.1     0   0.9     3  17.2     1  10.0  2
         3  17.2     3  17.2     0   0.0     1  10.0  1

    By bar for 10:

     Total Total  Self  Self  Desc  Desc Count Count
        ms     %    ms     %    ms     %           %
        20 100.0     3  15.2    17  84.8     1  10.0  20

    By bar for 9:

     Total Total  Self  Self  Desc  Desc Count Count
        ms     %    ms     %    ms     %           %
        17  84.8     3  17.9    13  66.9     1  10.0  18

