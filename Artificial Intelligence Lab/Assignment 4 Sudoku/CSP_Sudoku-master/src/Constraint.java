import java.util.ArrayList;


public interface Constraint {
	public boolean isSatisfied(ArrayList<Variable> variables);
}
