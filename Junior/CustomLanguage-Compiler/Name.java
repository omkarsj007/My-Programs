class Name implements Token
{
    int choice;
    String id;
    Expr e;
  
    public Name(String i)
    {
	id = i;
	choice = 0;
    }
  
    public Name(String i, Expr ed)
    {
	id = i;
	e = ed;
	choice = 1;
    }
  
    public String toString(int t)
    {
	String ret = "";
	if (choice == 0)
	    ret = id;
	else
	    ret = id + "[" + e.toString(t) + "]";
	return ret;
    }
    
    public String typeCheck(VarTable table, int scope) throws ExampleException
    {
	String thisType = table.getType(id, scope);
	
        if (thisType.equals(""))
	    throw new ExampleException("Error: " + id + " not declared");
        
	boolean isFinal = table.getFinal(id, scope);
	if(isFinal)
	    return "final"+thisType;

	boolean isArr = table.getArr(id, scope);
	if(isArr)
	    return "arr"+thisType;

	return thisType;
    }
}
