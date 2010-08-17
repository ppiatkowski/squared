package squared.core;

import java.util.Vector;

import com.db4o.query.Constraint;
import com.db4o.query.Query;

import squared.model.Diagram;
import squared.model.Node;

public class QueryBuilder {

	public static String getTextQuery(Diagram diagram) {
		Vector<String> result = new Vector<String>();
		result.add(new String("Query query = db.query();"));
		
		if (diagram.getRootElement() != null) {
			result.addAll( generateQuery(diagram) );
		}
		
		result.add("ObjectSet result = query.execute();");
		
		
		return convertToString(result);
	}
	
	private static Vector<String> generateQuery(Diagram diagram) {
		Vector<String> query = new Vector<String>();
		
		StringBuffer rootConstrain = new StringBuffer("query.constrain(");
		Node root = (Node)diagram.getRootElement();
		rootConstrain.append(root.getData().clazz.getName());
		rootConstrain.append(".class);");
		query.add(rootConstrain.toString());
		
		for (Node child : root.getChildren()) {
			//traverse(child);
		}
		
		/*
	            Query engineQuery = query.descend("engine");
	            Query capacityEngineQuery = engineQuery.descend("capacity");
	            Constraint capacityEngineConstraint = capacityEngineQuery.constrain(new Double(1.61));
	            capacityEngineConstraint.smaller();
	            
	            Query powerEngineQuery = engineQuery.descend("power");
	            Constraint powerEngineConstraint = powerEngineQuery.constrain(new Integer(100));
	            powerEngineConstraint.greater();
	            
	            Query driverQuery = query.descend("driver");
	            Query nameDriverQuery = driverQuery.descend("name");
	            Constraint nameDriverConstraint = nameDriverQuery.constrain("F");
	            nameDriverConstraint.startsWith(true);
		 */
//		query.add("query.descend(\"salary\").constrain(new Integer(50)).greater();");
		return query;
	}
	
	private static String convertToString(Vector<String> result) {
		String singleString = "";
		for (String line : result) {
			singleString += line + "\n";
		}
		return singleString;
	}
}
