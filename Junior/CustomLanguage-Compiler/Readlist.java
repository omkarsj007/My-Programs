class Readlist implements Token
{
    int choice;
    Name n;
    Readlist r;

    public Readlist(Name nd, Readlist rd)
    {
        n = nd;
        r = rd;
        choice = 0;
    }

    public Readlist(Name nd)
    {
        n = nd;
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = n.toString(t) + "," + r.toString(t);
        else
            ret = n.toString(t);
        return ret;
    }

    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	String thisType = n.typeCheck(table, scope);
        if (thisType.equals(""))
	    throw new ExampleException("Error: variable not found, readlist");

	if(thisType.contains("final"))
	    throw new ExampleException("Error: cannot read final variable");
	
	if (choice == 0)
	    {
		r.typeCheck(table, scope);
	    }
    }
}
