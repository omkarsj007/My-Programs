class Returntype implements Token
{
    int choice;
    Type ty;

    public Returntype(Type t)
    {
        ty = t;
        choice = 0;
    }

    public Returntype()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = " " +  ty.toString(t) + " ";
        else
            ret = " void ";

        return ret;
    }
}
