class Argdecls implements Token
{
    ArgdeclList a;
    int choice;

    public Argdecls(ArgdeclList ad)
    {
        a = ad;
        choice = 0;
    }

    public Argdecls()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = a.toString(t);
        
        return ret;
    }
}
