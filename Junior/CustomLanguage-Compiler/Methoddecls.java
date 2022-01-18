class Methoddecls implements Token
{
    int choice;
    Methoddecl m;
    Methoddecls ms;

    public Methoddecls(Methoddecl md, Methoddecls msd)
    {
        m = md;
        ms = msd;
        choice = 0;
    }

    public Methoddecls()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = m.toString(t) + ms.toString(t);
        
        return ret;
    }

    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(choice == 0)
	    {
		m.typeCheck(table, scope);
		ms.typeCheck(table, scope);
	    }
    }
}
