package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class Message {

	private byte[] payload;

	public Message(byte[] payload) {
		this.payload = payload; // TODO: check for length within boundary
	}

	public Message() {
		super();
	}

	public byte[] getData() {
		return this.payload; 
	}

	public byte[] encapsulate() {
		
		byte[] encoded = null;
		
		
		// TODO
		// encapulate/encode the payload of this message in the
		// encoded byte array according to message format
		
		byte messageLength = (byte)payload.length;

		if (messageLength < MessageConfig.SEGMENTSIZE) {
			byte[] extend = new byte[payload.length + 1]; //Array length = 57
			for (int i = 0; i < payload.length; i++) {
				extend[i + 1] = payload[i]; //Alt fra payload blir flyttet en lengre bak for å lage plass
			}
			
			payload = extend;
			payload[0] = messageLength;
			
			for (int i = 0; i < MessageConfig.SEGMENTSIZE; i++) {
				encoded = Arrays.copyOf(payload, i + 1);
			}
			
		}
		

		else {
		   throw new UnsupportedOperationException(TODO.method());
		}

		return encoded;
		
	}

	
	public void decapsulate(byte[] received) {
		
		// TODO
		// decapsulate the data contained in the received byte array and store it 
		// in the payload of this message
		
		int messageLength = received[0];
		byte[] decode = new byte[messageLength];
		
		for (int i = 0; i < decode.length; i++) {
			decode[i] = received[i + 1];
			System.out.println(decode[i]);
		}
		
		this.payload = decode;
		
		
		
	}
}
