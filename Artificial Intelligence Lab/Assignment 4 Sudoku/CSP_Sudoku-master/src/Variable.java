
public abstract class Variable {
	Object value;
	public abstract boolean isInstantiated();
	public abstract void uninstantiate();
	public void setValue(Object val){
		this.value = val;
	}
}
