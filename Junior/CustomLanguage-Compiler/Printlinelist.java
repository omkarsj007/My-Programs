class Printlinelist implements Token
{
    int choice;
    Printlist pl;

    public Printlinelist(Printlist pld)
    {
        pl = pld;
        choice = 0;
    }

    public Printlinelist()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = pl.toString(t);
        
        return ret;
    }
}
