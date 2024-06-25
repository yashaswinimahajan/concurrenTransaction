package concurrentFinancialTransaction;
import java.util.concurrent.BlockingQueue;
import java.util.UUID;

public class Producer implements Runnable  {
         private final BlockingQueue<Transaction> queue;
         private static final int MAX_TRANSACTIONS = 75;

	   public Producer(BlockingQueue<Transaction> queue) {
			this.queue = queue; 
		}

		@Override
		public void run() {
			try {
				
				for (int i = 0; i < MAX_TRANSACTIONS; i++) {
					
					String transactionID = UUID.randomUUID().toString();

					long timestamp = System.currentTimeMillis();
                    String type = (Math.random() > 0.7) ? "BUY" : "SELL";
                    double amount = Math.random() * 1000;

		Transaction transaction = new Transaction(transactionID, timestamp, type, amount);
         queue.put(transaction);
         System.out.println("Produced: " + transaction);
				}
				for (int i = 0; i < TransactionProcessor.NUM_CONSUMERS; i++) {
					queue.put(new Transaction("POISON_PILL", 0, "", 0));
				}
			} catch (InterruptedException e) {
			
				Thread.currentThread().interrupt();
			}
		}
	}

