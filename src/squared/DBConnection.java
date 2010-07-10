package squared;

import java.util.List;

import squared.utils.Tree;
import squared.utils.TreeNode;
import squared.utils.Utils;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.Db4oException;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;

public class DBConnection {
	private static DBConnection instance = null; 
	private static ObjectContainer db;
	private static String containerPath;
	private static Tree<ReflectClass> dbReflection;
	
	private DBConnection() {
	}
	
	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	public void open(String path) throws DatabaseFileLockedException, Db4oException {
		if (db != null)
		{
			close();
		}
		
		containerPath = path;
		System.out.println("[squared] OPEN object container: "+containerPath);
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), path);
		dbReflection = new Tree<ReflectClass>();
		
		try{
		
			List<ReflectClass> storedClasses = Utils.getStoredClasses(db);
			
			// placeholder root
			if (!storedClasses.isEmpty()) {
				dbReflection.setRootElement(new TreeNode<ReflectClass>(storedClasses.get(0)));
			
				for (ReflectClass cls : storedClasses)
				{
					System.out.println("===========================TRAVERSE=======================");
					System.out.println("class: "+cls.getName());
					traverse(cls, dbReflection.getRootElement());
				}
			}
			
			System.out.println("\n\n\n\n\n\n\n\n");
			//System.out.println("TREE: "+dbReflection.getRootElement().toString());
		} catch(Exception e){
			System.out.println("EKSEPSZON: ");
			e.printStackTrace();
		}
	}
	
	private void traverse(ReflectClass cls, TreeNode<ReflectClass> parentNode) {
		System.out.print("--------------------\ntraverse: "+cls.getName()+"  ");
		
		TreeNode node = new TreeNode<ReflectClass>(cls);
		parentNode.addChild(node);
		
		if (cls.isPrimitive() || Utils.isIgnored(cls.getName()))
		{
			System.out.println("Primitive or Ignored");
		}
		else
		{
			ReflectField[] fields = cls.getDeclaredFields();
			System.out.println("\nfields:");
			for (ReflectField field : fields) {
				System.out.println("field: "+field.getName());
			}
			for (ReflectField field : fields) {
				traverse(field.getFieldType(), node);
			}
			
		}
	}
	
	public void close() {
		System.out.println("[squared] CLOSE object container: "+containerPath);
		db.close();
		
		db = null;
		dbReflection = null;
		containerPath = null;
	}
	
	public Tree<ReflectClass> getDBReflection()
	{
		return dbReflection;
	}
	
	public boolean isOpened()
	{
		return db != null;
	}

}
