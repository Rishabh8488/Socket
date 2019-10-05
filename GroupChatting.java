import java.io.*;
import java.net.*;
import java.util.*;
class Network
{
	private ServerSocket ss;
	private Socket socket;
	private ArrayList<Socket> al;			//Generic 
	public Network()
	{
		try
		{
			
			System.out.println("Acquired Port no.1111");
			ss=new ServerSocket(1111);
			al=new ArrayList<Socket>();
			for(;;)
			{
				socket=ss.accept();
				System.out.println(socket);
				al.add(socket);
				GThread gt=new GThread(al,socket);
				gt.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void main(String abc[])
	{
		new Network();
	}
}
class GThread extends Thread
{
	private DataOutputStream dos;
	private DataInputStream dis;
	private ArrayList al;
	private Socket socket;
	public GThread(ArrayList al,Socket socket)
	{
		this.al=al;
		this.socket=socket;
	}
	public void run()
	{
		String str="";
		try
		{	
			do
			{
				dis=new DataInputStream(socket.getInputStream());
				str=dis.readUTF();
				if(!str.equals("stop"))
				{
					group(str);
				}
				else
				{
					dos=new DataOutputStream(socket.getOutputStream());
					System.out.println("Client Message :"+str);
					dos.writeUTF(str);
					dos.flush();
				}
			}while(!str.equals("stop"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void group(String x)			// WILL PRINT TO OTHER CLIENTS.
	{
		try
		{
			Iterator it=al.iterator();
			while(it.hasNext())
			{
				Socket obj=(Socket)it.next();
				dos=new DataOutputStream(obj.getOutputStream());
				dos.writeUTF(x);
				dos.flush();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}