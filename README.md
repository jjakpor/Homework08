# HashTable
## HashTableChain w/ Map

Complete the `HashTableChain` from lecture/book (Section 7.4) so that it fully implements the [`java.util.Map`](https://docs.oracle.com/javase/7/docs/api/java/util/Map.html) interface (you may only omit/stub out the `putAll()` and `values()` methods, all other methods must be implemented).

Also, complete the `SetIterator`, as described in section 7.5, for your `HashTableChain`.  This will required creating the class `EntrySet`, both of which will help you create the `entrySet()` method in the `Map` interface.  The `SetIterator` should behave as [iterators](https://docs.oracle.com/javase/7/docs/api/java/util/Iterator.html) do:
- Provides methods `hasNext()` and `next()`
- Use data field index to keep track of the next value of the iterator (initially 0)
- Data field `lastItemReturned` keeps track of the index of the last item returned by `next()`; this is used by the `remove()` method
- The `remove()` method removes the last item returned by the `next()` method from the Set. It may only be called once for each call to `next()`

JUnit tests have been provided for each method to thoroughly test the methods' functionality.  This also includes test to verify that `SetIterator` works.

> ***NOTE:***
> - Reading the book helps! Ch. 7 has a lot of information and code on hash tables and the parts required above!
> - Work with a small example (integers work well since the hashing function is straightforward, ~5 entries, make sure you have a couple collisions). Draw out exactly what the hash table (chaining) should look like before you begin writing code. Again, it will ensure you understand what the data structure should look like when built, and be a great check as you build the methods.
> - Strategic development is essential; plan the project first to identify which parts are key to the functionality of the data structure. Review the JUnit tests to ensure you understand the purpose/results of the method you're about to implement.
