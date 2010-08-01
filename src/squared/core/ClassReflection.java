package squared.core;

import com.db4o.reflect.ReflectClass;

public class ClassReflection {
	protected String descent;
	protected ReflectClass clazz;
	
	public ClassReflection(ReflectClass clazz, String descent)
	{
		this.descent = descent;
		this.clazz = clazz;
	}
	
	public String getDescent() 
	{
		return this.descent;
	}
	
	public ReflectClass getType()
	{
		return this.clazz;
	}
}
