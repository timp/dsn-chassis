# Background #

One of the main limitations of the vanilla Atom Publishing Protocol (atompub) is that it doesn't support retrieval of a revision history for any member entry in a collection. I.e., it is not possible to retrieve a prior state of any given member entry, nor to retrieve a record of who has performed what action(s) on the member or the collection.

# Pattern 1 - Linked Version Series #

> Let R be a collection, with member entries [r1](https://code.google.com/p/dsn-chassis/source/detail?r=1) .. rn.

> Let S be a collection, with member entries s1 .. sn.

> Each r (revision) in R is considered to be a revision of some s (series) in S.

> To create a new series, POST a new entry to S.

> To create a new revision, POST a new entry to R which includes a link to some s in S.

> PUT and DELETE are disallowed on both R and S.

The ordering provided by the date and time of publication of each r in R, in addition to the link from each r in R to some s in S, should be sufficient to derive an ordered sequence of revisions for any s in S by applying some query over R.

Advantages:

  * requires no special implementation on the server-side; simply a convention for using two collections together
  * all revisions in the same collection, convenient if you want to query previous state

Disadvantages:

  * inefficient if you only want to query latest state
  * needs special client implementation; not vanilla atom

# Pattern 2 - Transparent Version History #

> Let F be a collection with member entries f1 .. fn.

> Allow full atompub on F, i.e. GET, POST on F, GET, PUT and DELETE on f1 .. fn.

> Let H be a set of collections H1 .. Hn.

> Each Hx in H is a collection whose member entries store a history of all actions performed on member entry fx in F.

Advantages:

  * the versioning process is transparent; a client interacts with F as any normal atom collection; if a revision history is needed, it can be retrieved given the convention for mapping fx to Hx.
  * each history feed Hx in H can store metadata about the action, i.e., who did what

Disadvantages:

  * requires special server-side implementation