package com.syml.service.calculate;

import java.util.LinkedList;


public class CalculateQueue {
	
	 	private final PoolWorker[] threads;
	    private final LinkedList<FitnessCalculationTask> queue;
	    private static CalculateQueue instance;
	    
	    public static CalculateQueue getInstance()
	    {
	    	if(instance == null)
	    		instance = new CalculateQueue(20);
	    	return instance;
	    }
	    
	    private CalculateQueue(int nThreads)
	    {
	        queue = new LinkedList<FitnessCalculationTask>();
	        threads = new PoolWorker[nThreads];

	        for (int i=0; i<nThreads; i++) {
	            threads[i] = new PoolWorker();
	            threads[i].start();
	        }
	    }
	    
	    public void execute(FitnessCalculationTask w) {
	        synchronized(queue) {
	            queue.addLast(w);
	            queue.notify();
	        }
	    }
	    
	    private class PoolWorker extends Thread {
	        public void run() {
	        	FitnessCalculationTask w;

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

	                    w = queue.removeFirst();
	                }

	                // If we don't catch RuntimeException, 
	                // the pool could leak threads
	                try {
	                    w.start();
	                }
	                catch (RuntimeException e) {
	                    // You might want to log something here
	                }
	            }
	        }
	    }

}
