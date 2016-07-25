package com.syml.service.rule;

import java.util.LinkedList;


public class CheckingQueue {
	
	 	private final PoolWorker[] threads;
	    private final LinkedList<BaseChecking> queue;
	    private static CheckingQueue instance;
	    
	    public static CheckingQueue getInstance()
	    {
	    	if(instance == null)
	    		instance = new CheckingQueue(20);
	    	return instance;
	    }
	    
	    private CheckingQueue(int nThreads)
	    {
	        queue = new LinkedList<BaseChecking>();
	        threads = new PoolWorker[nThreads];

	        for (int i=0; i<nThreads; i++) {
	            threads[i] = new PoolWorker();
	            threads[i].start();
	        }
	    }
	    
	    public void execute(BaseChecking r) {
	        synchronized(queue) {
	            queue.addLast(r);
	            queue.notify();
	        }
	    }
	    
	    private class PoolWorker extends Thread {
	        public void run() {
	        	BaseChecking r;

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
