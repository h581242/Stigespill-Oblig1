package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;
	
	// hashmap to register RPC methods which are required to implement RPCImpl
	
	private HashMap<Integer,RPCImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer,RPCImpl>();
		
		// the stop RPC methods is built into the server
		services.put((int)RPCCommon.RPIDSTOP,new RPCServerStopImpl());
	}
	
	public void run() {
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
		
		connection = msgserver.accept();
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
	    
		   int rpcid;
		   
		   // TODO
		   // - receive message containing RPC request
		   // - find the identifier for the RPC methods to invoke
		   // - lookup the method to be invoked
		   // - invoke the method
		   // - send back message containing RPC reply
			
		  //Mottar meldingen som inneholder RPC request
		  Message recieved = connection.receive();

		  //Finner identifier'en (rcpID) som ligger i pos 0 i requesten.
		  rpcid = recieved.getData()[0];
		  
		  //Bruker services HashMap til å finne metoden som skal bli invoked
		  RPCImpl rpcImpl = services.get(rpcid);
		  
		  //Invoker metoden
		  byte[] rpcResponse = rpcImpl.invoke(recieved.getData());
		  
		  //Sender meldingen som inneholder RPC responsen.
		  Message response = new Message(rpcResponse);
		  connection.send(response);

		   
		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		}
	
	}
	
	
	public void register(int rpcid, RPCImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {
		connection.close();
		msgserver.stop();
		
	}
}
