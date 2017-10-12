import java.util.ArrayList;


public class ConstraintSatisfactionProblem {
	ArrayList<Constraint> constraints;
	ArrayList<Variable> variables;
	Domain domain;
	
	public static final Failure FAILURE = new Failure();
	public ConstraintSatisfactionProblem(ArrayList<Constraint> c, ArrayList<Variable> v, Domain d){
		this.constraints = c;
		this.variables = v;
		this.domain = d;
	}
	
	public boolean isSolved(){
		for(Variable var : this.variables){
			if (!var.isInstantiated())
				return false;
		}
		return true;
	}
	
	public Variable getUninstantiatedVariable(){
		for(Variable var : this.variables){
			if(!var.isInstantiated())
				return var;
		}
		return null;
	}
	
	public boolean isConsistent(){
		
		for(Constraint c : this.constraints){
			if(!c.isSatisfied(this.variables))
				return false;
		}
		return true;
	}
}
