class ArgdeclList implements Token
{
    Argdecl a;
    ArgdeclList al;
    int choice;

    public ArgdeclList(Argdecl ad, ArgdeclList ald)
    {
        a = ad;
        al = ald;
        choice = 0;
    }

    public ArgdeclList(Argdecl ad)
    {
        a = ad;
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = a.toString(t) + " , " + al.toString(t);
        else
            ret = a.toString(t);
        return ret;
    }
}
