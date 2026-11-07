# Lab 12 - Threads and locks: answers

## a) Fixed thread pool vs cached thread pool (1,000,000 transactions)

Run `App` (fixed pool) and `AppCachedPool` (cached pool) and fill in your own times:

| Pool   | 1000 accounts | 10 accounts |
|--------|---------------|-------------|
| Fixed  | ___ ms        | ___ ms      |
| Cached | ___ ms        | ___ ms      |

(Test run on a 2-core machine: fixed 326 ms / 333 ms, cached 3254 ms / 3501 ms.)

### c) What do you observe?

- The **fixed pool is much faster** (about 10x here). It reuses a small number of
  threads (one per core). The cached pool creates a new thread every time no idle
  thread is free. Tasks are submitted faster than they finish, so it creates
  thousands of threads. Creating threads, scheduling them and switching between
  them costs more than the transactions themselves.
- With **10 accounts** both pools are slower than with 1000 accounts. With only 10
  accounts, many transactions want the same accounts at the same time, so threads
  wait on each other's locks (high contention). With 1000 accounts, collisions are rare.
- Contention hurts the cached pool more. Blocked threads are not idle, so the
  cached pool creates even more threads.
- The total money is the same before and after in every case, so the locks
  keep the data consistent.

## b) Replace "synchronized" by locks

Each `CryptoAccount` has its own `ReentrantLock`. A transaction locks the sender
and the receiver, moves the money, then unlocks both in `finally` blocks. Two
transactions on different accounts can now run in parallel. With
`static synchronized`, only one transaction in the whole system could run at a time.

## c) What could go wrong?

**Deadlock.** If each transaction locks the sender first and then the receiver:

- Thread 1 pays A -> B: it locks A and waits for B.
- Thread 2 pays B -> A: it locks B and waits for A.

Each thread holds the lock the other one needs, so both wait forever and the
program never finishes. `DeadlockDemo` shows this
(`Transaction.executeWithoutOrdering()`).

## d) Solution: resource ordering

`CryptoAccount.compareTo()` defines a total order on accounts by id.
`Transaction.execute()` always locks the **smaller id first**, whatever the direction
of the payment. A -> B and B -> A both lock A then B. A thread can never hold
the higher lock while waiting for the lower one, so a circular wait, and
therefore a deadlock, is impossible. Locks are released in `finally` blocks,
so a refused transaction (insufficient balance) never leaves an account locked.
