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
}
