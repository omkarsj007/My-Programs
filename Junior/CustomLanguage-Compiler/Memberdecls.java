class Memberdecls implements Token
{
    Fielddecl f;
    Memberdecls m;
    Methoddecls mt;
    int choice;
    
    public Memberdecls(Fielddecl fs, Memberdecls ms)
    {
        f = fs;
        m = ms;
	choice = 0;
    }

    public Memberdecls(Methoddecls ms)
    {
        mt = ms;
	choice = 1;
    }

   
    public String toString(int t)
    {
        String ret = "";
	if(choice == 0)
	ret = f.toString(t) + m.toString(t);
	else
	    ret = mt.toString(t);
	return ret;
    }

    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(choice == 0)
	    {
		f.typeCheck(table, scope);
		m.typeCheck(table, scope);
	    }
	else
	    {
		mt.typeCheck(table,scope+1);
	    }
    }
}
