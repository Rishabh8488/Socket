import java.io.*;
import java.net.*;
class ClientDemo
{
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private Socket s;				// Client Object
	public ClientDemo()
	{
		try
		{
			s=new Socket("127.0.0.1",1111);
			dis=new DataInputStream(s.getInputStream());
			dos=new DataOutputStream(s.getOutputStream());
			WriteThread wt=new WriteThread(dos);
			ReadThread rt=new ReadThread(dis);
			wt.start();
			rt.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String abc[])
	{
		new ClientDemo();
	}
}
class WriteThread extends Thread
{
	private DataOutputStream dos;
	public WriteThread(DataOutputStream dos)
	{
		this.dos=dos;
	}
	public void run()
	{
		try
		{
			InputStreamReader isr=new InputStreamReader(System.in);
			BufferedReader br=new BufferedReader(isr);
			String str="";
			do
			{
				str=br.readLine();
				dos.writeUTF(str);
				dos.flush();
			}while(!str.equals("stop"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
class ReadThread extends Thread
{
	private DataInputStream dis;
	public ReadThread(DataInputStream dis)
	{
		this.dis=dis;
	}
	public void run()
	{
		try
		{
			String str="";
			do
			{
				str=dis.readUTF();
				System.out.println("Server Response: "+str);
			}while(!str.equals("stop"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}