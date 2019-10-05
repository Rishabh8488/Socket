import java.io.*;
import java.net.*;
class ServerDemo
{
	private DataInputStream dis;
	private DataOutputStream dos;
	private ServerSocket ss;		//Server Object
	private Socket socket;			//Client Object
	public ServerDemo()
	{
		System.out.println("Port number aquired 1111");
		try
		{
			ss=new ServerSocket(1111);		// 1111 is port number assigned to the server.
			for(;;)
			{
				socket=ss.accept();		// Accepts Request from the client
				System.out.println(socket);
				dis=new DataInputStream(socket.getInputStream());		//Input from client
				dos=new DataOutputStream(socket.getOutputStream());		//Output To Client
				SThread st=new SThread(dis,dos);						//Thread will read and write.
				st.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String abc[])
	{
		new ServerDemo();
	}
}
class SThread extends Thread
{
	private DataInputStream dis;
	private DataOutputStream dos;
	public SThread(DataInputStream dis,DataOutputStream dos)
	{
		this.dis=dis;
		this.dos=dos;
	}
	public void run()
	{
		try
		{
			String str="";
			do
			{
				str=dis.readUTF();
				System.out.println("Client Message: "+str);
				dos.writeUTF(str);
				dos.flush();				//Flush will empty buffer
			}while(!str.equals("stop"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}