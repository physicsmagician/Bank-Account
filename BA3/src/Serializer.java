import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Serializer
{
	public void serialize(Object o, String fileName) throws FileNotFoundException, IOException, Exception
	{
		ObjectOutputStream ouputStream = new ObjectOutputStream(new FileOutputStream(fileName));
		ouputStream.writeObject(o);
		ouputStream.close();
	}
		
	public Object deserialize(String fileName) throws IOException, ClassNotFoundException, Exception
	{	
		Object o = null;
		 FileInputStream inputStream = new FileInputStream(fileName);
		 ObjectInputStream reader = new ObjectInputStream(inputStream);

		 o = reader.readObject();
		
		return o;
	}
}