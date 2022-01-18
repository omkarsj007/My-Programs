class Printlist implements Token
{
    int choice;
    Printlist pl;
    Expr e;
  
    public Printlist(Expr ed, Printlist pld)
    {
	pl = pld;
	e = ed;
	choice = 0;
    }
  
    public Printlist(Expr ed)
    {
	e = ed;
	choice = 1;
    }
  
    public String toString(int t)
    {
	String ret = "";
	if (choice == 0)
	    ret = e.toString(t) + "," + pl.toString(t);
	else
	    ret = e.toString(t);
	return ret;
    }
    
    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	String thisType = e.typeCheck(table, scope);
        if (thisType.equals(""))
            throw new ExampleException("Error: expr not found, printlist");

	if (thisType.equals("void"))
	    throw new ExampleException("Error: cannot print a void function result");

        if (choice == 0)
            {
                pl.typeCheck(table, scope);
            }
    }

}
