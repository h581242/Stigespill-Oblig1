package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;

import no.hvl.dat110.TODO;

public class RPCUtils {

	// Utility methods for marshalling and marshalling of parameters and return values
	// in RPC request and RPC responses
	// data bytearrays and return byte arrays is according to the 
	// RPC message syntax [rpcid,parameter/return value]
	
	public static byte[] marshallString(byte rpcid, String str) {
		

		// TODO: marshall RPC identifier and string into byte array
		
		byte[] strByteArray = str.getBytes();

		//Byte Array for lengden av String'en + plass til rpcID.
		//Setter encoded[0] til å være rpcID.
		byte[] encoded = new byte[str.length() + 1];
		encoded[0] = rpcid;
		
		//Slår sammen encoded med strByteArray slik at rpcID er i første posisjon,
		//og strengen er resten av Array'en.
		for (int i = 1; i <= strByteArray.length; i++) {
			encoded[i] = strByteArray[i - 1];
		}
		
		return encoded;
		
	}

	public static String unmarshallString(byte[] data) {

		String decoded;

		// TODO: unmarshall String contained in data into decoded
				
		//Ny array med lengden av data parameteren.
		byte[] dataArray = new byte[data.length - 1];

		//Legge data inn i arrayen og fjerne rpcID.
		for (int i = 1; i <= dataArray.length; i++) {
			dataArray[i - 1] = data[i];
		}
		
		//Gjøre om fra byte til String
		decoded = new String(dataArray);
		
		return decoded;

	}

	
	
	
	public static byte[] marshallVoid(byte rpcid) {

		byte[] encoded;

		// TODO: marshall RPC identifier in case of void type

		//Void sender ingenting, bortsett fra rpcID'en. 
		//Oppretter en byteArray med lengden 1, og legger inn rpcID.
		encoded = new byte[1];
		encoded[0] = rpcid;

		return encoded;

	}
	

	public static void unmarshallVoid(byte[] data) {

		// TODO: unmarshall void type
		//Void returnerer ingenting, så her er det ingenting å gjøre.
		
	}

	public static byte[] marshallBoolean(byte rpcid, boolean b) {

		byte[] encoded = new byte[2];

		encoded[0] = rpcid;

		if (b) {
			encoded[1] = 1;
		} else {
			encoded[1] = 0;
		}

		return encoded;
	}

	public static boolean unmarshallBoolean(byte[] data) {

		return (data[1] > 0);

	}

	public static byte[] marshallInteger(byte rpcid, int x) {

		byte[] encoded;

		// TODO: marshall RPC identifier and string into byte array
		
		//Setter av en byte array med 5 plasser:
		//index 0 til rpcid, og 4 til integer ettersom en integer er 4 bytes.
		encoded = new byte[5];
		encoded[0] = rpcid;
		
		//Lager en byte array og fyller med x parameteren.
		//ByteBuffer objektet konverterer int til bytes og slenger de inn i en array.
		byte[] xByteArray = ByteBuffer.allocate(4).putInt(x).array();
		
		//Slår sammen de to Array'ene til en, der encoded[0] fortsatt er rpcID.
		for (int i = 1; i <= xByteArray.length; i++) {
			encoded[i] = xByteArray[i - 1];
		}
		
		return encoded;
	}

	public static int unmarshallInteger(byte[] data) {

		int decoded;

		// TODO: unmarshall integer contained in data
		
		//Lager en Array med samme lengde som data parameteren.
		byte[] dataArray = new byte[data.length - 1];

		//Kopierer alt fra data parameteren inn i den nye Array'en
		//og fjerner rpcID
		for (int i = 1; i <= dataArray.length; i++) {
			dataArray[i - 1] = data[i];
		}
		
		//Omgjør den nye byte arrayen til Integer.
		decoded = ByteBuffer.wrap(dataArray).getInt();

		return decoded;

	}
}
