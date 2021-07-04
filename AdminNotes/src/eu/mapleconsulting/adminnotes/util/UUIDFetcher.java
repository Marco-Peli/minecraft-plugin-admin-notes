package eu.mapleconsulting.adminnotes.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.Callable;
 
public class UUIDFetcher implements Callable<String> {
    private static final String API_URL = "https://playerdb.co/api/player/minecraft/";
 
    private String playerToRetrieveName;
 
    public UUIDFetcher(String name){
    	this.playerToRetrieveName = name;
    }
 
    public String call() throws Exception {
    	String urlString = API_URL + this.playerToRetrieveName;
		URL url;
		HttpURLConnection con = null;
		BufferedReader in = null;
		String id = "";
		try {
			url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestMethod("GET");
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			JsonObject jsonResponse = new Gson().fromJson(content.toString(), JsonObject.class);
			boolean success = jsonResponse.get("success").getAsBoolean();
			
			if(success == true)
			{
				id = jsonResponse.get("data").getAsJsonObject().get("player").getAsJsonObject().get("id").getAsString();
			}
			else
			{
				throw new Exception();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				con.disconnect();
			} catch (Exception e) {

			}
			
		}
		
		return id;
        
    }
 
    public static byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }
 
    public static UUID fromBytes(byte[] array) {
        if (array.length != 16) {
            throw new IllegalArgumentException("Illegal byte array length: " + array.length);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
        long mostSignificant = byteBuffer.getLong();
        long leastSignificant = byteBuffer.getLong();
        return new UUID(mostSignificant, leastSignificant);
    }
 
    public static UUID getUUIDOf(String name) throws Exception {
        return UUID.fromString(new UUIDFetcher(name).call());
    }
    
    public static String getUUIDFromName(String name) throws Exception{
    	name.toLowerCase();
		return UUIDFetcher.getUUIDOf(name).toString();
	}
}