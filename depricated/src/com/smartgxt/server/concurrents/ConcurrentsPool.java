package com.smartgxt.server.concurrents;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Anton Alexeyev
 * 
 */
public class ConcurrentsPool {

	private ConcurrentsPool instance;

	int poolSize = 3;

	int maxPoolSize = 3;

	long keepAliveTime = 10;

	ThreadPoolExecutor threadPool = null;

	final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
			5);

	// OracleOCIConnectionPool cpool;

	public ConcurrentsPool() {
		threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize,
				keepAliveTime, TimeUnit.SECONDS, queue);

		// Properties p = new Properties();
		// p.put(OracleOCIConnectionPool.CONNPOOL_MIN_LIMIT, "2");
		// p.put(OracleOCIConnectionPool.CONNPOOL_MAX_LIMIT, "2");
		// p.put(OracleOCIConnectionPool.CONNPOOL_INCREMENT, "0");
		//
		// Enable the new configuration
		// cpool = new OracleOCIConnectionPool("anton", "anton123",
		// OracleConfiguration.getUrl(), p);
	}

	// public OracleOCIConnectionPool getCpool() {
	// return cpool;
	// }

	public void runTask(Runnable task) throws SQLException {
		threadPool.execute(task);
	}

	public void shutDown() {
		threadPool.shutdown();
	}

	public ConcurrentsPool get() {
		if (instance == null)
			instance = new ConcurrentsPool();
		return instance;
	}

}
