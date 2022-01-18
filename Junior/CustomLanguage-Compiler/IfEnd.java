class IfEnd implements Token
{
    int choice;
    Stmt s;

    public IfEnd(Stmt sd)
    {
        s = sd;
        choice = 0;
    }

    public IfEnd()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
	String tabs = "";
	for (int i = 0; i < t; ++i)
	    tabs += "\t";

        if (choice == 0)
            ret = tabs + "else\n" + s.toString(t+1) + tabs + "fi\n";
        else
            ret = tabs + "fi\n";
        return ret;
    }
}
