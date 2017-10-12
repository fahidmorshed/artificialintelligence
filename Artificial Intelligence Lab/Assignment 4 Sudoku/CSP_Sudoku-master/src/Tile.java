	public class Tile extends Variable{
		int row;
		int col;
		
		public Tile(int row, int col, Object val){
			this.value = val;
			this.row = row;
			this.col = col;
		}
		
		public Tile(int row, int col){
			this.row = row;
			this.col = col;
			this.value = -1;
		}
		
		@Override
		public boolean isInstantiated() {
			return (!this.value.equals(new Integer(-1)));
		}

		@Override
		public void uninstantiate() {
			this.value = -1;
		}
		
		public int getRow(){
			return row;
		}
		
		public int getColumn(){
			return col;
		}
		
		@Override
		public String toString(){
			return this.value.toString();
		}
		
	}