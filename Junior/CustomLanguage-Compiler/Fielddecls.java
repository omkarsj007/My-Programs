class Fielddecls implements Token
{
    int choice;
    Fielddecl f;
    Fielddecls fs;

    public Fielddecls(Fielddecl fd, Fielddecls fsd)
    {
        f = fd;
        fs = fsd;
        choice = 0;
    }

    public Fielddecls()
    {
	//f = fd;
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = f.toString(t) + fs.toString(t);
	//else
	//  ret = f.toString(t);
        return ret;
    }

    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(choice == 0)
	    {
		f.typeCheck(table, scope);
		fs.typeCheck(table, scope);
	    }
    }
}
