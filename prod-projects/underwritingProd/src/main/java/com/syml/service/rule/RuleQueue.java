package com.syml.service.rule;

import java.util.LinkedList;

public class RuleQueue {
	
    private final PoolWorker[] threads;
    private final LinkedList<IRule> queue;
    private static RuleQueue instance;
    
    public static RuleQueue getInstance()
    {
    	if(instance == null)
    		instance = new RuleQueue(10);
    	return instance;
    }
    
    private RuleQueue(int nThreads)
    {
        queue = new LinkedList<IRule>();
        threads = new PoolWorker[nThreads];

        for (int i=0; i<nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(IRule r) {
        synchronized(queue) {
            queue.addLast(r);
            queue.notify();
        }
    }
    
    private class PoolWorker extends Thread {
        public void run() {
        	IRule r;

            while (true) {
                synchronized(queue) {
                    while (queue.isEmpty()) {
                        try
                        {
                            queue.wait();
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }

                    r = queue.removeFirst();
                }

                // If we don't catch RuntimeException, 
                // the pool could leak threads
                try {
                    r.start();
                }
                catch (RuntimeException e) {
                    // You might want to log something here
                }
            }
        }
    }


}
