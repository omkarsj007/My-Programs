class Args implements Token
{
    int choice;
    Args a;
    Expr e;
  
    public Args(Expr ed, Args ad)
	{
	    a = ad;
	    e = ed;
	    choice = 0;
	}
  
    public Args(Expr ed)
	{
	    e = ed;
	    choice = 1;
	}
  
    public String toString(int t)
    {
	String ret = "";
	if (choice == 0)
	    ret = e.toString(t) + "," + a.toString(t);
	else
	    ret = e.toString(t);
	return ret;
    }

    public String typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(choice == 0)
	    {
		return e.typeCheck(table, scope);
	    }
	else
	    return e.typeCheck(table, scope);
    }
}
