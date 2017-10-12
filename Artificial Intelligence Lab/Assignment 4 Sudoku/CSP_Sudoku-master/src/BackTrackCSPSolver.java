import java.util.ArrayList;

public class BackTrackCSPSolver {

	public ArrayList<Variable> backTrackSolve(ConstraintSatisfactionProblem csp) {
		ArrayList<Variable> assignment = (ArrayList<Variable>) csp.variables
				.clone();
		return recursiveBackTrackSolver(assignment, csp);
	}

	private ArrayList<Variable> recursiveBackTrackSolver(
			ArrayList<Variable> assignment, ConstraintSatisfactionProblem csp) {

		if (csp.isSolved())
			return assignment;

		Variable var = csp.getUninstantiatedVariable();

		ArrayList<Object> domain = csp.domain.getDomain();
		for (Object val : domain) {
			var.setValue(val);
			if (csp.isConsistent()){
				ArrayList<Variable> result = recursiveBackTrackSolver(assignment, csp);
				if(result != csp.FAILURE){
					return result;
				}
				var.uninstantiate();
			} var.uninstantiate();
		}
		return csp.FAILURE;
	}
		

}
