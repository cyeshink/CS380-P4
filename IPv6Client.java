import java.net.*;
import java.io.*;

public class IPv6Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("codebank.xyz", 38004)) {
			InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
			OutputStream out = socket.getOutputStream();
            InetAddress address = socket.getInetAddress();
            byte[] dstAdr = address.getAddress();
            int len = 40;
			int data = 1;
			for (int i = 0; i < 12; i++) {
				data *= 2;
				int size = len + data;
				byte [] packet = new byte [size];
				packet [0] = 0x6 << 4; //Version
				packet[1] = 0; //Traffic Class
				packet[2] = 0; //Flow Label
				packet[3] = 0; //Flow Label
				packet[4] = (byte) ((data >>> 8) & 0xFF); //Payload Length
				packet[5] = (byte) (data & 0xFF); //Payload Length
				packet[6] = 0x11; //Next Header
				packet[7] = 20; //Hop Limit
                //Source Address Extended
                for(int x = 8; x < 18; x++){
                    packet[x] = 0;
                }
				packet[18] = (byte) 0xFF; //Source Address 1's
				packet[19] = (byte) 0xFF; //Source Address 1's
				packet[20] = 1; //Source Address
				packet[21] = 2; //Source Address
				packet[22] = 3; //Source Address
				packet[23] = 4; //Source Address
                //Destination Address Extended
                for(int x = 24; x < 34; x++){
                    packet[x] = 0;
                }
				packet[34] = (byte) 0xFF; //Destination Address 1's
				packet[35] = (byte) 0xFF; //Destination Address 1's
				packet[36] = dstAdr[0]; //Destination Address
				packet[37] = dstAdr[1]; //Destination Address
				packet[38] = dstAdr[2]; //Destination Address
				packet[39] = dstAdr[3]; //Destination Address
				out.write(packet);
				System.out.println("Data Length: " + data);
                String response = "";
				for (int l = 0; l < 4; l++) {
                    response += Integer.toHexString(is.read()).toUpperCase();
				}
                System.out.println("Response: 0x" + response + "\n");	
			}
		}
		catch (Exception e) {
		}
		
	}
	
}