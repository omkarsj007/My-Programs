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
}
