package org.marketcetera.core;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.marketcetera.core.attributes.ClassVersion;

/* $License$ */

/**
 * A wrapper around {@link ReentrantReadWriteLock} guaranteeing
 * acquisition and release of the lock.
 * 
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: LockHelper.java 16063 2012-01-31 18:21:55Z colin $
 * @since 0.5.0
 */
@ClassVersion("$Id: LockHelper.java 16063 2012-01-31 18:21:55Z colin $")
public class LockHelper
{
    /**
     * object which controls the locking behavior
     */
    private final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    /**
     * Executes the given {@link Callable} block within the context of a
     * write lock.
     *
     * @param inBlock a <code>Callable&lt;T&gt;</code> value
     * @return a <code>T</code> value
     * @throws Exception if an error occurs during block execution
     */
    public <T> T executeWrite(Callable<T> inBlock)
        throws Exception
    {
        try {
            mLock.writeLock().lock();
            return inBlock.call();
        } finally {
            mLock.writeLock().unlock();
        }
    }
    /**
     * Executes the given {@link Runnable} block within the context of a
     * write lock.
     *
     * @param inBlock a <code>Runnable</code> value
     */
    public void executeWrite(Runnable inBlock)
    {
        try {
            mLock.writeLock().lock();
            inBlock.run();
        } finally {
            mLock.writeLock().unlock();
        }
    }
    /**
     * Executes the given {@link Callable} block within the context of a
     * read lock.
     *
     * @param inBlock a <code>Callable&lt;T&gt;</code> value
     * @return a <code>T</code> value
     * @throws Exception if an error occurs during block execution
     */
    public <T> T executeRead(Callable<T> inBlock)
        throws Exception
    {
        try {
            mLock.readLock().lock();
            return inBlock.call();
        } finally {
            mLock.readLock().unlock();
        }
    }
    /**
     * Executes the given {@link Runnable} block within the context of a
     * read lock.
     *
     * @param inBlock a <code>Runnable</code> value
     */
    public void executeRead(Runnable inBlock)
    {
        try {
            mLock.readLock().lock();
            inBlock.run();
        } finally {
            mLock.readLock().unlock();
        }
    }
}
