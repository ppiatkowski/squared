package squared.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import squared.model.Diagram;
import squared.model.DiagramElement;
import squared.model.Node;
import squared.utils.Utils;

public class QueryBuilder {
	
	protected static String[] validOperators = {">=", "<=", "==", ">", "<", "*"};

	public static String getTextQuery(Diagram diagram) {
		Vector<String> result = new Vector<String>();
		result.add(new String("Query query = db.query();"));
		
		if (diagram.getRootElement() != null) {
			result.addAll( generateQuery(diagram) );
		}
		
		result.add("ObjectSet result = query.execute();");
		
		
		return convertToString(result);
	}
	
	protected static String parseConstraint(String inputData) {
		StringBuffer expression = new StringBuffer("");
		String operator = "";
		int operatorIndex = -1;
		for (int i = 0; i < validOperators.length; i++) {
			operatorIndex = inputData.indexOf(validOperators[i]); 
			if (operatorIndex != -1) {
				operator = validOperators[i];
				break;
			}
		}
		
		if (operatorIndex == -1) {
			expression.append("(").append(inputData).append(")");
		} else {
			String leftSide = inputData.substring(0, operatorIndex);
			String rightSide = inputData.substring(operatorIndex + operator.length(), inputData.length());
			expression.append("(").append(parseStringConstraint(leftSide.trim(), operator, rightSide.trim()));
		}
		
		return expression.toString();
	}
	
	protected static boolean isNumber(String str) {
		boolean result = true;
		try {
			Double.parseDouble(str);
			result = true;
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}
	
	protected static StringBuffer parseStringConstraint(String leftSide, String operator, String rightSide) {
		StringBuffer expression = new StringBuffer("");
		if (operator.equals("*")) {
			if (!leftSide.equals("")) {
				expression.append("\"").append(leftSide).append("\").startsWith(false)");
			}
		} else if (isNumber(rightSide)) {
			expression.append(rightSide);
			if (operator.equals(">")) {
				expression.append(").greater()");
			} else if (operator.equals("<")) {
				expression.append(").smaller()");
			} else if (operator.equals(">=")) {
				expression.append(").greater().equal()");
			} else if (operator.equals("<=")) {
				expression.append(").smaller().equal()");
			} else if (operator.equals("==")) {
				expression.append(")");
			}
		}
		return expression;
	}
	
	
	protected static Vector<String> generateQuery(Diagram diagram) {
		Vector<String> query = new Vector<String>();
		
		StringBuffer rootConstrain = new StringBuffer("query.constrain(");
		Node root = (Node)diagram.getRootElement();
		rootConstrain.append(root.getData().clazz.getName());
		rootConstrain.append(".class);");
		query.add(rootConstrain.toString());
		
		
		// finds all diagram elements that have contraints (1)
		// when constraint is found we travel tree from that node up to the diagram root (2)
		// we record travel history on the stack while we go up the tree
		// then we pop the stack (reversing the travel history) (3)
		for (DiagramElement e : diagram.getElements()) {
			if (e instanceof Node) {
				Node node = (Node) e;
				Hashtable<String, String> constraints = node.getConstraints();
				// (1)
				if (!constraints.isEmpty()) { 
					Set set = constraints.entrySet();
					Iterator constr = set.iterator();
					while (constr.hasNext()) {
						
						Stack<String> descendStack = new Stack<String>();
						Map.Entry entry = (Map.Entry) constr.next();
						descendStack.push(".descend(\"" + entry.getKey() + "\").constrain" + parseConstraint((String)entry.getValue()) + ";");
						
						StringBuffer line = new StringBuffer();
						line.append("Constraint constr").append(Utils.capitalize((String)entry.getKey()));
						
						// (2)
						Node currentNode = node;
						while (currentNode.getParent() != null) {
							StringBuffer descent = new StringBuffer(currentNode.getDescent());
							char capitalized = descent.charAt(0);
							line.append(Utils.capitalize(descent.toString()));
							
							descendStack.push(".descend(\""+descent.toString()+"\")");
							currentNode = currentNode.getParent();
						}
						descendStack.push("query");
						
						// (3)
						line.append(" = ");
						while (!descendStack.empty()) {
							line.append(descendStack.pop());
						}
						
						query.add(line.toString());
					}
					

				}
			}
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
	
	protected static String convertToString(Vector<String> result) {
		String singleString = "";
		for (String line : result) {
			singleString += line + "\n";
		}
		return singleString;
	}
}
