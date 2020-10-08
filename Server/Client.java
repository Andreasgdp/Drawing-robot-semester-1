import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		Socket s = new Socket("LocalHost", 4999);

		PrintWriter pr = new PrintWriter(s.getOutputStream());

		pr.println("it is working");
		pr.flush();

		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);

		String str = bf.readLine();
		System.out.println("Server : " + str);

	}
}
